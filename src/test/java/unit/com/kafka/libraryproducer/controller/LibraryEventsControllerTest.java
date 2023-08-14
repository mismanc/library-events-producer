package com.kafka.libraryproducer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.libraryproducer.domain.LibraryEvent;
import com.kafka.libraryproducer.producer.LibraryEventsProducer;
import com.kafka.libraryproducer.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LibraryEventsController.class)
class LibraryEventsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    LibraryEventsProducer libraryEventsProducer;

    @Test
    void createLibraryEvent() throws Exception {

        when(libraryEventsProducer.sendLibraryEventApproach3(isA(LibraryEvent.class)))
                .thenReturn(null);

        String jsonBody = objectMapper.writeValueAsString(TestUtil.libraryEventRecord());

        mockMvc.perform(post("/api/v1/library-event-3").content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Test
    void createLibraryEventInvalid() throws Exception {

        when(libraryEventsProducer.sendLibraryEventApproach3(isA(LibraryEvent.class)))
                .thenReturn(null);

        String jsonBody = objectMapper.writeValueAsString(TestUtil.libraryEventRecordWithInvalid());

        String invalidMessage = "book.id - must not be null,\nbook.name - must not be blank";
        mockMvc.perform(post("/api/v1/library-event-3").content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(invalidMessage));

    }
}
