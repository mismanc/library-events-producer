package com.kafka.libraryproducer.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.libraryproducer.domain.Book;
import com.kafka.libraryproducer.domain.LibraryEvent;
import com.kafka.libraryproducer.domain.LibraryEventType;

public class TestUtil {

    public static Book bookRecord() {
        return new Book(123, "Islam Between East And West", "Alija Izetbegovic");
    }

    public static Book bookRecordInvalid() {
        return new Book(123, "Alija Izetbegovic", "Islam Between East And West");
    }

    public static LibraryEvent libraryEventRecordWithEventId(Integer eventId) {
        return new LibraryEvent(eventId == null ? 123 : eventId, LibraryEventType.NEW, bookRecord());
    }

    public static LibraryEvent libraryEventRecord() {
        return new LibraryEvent(null, LibraryEventType.NEW, bookRecord());
    }

    public static LibraryEvent libraryEventRecordUpdate() {
        return new LibraryEvent(123, LibraryEventType.UPDATE, bookRecord());
    }

    public static LibraryEvent parseLibraryEventRecord(ObjectMapper objectMapper, String json){
        try {
            return objectMapper.readValue(json, LibraryEvent.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
