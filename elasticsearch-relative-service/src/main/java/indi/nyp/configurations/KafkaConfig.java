package indi.nyp.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration(proxyBeanMethods = false)
public class KafkaConfig {
    @Bean(value = "kafka consumer")
    public Properties getConsumerConfig() {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("group.id", "test");
        props.setProperty("enable.auto.commit", "true"); //自动提交偏移量
        props.setProperty("auto.commit.interval.ms", "1000");//自动提交偏移量的间隔
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }

    @Bean(value = "kafka producer")
    public Properties getProducerConfig(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all"); //即-1，leader和follower都同步完成后才会发ack
        props.put("retries", 0);
        props.put("linger.ms", 1);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        return props;
    }
}
