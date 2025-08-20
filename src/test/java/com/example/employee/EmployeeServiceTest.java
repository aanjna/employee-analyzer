package com.example.employee;

import com.example.employee.model.Employee;
import com.example.employee.service.EmployeeService;
import com.example.employee.util.CsvReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class EmployeeServiceTest {

    @Mock
    CsvReader csvReader;

    @InjectMocks
    EmployeeService service;

    Map<Integer, Employee> employeeMap;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up test employee
        Employee ceo = new Employee(123, "Joe", "Doe", 60000, null);
        Employee m1 = new Employee(124, "Martin", "Chekov", 45000, 123);
        Employee m2 = new Employee(125, "Bob", "Ronstad", 47000, 123);
        Employee e1 = new Employee(300, "Alice", "Hasacat", 50000, 124);
        Employee e2 = new Employee(305, "Brett", "Hardleaf", 34000, 300);

        ceo.addSubordinate(m1);
        ceo.addSubordinate(m2);
        m1.addSubordinate(e1);
        e1.addSubordinate(e2);

        employeeMap = new HashMap<>();
        employeeMap.put(123, ceo);
        employeeMap.put(124, m1);
        employeeMap.put(125, m2);
        employeeMap.put(300, e1);
        employeeMap.put(305, e2);

        when(csvReader.readEmployees("src/main/resources/employees.csv")).thenReturn(employeeMap);

        service.init();
    }

    @Test
    void testGetManagersEarningLessThanExpected() {
        List<Map<String, Object>> result = service.getManagersEarningLessThanExpected();
        assertThat(result).isNotNull();
    }

    @Test
    void testGetManagersEarningMoreThanExpected() {
        List<Map<String, Object>> result = service.getManagersEarningMoreThanExpected();
        assertThat(result).isNotNull();
    }

    @Test
    void testGetEmployeesWithLongReportingLines() {
        List<Map<String, Object>> result = service.getEmployeesWithLongReportingLines(4);
        assertThat(result).isNotNull();
    }
}

