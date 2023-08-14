package com.kafka.libraryproducer.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record Book(
        @NotNull
        Integer id,
        @NotBlank
        String name,
        @NotBlank
        String author) {
}
