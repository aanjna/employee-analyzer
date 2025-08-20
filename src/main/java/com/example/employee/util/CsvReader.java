package com.example.employee.util;

import com.example.employee.model.Employee;
import com.opencsv.CSVReader;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.Reader;
import java.util.*;

@Component
public class CsvReader {
    public Map<Integer, Employee> readEmployees(String fileName) {
        Map<Integer, Employee> map = new HashMap<>();

        try (Reader reader = new FileReader(fileName);
             CSVReader csvReader = new CSVReader(reader)) {
            String[] line;
            boolean skipHeader = true;
            while ((line = csvReader.readNext()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }
                int id = Integer.parseInt(line[0]);
                String firstName = line[1];
                String lastName = line[2];
                double salary = Double.parseDouble(line[3]);
                Integer managerId = line.length > 4 && !line[4].isEmpty() ? Integer.valueOf(line[4]) : null;
                map.put(id, new Employee(id, firstName, lastName, salary, managerId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // fill subordinate to manager and employee map
        for (Employee employee : map.values()) {
            if (employee.getManagerId() != null) {
                Employee manager = map.get(employee.getManagerId());
                if (manager != null) {
                    manager.addSubordinate(employee);
                }
            }
        }
        return map;
    }
}