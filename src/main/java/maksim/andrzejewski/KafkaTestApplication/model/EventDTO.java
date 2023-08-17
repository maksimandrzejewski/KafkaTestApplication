package maksim.andrzejewski.KafkaTestApplication.model;

import lombok.Data;
import lombok.Value;
import maksim.andrzejewski.KafkaTestApplication.entity.EventType;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
public class EventDTO {

    private Long id;

    private EventType type;

    private String description;

    private LocalDate creationDate;

    private LocalDateTime modificationDateTime;
}
