package maksim.andrzejewski.KafkaTestApplication.config;

import lombok.RequiredArgsConstructor;
import maksim.andrzejewski.KafkaTestApplication.listener.EventListener;
import maksim.andrzejewski.KafkaTestApplication.model.EventDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link EnableKafka} annotation is required on the configuration
 * class to enable the detection of @KafkaListener annotation on spring-managed beans
 */
@EnableKafka
@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {

    private final AppProperties appProperties;

    @Bean
    public ConsumerFactory<String, EventDTO> eventConsumerFactory() {
        final Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                appProperties.getKafkaBootstrapServerUrl());
//        props.put(
//                ConsumerConfig.GROUP_ID_CONFIG,
//                appProperties.getGroupId());
//        props.put(
//                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
//                StringDeserializer.class);
//        props.put(
//                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
//                JsonDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(),
                new JsonDeserializer<>(EventDTO.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EventDTO> eventListenerContainerFactory() {
        final ConcurrentKafkaListenerContainerFactory<String, EventDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(eventConsumerFactory());
        return factory;
    }

//    @Bean
//    public EventListener eventListener1() {
//        return new EventListener();
//    }
//    @Bean
//    public EventListener eventListener2() {
//        return new EventListener();
//    }
}
