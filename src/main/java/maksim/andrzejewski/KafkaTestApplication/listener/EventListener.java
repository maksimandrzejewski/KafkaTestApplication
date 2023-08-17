package maksim.andrzejewski.KafkaTestApplication.listener;

import lombok.extern.slf4j.Slf4j;
import maksim.andrzejewski.KafkaTestApplication.entity.EventType;
import maksim.andrzejewski.KafkaTestApplication.model.EventDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Slf4j
public class EventListener {

    @RetryableTopic(
            attempts = "3",
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE,
            backoff = @Backoff(delay = 10_000, maxDelay = 15_000, random = true),
            dltTopicSuffix = ".deadletter"
    )
    @KafkaListener(topics = "${app.properties.topicName}",
            groupId = "${app.properties.groupId}",
            containerFactory = "eventListenerContainerFactory")
    public void listenEvent(@Payload EventDTO eventDTO,
                            @Header(name = KafkaHeaders.RECEIVED_KEY, required = false) String key,
                            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                            @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp,
                            @Header(KafkaHeaders.OFFSET) long offset
                            ) {
        log.info("Single event listener: Received {}", eventDTO);
        log.info("key {}, topic {}, partition {}, offset {}, timestamp {}", key, topic, partition, offset, new Timestamp(timestamp).toLocalDateTime());

        if (eventDTO.getType() == EventType.DELETED) {
            throw new RuntimeException("DELETED EVENT");
        }
    }


//
//    @KafkaListener(topics = "${app.properties.topicName}",
//            groupId = "${app.properties.groupId}",
//            containerFactory = "eventListenerContainerFactory")
//    public void listenBatchEvent(@Payload List<EventDTO> eventDTO,
//                            @Header(name = KafkaHeaders.RECEIVED_KEY, required = false) String key,
//                            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
//                            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
//                            @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp,
//                            @Header(KafkaHeaders.OFFSET) long offset
//                            ) {
//        log.info("Batched event listener: Received {}", eventDTO.size());
//        log.info("key {}, topic {}, partition {}, offset {}, timestamp {}", key, topic, partition, offset, new Timestamp(timestamp).toLocalDateTime());
//    }
}
