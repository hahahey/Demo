package main.kafka.multiconsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author locks
 * @date 2022-03-11 14:56
 */
public class ConsumerThread implements Runnable {

  private KafkaConsumer<String, String> kafkaConsumer;
  private int id;

  private static final Properties props = new Properties();

  static {
    props.put("bootstrap.servers", "hadoop101:9092,hadoop102:9092,hadoop103:9092");
    props.put("group.id", "groupId010");
    props.put("enable.auto.commit", "true");
    props.put("auto.offset.reset", "earliest");
    props.put("max.poll.records", 10);
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
  }

  public ConsumerThread(int id ) {
    this.kafkaConsumer = new KafkaConsumer<>(props);
    this.id = id;
  }

  @Override
  public void run() {
    kafkaConsumer.subscribe(Arrays.asList("test_topic"));

    while (true) {
      ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));
      for (ConsumerRecord<String, String> singleRecord : records) {
//        try {
//          TimeUnit.SECONDS.sleep(2);
//        } catch (InterruptedException e) {
//          e.printStackTrace();
//        }
        System.out.println(
                Thread.currentThread().getName() + "   "
                + singleRecord.topic()
                + "   "
                + singleRecord.partition()
                + "   "
                + singleRecord.offset()
                + "   "
                + singleRecord.value());
      }
    }
  }
}
