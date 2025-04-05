package com.productorderingapp.order_service.config;

import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.productorderingapp.order_service.event.OrderPlacedEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Properties;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }

    Properties getProperties(){
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
        return properties;
    }

     ProducerFactory getProducerFactory(){
        return new DefaultKafkaProducerFactory(getProperties());
    }
    @Bean
    KafkaTemplate<String, OrderPlacedEvent> getKafkaTemplate(){
        return new KafkaTemplate(getProducerFactory());
    }
}
