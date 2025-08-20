package com.example.employee;

import com.example.employee.service.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private EmployeeService employeeService;

    @Test
    @DisplayName("Test reporting line too long with maxAllowed=2")
    void testReportingLineTooLongWithParam() throws Exception {
        mockMvc.perform(get("/api/reporting-line-too-long").param("maxAllowed", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].overBy", greaterThanOrEqualTo(1)));
    }

    @Test
    public void testUnderpaidManagers() throws Exception {
        List<Map<String, Object>> sampleResponse = new ArrayList<>();
        Map<String, Object> m = new HashMap<>();
        m.put("manager", "John Manager");
        m.put("actualSalary", 45000);
        m.put("minExpectedSalary", 50000);
        m.put("diff", 5000);
        sampleResponse.add(m);

        when(employeeService.getManagersEarningLessThanExpected()).thenReturn(sampleResponse);

        mockMvc.perform(get("/api/underpaid-managers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].manager").value("John Manager"))
                .andExpect(jsonPath("$.diff").value(5000));
    }
}

