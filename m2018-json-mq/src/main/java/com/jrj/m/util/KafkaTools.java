package com.jrj.m.util;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author tao.wang@jrj.com.cn
 *
 */
public class KafkaTools {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private static KafkaTools instance;	 
	
	private Producer<String, String> producer;
	private final Properties props = new Properties();
	
	public static KafkaTools getInstance(String kafka_address) {
		if(instance==null){
			instance=new KafkaTools(kafka_address);
		}
		return instance;
	}

	public KafkaTools(String kafka_address){
		props.put("bootstrap.servers",kafka_address );
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		producer = new KafkaProducer<>(props);		
	}
	
	public void send(String topic,String msg){
		log.info(msg,"proxy");		
		String key="1";
		producer.send(new ProducerRecord<String, String>(topic, key, msg));
		//producer.close(); 
	}

}
