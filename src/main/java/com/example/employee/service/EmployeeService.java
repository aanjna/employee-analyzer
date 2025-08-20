package com.example.employee.service;


import com.example.employee.model.Employee;
import com.example.employee.util.CsvReader;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeService {

    private final CsvReader csvReader;

    public EmployeeService(CsvReader csvReader) {
        this.csvReader = csvReader;
    }

    private Map<Integer, Employee> employeeMap;

    @PostConstruct
    public void init() {
        this.employeeMap = csvReader.readEmployees("src/main/resources/employees.csv");
    }

    // Helper: Get CEO (no managerId)
    public Employee getCEO() {
        for (Employee emp : employeeMap.values()) {
            if (emp.getManagerId() == null) {
                return emp;
            }
        }
        return null;
    }

    // 1. Managers earning less than 20% above avg subordinate salary
    public List<Map<String, Object>> getManagersEarningLessThanExpected() {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Employee emp : employeeMap.values()) {
            if (!emp.getSubordinates().isEmpty()) {
                double avgSub = emp.getSubordinates().stream().mapToDouble(Employee::getSalary).average().orElse(0);
                double minSalary = avgSub * 1.2;
                if (emp.getSalary() < minSalary) {
                    Map<String, Object> r = new HashMap<>();
                    r.put("manager", emp.getFirstName() + " " + emp.getLastName());
                    r.put("actualSalary", emp.getSalary());
                    r.put("minExpectedSalary", minSalary);
                    r.put("diff", minSalary - emp.getSalary());
                    result.add(r);
                }
            }
        }
        return result;
    }

    // 2. Managers earning more than 50% above avg subordinate salary
    public List<Map<String, Object>> getManagersEarningMoreThanExpected() {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Employee emp : employeeMap.values()) {
            if (!emp.getSubordinates().isEmpty()) {
                double avgSub = emp.getSubordinates().stream().mapToDouble(Employee::getSalary).average().orElse(0);
                double maxSalary = avgSub * 1.5;
                if (emp.getSalary() > maxSalary) {
                    Map<String, Object> r = new HashMap<>();
                    r.put("manager", emp.getFirstName() + " " + emp.getLastName());
                    r.put("actualSalary", emp.getSalary());
                    r.put("maxExpectedSalary", maxSalary);
                    r.put("diff", emp.getSalary() - maxSalary);
                    result.add(r);
                }
            }
        }
        return result;
    }

    // 3. Employees with too long reporting line to CEO
    public List<Map<String, Object>> getEmployeesWithLongReportingLines(int maxAllowed) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Employee emp : employeeMap.values()) {
            int depth = 0;
            Integer mgrId = emp.getManagerId();
            while (mgrId != null) {
                depth++;
                Employee mgr = employeeMap.get(mgrId);
                if (mgr == null) break;
                mgrId = mgr.getManagerId();
            }
            if (depth > maxAllowed) {
                Map<String, Object> r = new HashMap<>();
                r.put("employee", emp.getFirstName() + " " + emp.getLastName());
                r.put("reportingLine", depth);
                r.put("overBy", depth - maxAllowed);
                result.add(r);
            }
        }
        return result;
    }
}
