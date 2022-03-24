package main.kafka.multiconsumer;

import main.kafka.common.KafkaUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author locks
 * @date 2022-03-22 18:37
 */
public class WithOneConsumerAndMultiHander {


    public static void main(String[] args) {
        String topicName = "test_topic";
        String groupId = "test_topic_groupid111";

        KafkaConsumer<String, String> consumer = KafkaUtil.getConsumer(topicName, groupId);

        new ConsumerThread(consumer).start();

    }


    private static class ConsumerThread extends Thread {

        private KafkaConsumer<String, String> consumer;
        private final ExecutorService executorService;

        public ConsumerThread(KafkaConsumer<String, String> consumer) {
            this.consumer = consumer;
            this.executorService = Executors.newFixedThreadPool(4);
        }

        @Override
        public void run() {
            while(true){
            ConsumerRecords<String, String> poll = consumer.poll(Duration.ofMillis(100));
            if (!poll.isEmpty()) {
                executorService.execute(new HandleDataThread(poll));
            }
            }
        }
    }

    private static class HandleDataThread extends Thread {
        private ConsumerRecords<String, String> records;

        public HandleDataThread(ConsumerRecords<String, String> records) {
            this.records = records;
        }


        @Override
        public void run() {
            for (ConsumerRecord<String, String> singleRecord : records) {
                System.out.println(Thread.currentThread().getName() + "  " + singleRecord.value() + singleRecord.offset());
            }
        }
    }

}
