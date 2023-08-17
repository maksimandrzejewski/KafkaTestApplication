package maksim.andrzejewski.KafkaTestApplication.repository;

import maksim.andrzejewski.KafkaTestApplication.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
