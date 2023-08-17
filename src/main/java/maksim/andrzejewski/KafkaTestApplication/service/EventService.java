package maksim.andrzejewski.KafkaTestApplication.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maksim.andrzejewski.KafkaTestApplication.config.AppProperties;
import maksim.andrzejewski.KafkaTestApplication.model.EventDTO;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Random;

@Service
//@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
//@RequestScope
@Slf4j
@RequiredArgsConstructor
public class EventService {

    private final AppProperties appProperties;
    private final KafkaTemplate<String, EventDTO> eventKafkaTemplate;


    public String sendEvent(EventDTO eventDTO) {
        final String topicName = appProperties.getTopicName();
        final ProducerRecord<String, EventDTO> producerRecord = new ProducerRecord<>(topicName, eventDTO);
        try {
            final SendResult<String, EventDTO> stringEventDTOSendResult = eventKafkaTemplate.send(producerRecord).get();
            final RecordMetadata recordMetadata = stringEventDTOSendResult.getRecordMetadata();
            final String producerMetaData = String.format("Send to topic: %s key: %s partition: %s, offset: %s", recordMetadata.topic(), producerRecord.key(), recordMetadata.partition(), recordMetadata.offset());
            log.info(producerMetaData);
            return producerMetaData;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }



}
