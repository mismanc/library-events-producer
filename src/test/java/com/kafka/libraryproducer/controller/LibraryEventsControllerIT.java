package com.kafka.libraryproducer.controller;

import com.kafka.libraryproducer.domain.LibraryEvent;
import com.kafka.libraryproducer.util.TestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("local")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LibraryEventsControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void createLibraryEvent() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<LibraryEvent> httpEntity = new HttpEntity<>(TestUtil.libraryEventRecord(), httpHeaders);
        ResponseEntity<LibraryEvent> responseEntity = restTemplate.exchange("/api/v1/library-event", HttpMethod.POST,
                httpEntity, LibraryEvent.class);

        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());


    }
}
