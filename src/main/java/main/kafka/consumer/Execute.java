package main.kafka.consumer;

import main.kafka.common.KafkaUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author locks
 * @date 2022-03-09 15:40
 */
public class Execute {
  private static final Logger log = LoggerFactory.getLogger("KafkaConsumerDemo");

  public static  void execute() {

    KafkaConsumer<String, String> consumer = KafkaUtil.getConsumer();
    log.info("get consumer");

    Thread thread = Thread.currentThread();

    Runtime.getRuntime().traceMethodCalls(true);
    Runtime.getRuntime()
            .addShutdownHook(
                    new Thread(
                            () -> {
                              System.out.println("执行钩子方法");
                              log.info("执行钩子方法");
                              consumer.wakeup();

                              try {
                                thread.join();
                              } catch (InterruptedException e) {
                                e.printStackTrace();
                              }
                            }));
    int count = 0;


    try {
      while (true) {
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

        for (ConsumerRecord<String, String> recordData : records) {
          count++;
          String value = recordData.value();

          System.out.println(value);
          try {
            TimeUnit.MILLISECONDS.sleep(90);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        consumer.commitSync();
        System.out.println(count);
      }
    } catch (WakeupException e) {
      System.out.println("kafka consumer 停止了!!!");
     log.info("kafka consumer 停止了!!!");
    } finally {
      System.out.println("finally 方法!!!");
      log.info("finally 方法!!!");
    }
  }
}
