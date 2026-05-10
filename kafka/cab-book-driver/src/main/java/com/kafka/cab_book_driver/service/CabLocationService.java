package com.kafka.cab_book_driver.service;

import com.kafka.cab_book_driver.constant.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CabLocationService {

    @Autowired
    public KafkaTemplate<String,Object> kafkaTemplate;

    public boolean cabLocationService(String location){
        kafkaTemplate.send(AppConstant.TOPIC_NAME,location);
        return true;
    }
}
