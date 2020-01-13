package com.example.kafkaApplication.controller;

//import com.example.kafkaApplication.entity.Employee;
import com.example.kafkaApplication.services.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    KafkaService kafkaService;

    @GetMapping("/publish/{message}")
    public String post(@PathVariable("message") final String message){
        return kafkaService.post(message);
    }

}
