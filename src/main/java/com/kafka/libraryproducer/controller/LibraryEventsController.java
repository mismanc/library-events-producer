package com.kafka.libraryproducer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kafka.libraryproducer.domain.LibraryEvent;
import com.kafka.libraryproducer.domain.LibraryEventType;
import com.kafka.libraryproducer.producer.LibraryEventsProducer;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<LibraryEvent> createLibraryEvent(@RequestBody @Valid LibraryEvent libraryEvent) {
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
    public ResponseEntity<LibraryEvent> createLibraryEvent2(@RequestBody @Valid LibraryEvent libraryEvent) {
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
    public ResponseEntity<LibraryEvent> createLibraryEvent3(@RequestBody @Valid LibraryEvent libraryEvent) {
        try {
            log.info("library event : {} ", libraryEvent);
            libraryEventsProducer.sendLibraryEventApproach3(libraryEvent);
            log.info("After sending approach3 library event : ");
        } catch (JsonProcessingException | ExecutionException | InterruptedException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }

    @PutMapping("library-event")
    public ResponseEntity<?> updateLibraryEvent3(@RequestBody @Valid LibraryEvent libraryEvent) {
        ResponseEntity<String> notValid = validateLibraryEvent(libraryEvent);
        if (notValid != null) return notValid;
        try {
            log.info("library event : {} ", libraryEvent);
            libraryEventsProducer.sendLibraryEventApproach3(libraryEvent);
            log.info("After sending approach3 library event : ");
        } catch (JsonProcessingException | ExecutionException | InterruptedException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }

    private static ResponseEntity<String> validateLibraryEvent(LibraryEvent libraryEvent) {
        if (libraryEvent.libraryEventId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please pass the libraryEventId");
        }
        if (!libraryEvent.libraryEventType().equals(LibraryEventType.UPDATE)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Only update event type supported");
        }
        return null;
    }


}
