package com.example.kafkaApplication.services.impl;

import com.example.kafkaApplication.entity.Employee;
import com.example.kafkaApplication.entity.EmployeeMongo;
import com.example.kafkaApplication.entity.EmployeePostgres;
import com.example.kafkaApplication.repository.EmployeeRepositoryMongo;
import com.example.kafkaApplication.repository.EmployeeRepositoryPostgres;
import com.example.kafkaApplication.services.KafkaService;
import com.example.kafkaApplication.services.read.CSVRead;
import com.example.kafkaApplication.services.read.JsonRead;
import com.example.kafkaApplication.services.read.XMLRead;
import net.minidev.json.writer.BeansMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
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
    private static int count =0;

    @Autowired
    EmployeeRepositoryMongo employeeRepositoryMongo;
    @Autowired
    EmployeeRepositoryPostgres employeeRepositoryPostgres;


    @Override
    public String post(final String message){
        XMLRead read = new XMLRead();
        for(int file=0;file<100;file++)
        kafkaTemplate.send(topic,read.read());


//        JsonRead jsonRead = new JsonRead();
//        for(int i=0;i<100;i++)
//            kafkaTemplate.send(topic,employees.get(i));

        CSVRead csvRead = new CSVRead();
        for(int file=0;file<100;file++)
        {
            kafkaTemplate.send(topic,csvRead.read());
        }

        return  "Published successfully";

    }

    @KafkaListener(topics = "kafkaListen",groupId = "group_id")
    public void consume(String message){
        System.out.println("Consumed message "+message);
    }

    @KafkaListener(topics=topic,groupId = "group_id",containerFactory = "employeeKafkaListenerContainerFactory")
    public void consume(Employee employee){
        //add to postgre and mongo
        if(count<150){
        EmployeeMongo employeeMongo = new EmployeeMongo();
        BeanUtils.copyProperties(employee,employeeMongo);
        employeeRepositoryMongo.insert(employeeMongo);
        }
        else{
            EmployeePostgres employeePostgres = new EmployeePostgres();
            BeanUtils.copyProperties(employee,employeePostgres);
            employeeRepositoryPostgres.save(employeePostgres);
        }
        count++;
        System.out.println(employee.toString());

    }

}
