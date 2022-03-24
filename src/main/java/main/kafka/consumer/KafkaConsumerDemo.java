package main.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.*;

/**
 * @author locks
 * @date 2022-02-09 9:56
 */
public class KafkaConsumerDemo {

  public static void main(String[] args) {

    Properties props = new Properties();
    props.put("bootstrap.servers", "hadoop101:9092,hadoop102:9092,hadoop103:9092");
    props.put("group.id", "0001");
    props.put("enable.auto.commit", "true");
    props.put("auto.offset.reset", "earliest");
    props.put("max.poll.records", 10);
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

    //    String topicName = "test_topic";
    //    String topicName = "topic_1_partiton_3_factor";
    String topicName = "topic_1_partiton_3_factor";

    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
    consumer.subscribe(Collections.singletonList(topicName));


    TopicPartition topicPartition = new TopicPartition(topicName, 0);

    consumer.poll(Duration.ofMillis(1000));

    consumer.seek(topicPartition,1000001);


    while (true) {

      ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));


      Map<TopicPartition, OffsetAndMetadata> map = new HashMap<>();

      for (ConsumerRecord<String, String> recordData : records) {

        System.out.println(recordData.value() + " " + recordData.offset());
      }


      TopicPartition partition = new TopicPartition("topic_1_partiton_3_factor", 0);
      OffsetAndMetadata offsetAndMetadata = new OffsetAndMetadata(100012);

      map.put(partition,offsetAndMetadata);
      consumer.commitSync(map);
      System.out.println("------");


    }

  }
}
