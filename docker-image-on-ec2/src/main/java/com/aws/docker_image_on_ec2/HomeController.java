package com.aws.docker_image_on_ec2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HomeController {

    @GetMapping("/check")
    public String dockerCheck(){
        return "Docker is working fine on EC2 instance!";
    }

}
