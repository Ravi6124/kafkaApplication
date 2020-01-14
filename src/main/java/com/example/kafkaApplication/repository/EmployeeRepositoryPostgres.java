package com.example.kafkaApplication.repository;

import com.example.kafkaApplication.entity.EmployeePostgres;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface EmployeeRepositoryPostgres extends CrudRepository<EmployeePostgres,Integer> {

}
