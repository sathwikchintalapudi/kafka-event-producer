package com.kafka.libraryeventsproducer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class LibraryEvent {

    private Integer libraryEventId;

    private EventType eventType;

    private Book book;

}
