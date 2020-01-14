package com.example.kafkaApplication.services.read;

import com.example.kafkaApplication.entity.Employee;
import com.example.kafkaApplication.services.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class CSVRead {
    @Autowired
    private KafkaService kafkaService;
    private List<Employee> employee = new ArrayList<Employee>();
    private static int count=0;
    public Employee read() {
        //todo : move this file name to property file
        String filepath = "/Users/ravikumarjha/Desktop/kafkaApplication/src/main/resources/employee.csv";
        File csvFile = new File(filepath);
        BufferedReader br;
        String line;
        String splitUsing = ",";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                Employee e = new Employee();
                String[] emp = line.split(splitUsing);
                e.setFirstName(emp[0]);
                e.setLastName(emp[1]);
                Date date = (new SimpleDateFormat("MM/dd/yy")).parse(emp[2]);
                e.setDateOfBirth(date);
                e.setExperience(Double.parseDouble(emp[3]));
                //todo : don't add to list, produce message on kafka directly
                //return e;
                employee.add(e);
                //kafkaService.post(csvFile.)
                //System.out.println(emp[0]+ "," + emp[1]+ "," + emp[2]+"," + emp[3]);
                //System.out.println(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return  employee.get(count++);
    }
}
