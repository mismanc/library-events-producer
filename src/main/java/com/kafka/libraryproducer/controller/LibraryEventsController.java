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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

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
        try {
            log.info("library event : {} ", libraryEvent);
            libraryEventsProducer.sendLibraryEvent(libraryEvent);
            log.info("After sending library event : ");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }

    @PostMapping("library-event-2")
    public ResponseEntity<LibraryEvent> createLibraryEvent2(@RequestBody LibraryEvent libraryEvent) {
        try {
            log.info("library event : {} ", libraryEvent);
            libraryEventsProducer.sendLibraryEventApproach2(libraryEvent);
            log.info("After sending approach2 library event : ");
        } catch (JsonProcessingException | ExecutionException | InterruptedException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }

    @PostMapping("library-event-3")
    public ResponseEntity<LibraryEvent> createLibraryEvent3(@RequestBody LibraryEvent libraryEvent) {
        try {
            log.info("library event : {} ", libraryEvent);
            libraryEventsProducer.sendLibraryEventApproach3(libraryEvent);
            log.info("After sending approach2 library event : ");
        } catch (JsonProcessingException | ExecutionException | InterruptedException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }


}
