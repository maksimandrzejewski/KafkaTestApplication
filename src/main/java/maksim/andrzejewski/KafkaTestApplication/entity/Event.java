package maksim.andrzejewski.KafkaTestApplication.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private EventType type;

    private String description;

    @CreationTimestamp
    private LocalDate creationDate;

    @UpdateTimestamp
    private LocalDateTime modificationDateTime;
}
