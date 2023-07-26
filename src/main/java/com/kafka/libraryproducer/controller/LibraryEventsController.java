package com.kafka.libraryproducer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kafka.libraryproducer.domain.LibraryEvent;
import com.kafka.libraryproducer.producer.LibraryEventsProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1")
public class LibraryEventsController {

    private final LibraryEventsProducer libraryEventsProducer;

    public LibraryEventsController(LibraryEventsProducer libraryEventsProducer) {
        this.libraryEventsProducer = libraryEventsProducer;
    }

    @PostMapping("library-event")
    public ResponseEntity<LibraryEvent> createLibraryEvent(@RequestBody LibraryEvent libraryEvent) {
        log.info("library event : {} ", libraryEvent);
        try {
            libraryEventsProducer.sendLibraryEvent(libraryEvent);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info("After sending library event : ");
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }


}
