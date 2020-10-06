package com.kafka.libraryeventsproducer.producers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.libraryeventsproducer.domain.LibraryEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
public class LibraryEventProducer {

    @Autowired
    private KafkaTemplate<Integer, String> template;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendLibraryEvent(LibraryEvent libraryEvent) throws JsonProcessingException {
        Integer key = libraryEvent.getLibraryEventId();
        String message = objectMapper.writeValueAsString(libraryEvent);
        ListenableFuture<SendResult<Integer, String>> callBack = template.sendDefault(key, message);
        callBack.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("Message delivary failed : {}", throwable.toString());
            }

            // Result contains many details about the partion to which message is deliveried
            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                log.info("Message delivary successfully, Key : {}, Partition : {}", key, result.getRecordMetadata().partition());
            }
        });
    }

}
