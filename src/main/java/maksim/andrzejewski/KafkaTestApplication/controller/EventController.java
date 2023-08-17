package maksim.andrzejewski.KafkaTestApplication.controller;

import lombok.RequiredArgsConstructor;
import maksim.andrzejewski.KafkaTestApplication.model.EventDTO;
import maksim.andrzejewski.KafkaTestApplication.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka/test/app/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping(value = "add-single")
    public ResponseEntity<String> postEvent(@RequestBody EventDTO eventDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.sendEvent(eventDTO));
    }
}
