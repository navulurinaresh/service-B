package com.example.demo.controller;

import com.example.demo.service.EmployeeService;
import com.example.demo.vo.EmployeeVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private EmployeeVO testEmployeeVO;

    @BeforeEach
    void setUp() {
        testEmployeeVO = new EmployeeVO();
        testEmployeeVO.setId(1);
        testEmployeeVO.setName("John Doe");
        testEmployeeVO.setAge(30);
        testEmployeeVO.setSalary(50000);
        testEmployeeVO.setGender("Male");
    }

    @Test
    void testUpdateEmployee_Success() throws Exception {
        // Given
        EmployeeVO updatedEmployee = new EmployeeVO();
        updatedEmployee.setId(1);
        updatedEmployee.setName("John Doe Updated");
        updatedEmployee.setAge(31);
        updatedEmployee.setSalary(55000);
        updatedEmployee.setGender("Male");

        when(employeeService.updateEmployee(eq(1), any(EmployeeVO.class)))
                .thenReturn(updatedEmployee);

        // When & Then
        mockMvc.perform(put("/employee/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedEmployee)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe Updated"))
                .andExpect(jsonPath("$.age").value(31))
                .andExpect(jsonPath("$.salary").value(55000))
                .andExpect(jsonPath("$.gender").value("Male"));
    }


    @Test
    void testUpdateEmployee_WithAllFields() throws Exception {
        // Given
        EmployeeVO fullUpdate = new EmployeeVO();
        fullUpdate.setId(1);
        fullUpdate.setName("Jane Smith");
        fullUpdate.setAge(35);
        fullUpdate.setSalary(70000);
        fullUpdate.setGender("Female");

        when(employeeService.updateEmployee(eq(1), any(EmployeeVO.class)))
                .thenReturn(fullUpdate);

        // When & Then
        mockMvc.perform(put("/employee/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fullUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane Smith"))
                .andExpect(jsonPath("$.age").value(35))
                .andExpect(jsonPath("$.salary").value(70000))
                .andExpect(jsonPath("$.gender").value("Female"));
    }
}
