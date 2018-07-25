package com.jrj.m.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

	/**
	 * servers
	 */
    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String servers;
    /**
     * 组id
     */
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;
    /**
     * 自动提交
     */
    @Value("${spring.kafka.consumer.enable-auto-commit}")
    private boolean enableAutoCommit;
    /**
     * 自动提交频率，毫秒数
     */
    @Value("${spring.kafka.consumer.auto_commit_interval_ms}")
    private int autoCommitIntervalMs;
    /**
     * 应答拉取请求的阻塞时间，单位毫秒
     */
    @Value("${spring.kafka.consumer.fetch-max-wait}")
    private int fetchMaxWait;
    /**
     * 一次取出最大消息数
     */
    @Value("${spring.kafka.consumer.max-poll-records}")
    private int maxPollRecords;
    @Value("${spring.kafka.consumer.key-deserializer}")
    private String keySerializer;
    @Value("${spring.kafka.consumer.value-deserializer}")
    private String valueSerializer;
    /**
     * listener 线程并发数
     */
    @Value("${spring.kafka.listener.concurrency}")
    private int concurrency;
    
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(concurrency);
        factory.getContainerProperties().setPollTimeout(1500);
        return factory;
    }

    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }
    
    /**
     * @Description: 消费者配置
     * @return Map<String,Object>
     * @author yuhai.li
     * @date 2018年1月24日 上午9:46:34
     */
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> propsMap = new HashMap<>();
        propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitIntervalMs);
        propsMap.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, fetchMaxWait);
        propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keySerializer);
        propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueSerializer);
        propsMap.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return propsMap;
    }

}