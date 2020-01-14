package com.example.kafkaApplication.services.impl;

import com.example.kafkaApplication.entity.Employee;
import com.example.kafkaApplication.services.KafkaService;
import com.example.kafkaApplication.services.read.JsonRead;
import com.example.kafkaApplication.services.read.XMLRead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
public class KafkaServiceImpl implements KafkaService {

    @Autowired
    KafkaTemplate<String,Employee> kafkaTemplate;
    private static final String topic = "kafka";

    @Override
    public String post(final String message){
        XMLRead read = new XMLRead();
        List<Employee> employee = read.read();
        //employee.setFirstName(message);

        for(int i=0;i<100;i++)
        kafkaTemplate.send(topic,employee.get(i));
        return  "Published successfully";

        
    }

    @Override
    public String postJson(){
        JsonRead jsonRead = new JsonRead();

        List<Employee> employees = jsonRead.JsonReadFunction();
        System.out.println(employees);


        return "Published Successfully";


    }

}
