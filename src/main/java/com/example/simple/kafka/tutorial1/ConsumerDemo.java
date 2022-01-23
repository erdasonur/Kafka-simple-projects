package com.example.simple.kafka.tutorial1;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ConsumerDemo {
  public static void main(String[] args) {
    Logger logger = LoggerFactory.getLogger(ConsumerDemo.class);
    //create Consumer properties
    Properties properties = new Properties();
    properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
    properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "my-first-app");
    properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    //create the consumer
    KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);

    //subscribe consumer to our topics
    consumer.subscribe(Collections.singleton("first_topic"));

    //poll for the new data
    while (true) {
      ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
      for (ConsumerRecord<String, String> record : records) {
        logger.info("Received new metadata. \n"
                + "Key:" + record.key() + "\n"
                + "Value:" + record.value() + "\n"
                + "Partition:" + record.partition() + "\n"
                + "Offset:" + record.offset());
      }
    }
  }
}
