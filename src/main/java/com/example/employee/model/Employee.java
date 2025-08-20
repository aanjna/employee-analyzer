package com.example.employee.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Employee {

    private int id;
    private String firstName;
    private String lastName;
    private double salary;
    private Integer managerId;
    private final List<Employee> subordinates = new ArrayList<>();

    public Employee(int id, String firstName, String lastName, double salary, Integer managerId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.managerId = managerId;
    }

    public void addSubordinate(Employee employee) {
        subordinates.add(employee);
    }

}
