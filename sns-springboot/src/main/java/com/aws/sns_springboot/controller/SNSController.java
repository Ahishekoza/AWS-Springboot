package com.aws.sns_springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;
import software.amazon.awssdk.services.sns.model.SubscribeResponse;

@RestController
@RequestMapping("/sns")
public class SNSController {

    @Autowired
    private SnsClient snsClient;

    @Value("${aws.sns.topic-arn}")
    private String topicArn ;

    @GetMapping("/subscribe/{email}")
    public String subscribeToTopic(@PathVariable String email) {
        // Validate email
        if (email == null || email.trim().isEmpty()) {
            return "Error: Email cannot be empty";
        }

        try {
            SubscribeRequest request = SubscribeRequest.builder()
                    .protocol("email")
                    .endpoint(email)
                    .topicArn(topicArn)
                    .build();

            SubscribeResponse response = snsClient.subscribe(request);

            return "Subscription request sent for " + email + ". Subscription ARN: " + response.subscriptionArn();
        } catch (Exception e) {
            return "Error: Failed to subscribe - " + e.getMessage();
        }
    }

    @GetMapping("/publish/{message}")
    public String publishMessageToTopic(@PathVariable String message) {
        try {
            PublishRequest request = PublishRequest.builder()
                    .message(message)
                    .subject("New Message from Spring Boot SNS")
                    .topicArn(topicArn)
                    .build();

            snsClient.publish(request);
            return "Message published to topic: " + topicArn;
        } catch (Exception e) {
            return "Error: Failed to publish message - " + e.getMessage();
        }
    }
}
