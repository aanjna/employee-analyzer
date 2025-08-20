package com.example.employee;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test underpaid managers")
    void testUnderpaidManagers() throws Exception {
        mockMvc.perform(get("/api/underpaid-managers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                // Check object structure, e.g., "manager" and "diff"
                .andExpect(jsonPath("$[0].manager").exists())
                .andExpect(jsonPath("$.diff", greaterThan(0.0)));
    }

    @Test
    @DisplayName("Test overpaid managers")
    void testOverpaidManagers() throws Exception {
        mockMvc.perform(get("/api/overpaid-managers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.manager").exists())
                .andExpect(jsonPath("$.diff", greaterThan(0.0)));
    }

    @Test
    @DisplayName("Test reporting line too long with default (4)")
    void testReportingLineTooLongDefault() throws Exception {
        mockMvc.perform(get("/api/reporting-line-too-long"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].employee").exists())
                .andExpect(jsonPath("$.overBy").isNumber());
    }

    @Test
    @DisplayName("Test reporting line too long with maxAllowed=2")
    void testReportingLineTooLongWithParam() throws Exception {
        mockMvc.perform(get("/api/reporting-line-too-long").param("maxAllowed", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].overBy", greaterThanOrEqualTo(1)));
    }
}

