package com.kafka.cab_book_driver.controller;


import com.kafka.cab_book_driver.service.CabLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cab-location")
public class cabLocationController {

    @Autowired
    private CabLocationService cabLocationService;

    @PutMapping
    public ResponseEntity cabLocationController() throws InterruptedException {
        int range = 100;
        while(range > 0 ){
            cabLocationService.cabLocationService("Cab location update: " + range);
            Thread.sleep(1000); // Simulate delay between location updates
            range --;
        }

        return new ResponseEntity<>("Cab location updates sent successfully", HttpStatus.ACCEPTED);
    }

}
