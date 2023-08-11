package com.kafka.libraryproducer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.libraryproducer.domain.LibraryEvent;
import com.kafka.libraryproducer.util.TestUtil;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@EmbeddedKafka(topics = "library-events")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LibraryEventsControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    ObjectMapper objectMapper;

    private Consumer<Integer, String> consumer;

    @BeforeEach
    void setUp() {
        Map<String, Object> kafkaUtils = new HashMap<>(KafkaTestUtils.consumerProps("group1", "true", embeddedKafkaBroker));
        kafkaUtils.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        consumer = new DefaultKafkaConsumerFactory<>(kafkaUtils, new IntegerDeserializer(), new StringDeserializer()).createConsumer();
        embeddedKafkaBroker.consumeFromAllEmbeddedTopics(consumer);
    }

    @AfterEach
    void tearDown() {
        consumer.close();
    }

    @Test
    void createLibraryEvent() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<LibraryEvent> httpEntity = new HttpEntity<>(TestUtil.libraryEventRecord(), httpHeaders);
        ResponseEntity<LibraryEvent> responseEntity = restTemplate.exchange("/api/v1/library-event", HttpMethod.POST,
                httpEntity, LibraryEvent.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        ConsumerRecords<Integer, String> records = KafkaTestUtils.getRecords(consumer);
        assertEquals(1, records.count());
        records.forEach(rec -> {
            LibraryEvent actual = TestUtil.parseLibraryEventRecord(objectMapper, rec.value());
            System.out.println("actual: " + actual);
            assertEquals(actual, TestUtil.libraryEventRecord());
        });
    }

    @Test
    void createLibraryEvent2() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<LibraryEvent> httpEntity = new HttpEntity<>(TestUtil.libraryEventRecord(), httpHeaders);
        ResponseEntity<LibraryEvent> responseEntity = restTemplate.exchange("/api/v1/library-event-2", HttpMethod.POST,
                httpEntity, LibraryEvent.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        ConsumerRecords<Integer, String> records = KafkaTestUtils.getRecords(consumer);
        assertEquals(1, records.count());
        records.forEach(rec -> {
            LibraryEvent actual = TestUtil.parseLibraryEventRecord(objectMapper, rec.value());
            System.out.println("actual: " + actual);
            assertEquals(actual, TestUtil.libraryEventRecord());
        });

    }

    @Test
    void createLibraryEvent3() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<LibraryEvent> httpEntity = new HttpEntity<>(TestUtil.libraryEventRecord(), httpHeaders);
        ResponseEntity<LibraryEvent> responseEntity = restTemplate.exchange("/api/v1/library-event-3", HttpMethod.POST,
                httpEntity, LibraryEvent.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());


    }
}
