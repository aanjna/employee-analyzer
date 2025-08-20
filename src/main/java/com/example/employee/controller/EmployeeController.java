package com.example.employee.controller;

import com.example.employee.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    private final EmployeeService service;

    public  EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/underpaid-managers")
    public List<Map<String, Object>> underpaidManagers() {
        return service.getManagersEarningLessThanExpected();
    }

    @GetMapping("/overpaid-managers")
    public List<Map<String, Object>> overpaidManagers() {
        return service.getManagersEarningMoreThanExpected();
    }

    @GetMapping("/reporting-line-too-long")
    public List<Map<String, Object>> reportingLineTooLong(@RequestParam(defaultValue = "2") int maxAllowed) {
        //we can take any maxAllowed number here
        return service.getEmployeesWithLongReportingLines(maxAllowed);
    }
}
