package indi.nyp.service.impl;

import indi.nyp.params.KafkaParams;
import indi.nyp.service.KafkaUsingCase;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class KafkaUsingCaseImpl implements KafkaUsingCase {

    @Autowired
    @Qualifier(value = "kafka consumer")
    Properties consumerConfig;

    @Autowired
    @Qualifier(value = "kafka producer")
    Properties producerConfig;

    @Override
    public void consumerFunction() {
        log.info("++++++++++++++consumer begin++++++++++++++++");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerConfig);
        consumer.subscribe(Collections.singletonList(KafkaParams.topic1));

        new Thread(() -> {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                }
            }

        }).start();

        try {
            Thread.sleep(2000);
            //让consumer 2s后停止消费
            consumer.wakeup();
        } catch (InterruptedException e) {
            log.error("sleep ex",e);
        }

    }

    @Override
    public void producerFunction() {
        Producer<String, String> producer = new KafkaProducer<>(producerConfig);
        //随机生成value，key从1～10，可以根据负载均衡分到不同的topic的partition
        for (int i = 0; i < 10; i++)
            producer.send(new ProducerRecord<String, String>(KafkaParams.topic1, Integer.toString(i), Integer.toString((int)(i+Math.random()*10))));

        producer.close();
    }
}
