package com.example.kafkaApplication.repository;

import com.example.kafkaApplication.entity.EmployeeMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepositoryMongo extends MongoRepository<EmployeeMongo,Integer> {

}
