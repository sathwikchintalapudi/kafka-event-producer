package com.kafka.libraryeventsproducer.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Book {

    private String bookName;

    private String bookId;

    private String bookAuthor;

}
