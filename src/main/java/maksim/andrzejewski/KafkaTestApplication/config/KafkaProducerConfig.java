package maksim.andrzejewski.KafkaTestApplication.config;


import lombok.RequiredArgsConstructor;
import maksim.andrzejewski.KafkaTestApplication.model.EventDTO;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.RoundRobinPartitioner;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.web.context.annotation.RequestScope;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {

    private final AppProperties appProperties;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        final Map<String, Object> configs = new HashMap<>();

        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, appProperties.getKafkaBootstrapServerUrl());
        return new KafkaAdmin(configs);
    }

//    /**
//     * Creation of new topic!
//     */
//    @Bean
//    public NewTopic retryTopic() {
//        return new NewTopic(appProperties.getTopicName() + ".retry", 1, (short) 1);
//    }
    /**
     * Creation of new topic!
     */
    @Bean
    public NewTopic deadLetterTopic() {
        return new NewTopic(appProperties.getTopicName() + ".deadletter", 1, (short) 1);
    }

    @Bean
    public ProducerFactory<String, EventDTO> eventProducerFactory() {
        final Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                appProperties.getKafkaBootstrapServerUrl());
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, 400);
//        configProps.put(ProducerConfig.LINGER_MS_CONFIG, 200);
//        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, false);
//        configProps.put(
//                ProducerConfig.PARTITIONER_CLASS_CONFIG,
//                RoundRobinPartitioner.class.getName());
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, EventDTO> eventKafkaTemplate() {
        return new KafkaTemplate<>(eventProducerFactory());
    }

}
