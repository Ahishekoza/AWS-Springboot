package com.aws.sqs_springboot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.util.List;

@RestController
@RequestMapping("/sqs")
public class SQSController {

    @Value("${cloud.sqs.queue-url}")
    private String queueUrl;

    @Autowired
    private SqsClient sqsClient;

    @GetMapping("/put/{msg}")
    public String sendMessageToQueue(@PathVariable  String msg){
        try {
            SendMessageRequest request = SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(msg)
                    .build();
            sqsClient.sendMessage(request);
            return "Message sent to SQS queue: " + msg;
        } catch (Exception e) {
            return "Error: Failed to send message - " + e.getMessage();
        }
    }

    @GetMapping("/listen")
    public String receiveMessageFromQueue(){
        // Implement logic to receive messages from the SQS queue
        try {


            ReceiveMessageRequest request = ReceiveMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .maxNumberOfMessages(1)
                    .build();

            List<Message> messages = sqsClient.receiveMessage(request).messages();

            for (Message message : messages) {
                System.out.println("Received message: " + message.body());
                // Optionally, delete the message from the queue after processing
            }
        } catch (Exception e) {
            return "Error: Failed to receive message - " + e.getMessage();
        }
        
        return "Received message from SQS queue";
    }

}
