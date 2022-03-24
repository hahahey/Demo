package main.kafka.multiconsumer;

import main.kafka.common.KafkaUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author locks
 * @date 2022-03-22 9:24
 */
public class WithPartitionsConsumer {
    public static void main(String[] args) {

        String topicName = "test_topic";
        String groupId = "test_topic_groupid11";

        KafkaConsumer<String, String> consumer = KafkaUtil.getConsumer(topicName, groupId);

        consumer.poll(Duration.ofMillis(100));
        List<PartitionInfo> partitionInfos = consumer.partitionsFor(topicName);

        System.out.println(partitionInfos.size());
        for (int i = 0; i < partitionInfos.size(); i++) {
            new WithPartitionsConsumerThread(topicName,groupId).start();
        }


    }



    public static class WithPartitionsConsumerThread extends Thread{

        private KafkaConsumer<String,String> consumer;

        public WithPartitionsConsumerThread(String topicName,String groupId){
            this.consumer = KafkaUtil.getConsumer(topicName, groupId);
            this.consumer.subscribe(Collections.singletonList(topicName));
        }


        @Override
        public void run() {
            while(true){
            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "---- " +consumerRecord.topic() + " " + consumerRecord.value() + " " + consumerRecord.offset());
            }
            }
        }
    }
}
