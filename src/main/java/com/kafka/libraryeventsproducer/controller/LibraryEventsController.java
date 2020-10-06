package com.kafka.libraryeventsproducer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kafka.libraryeventsproducer.domain.EventType;
import com.kafka.libraryeventsproducer.domain.LibraryEvent;
import com.kafka.libraryeventsproducer.producers.LibraryEventProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LibraryEventsController {


    @Autowired
    LibraryEventProducer libraryEventProducer;

    @PostMapping("/v1/libraryevent")
    public ResponseEntity<?> postLibraryEvent(@RequestBody LibraryEvent libraryEvent) {

        try {
            if (libraryEvent.getLibraryEventId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please send Event Id");
            }
            libraryEvent.setEventType(EventType.NEW);
            libraryEventProducer.sendLibraryEvent(libraryEvent);
        } catch (JsonProcessingException e) {
            log.error("Exception occurred while posting message in the topic");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }

    @PutMapping("/v1/libraryevent")
    public ResponseEntity<?> updateLibraryEvent(@RequestBody LibraryEvent libraryEvent) {

        try {
            if (libraryEvent.getLibraryEventId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please send Event Id");
            }
            libraryEvent.setEventType(EventType.UPDATE);
            libraryEventProducer.sendLibraryEvent(libraryEvent);
        } catch (JsonProcessingException e) {
            log.error("Exception occurred while posting message in the topic");
        }

        return ResponseEntity.status(HttpStatus.OK).body(libraryEvent);
    }

}
