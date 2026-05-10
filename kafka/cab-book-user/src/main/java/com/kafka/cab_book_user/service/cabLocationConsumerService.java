package com.kafka.cab_book_user.service;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class cabLocationConsumerService {

    @KafkaListener(topics = "cab-book-driver", groupId = "cab-book-user-group")
    public void consumeCabLocation(String location) {
        System.out.println("Received cab location: " + location);
    }


}
