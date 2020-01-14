package com.example.kafkaApplication.services.read;

import com.example.kafkaApplication.entity.Employee;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class JsonRead {


    public List<Employee> JsonReadFunction() {

        Object obj = null;
        try {
            obj = new JSONParser().parse(new FileReader("/Users/thejeshwarreddyyerasi/kafkaApplication/src/main/resources/employee.json"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JSONArray jsonArray = (JSONArray) obj;

        List<Employee> jsonEmployeeData = new ArrayList<Employee>();

        for (int i = 0; i <= 99; i++) {

            JSONObject jsonObject = (JSONObject) jsonArray.get(i);


            Date date = null;
            try {
                date = new SimpleDateFormat("dd/MM/yyyy").parse((String) jsonObject.get("dateOfBirth"));
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
            String firstName = (String) jsonObject.get("firstName");
            String lastName = (String) jsonObject.get("lastName");
            double experience = (long) jsonObject.get("experience");


            Employee employee = new Employee();

            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employee.setDateOfBirth(date);
            employee.setExperience(experience);


            jsonEmployeeData.add(employee);


        }
        return jsonEmployeeData;

    }
}
