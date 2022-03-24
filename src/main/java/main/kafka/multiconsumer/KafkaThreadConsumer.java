package main.kafka.multiconsumer;

import main.kafka.multiconsumer.ConsumerThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author locks
 * @date 2022-03-11 14:56
 */
public class KafkaThreadConsumer {
        private volatile int icou = 0;
  public static void main(String[] args) {

    ExecutorService executorService = Executors.newFixedThreadPool(2);


    for (int i = 0; i < 3; i++) {
        executorService.execute(new ConsumerThread(i ));
     }
  }
}
