package maksim.andrzejewski.KafkaTestApplication.listener;

import lombok.extern.slf4j.Slf4j;
import maksim.andrzejewski.KafkaTestApplication.model.EventDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Slf4j
public class EventListener {

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
        log.info("Received {}", eventDTO);
        log.info("key {}, topic {}, partition {}, offset {}, timestamp {}", key, topic, partition, offset, new Timestamp(timestamp).toLocalDateTime());
    }
}
