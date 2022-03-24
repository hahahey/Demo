package main.kafka.common;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.Properties;

/**
 * @author locks
 * @date 2022-03-09 15:38
 */
public class KafkaUtil {



  public static KafkaConsumer<String,String> getConsumer(){


      Properties props = new Properties();
      props.put("bootstrap.servers", "hadoop101:9092,hadoop102:9092,hadoop103:9092");
      props.put("group.id", "groupId11");
      props.put("enable.auto.commit", "true");
      props.put("auto.offset.reset", "earliest");
      props.put("max.poll.records", 10);
      props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
      props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

      //    String topicName = "test_topic";
      String topicName = "topic_1_partiton_3_factor";

      KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
      consumer.subscribe(Collections.singletonList(topicName));
      return consumer;
    }

    public static KafkaConsumer<String,String> getConsumer(String topicName,String groupId){

        Properties props = new Properties();
        props.put("bootstrap.servers", "hadoop101:9092,hadoop102:9092,hadoop103:9092");
        props.put("group.id", groupId);
        props.put("enable.auto.commit", "false");
        props.put("auto.offset.reset", "earliest");
        props.put("max.poll.records", 10);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topicName));
        return consumer;
    }

}
