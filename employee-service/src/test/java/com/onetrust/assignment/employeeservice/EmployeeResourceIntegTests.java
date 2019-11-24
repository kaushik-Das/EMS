package com.onetrust.assignment.employeeservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetrust.assignment.employeeservice.models.Department;
import com.onetrust.assignment.employeeservice.models.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = EmployeeServiceApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = {"classpath:application-test.yml", "classpath:bootstrap-test.yml"})
@ActiveProfiles("test")
public class EmployeeResourceIntegTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void test_createEmployee() throws Exception {
        Employee employee = setUpEmployeeWithNullEmpId();
        mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.empId").isNumber())
                .andExpect(jsonPath("$.empName").value("Emp-1"))
                .andExpect(jsonPath("$.salary").value(1000.00))
                .andExpect(jsonPath("$.department.deptId").value(1))
                .andExpect(jsonPath("$.department.deptName").value("IT"));

    }

    @Test
    public void test_createEmployeeWithInvalidDept() throws Exception {
        Employee employee = setUpEmployeeWithNullEmpId();
        employee.setDepartment(null);
        mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void test_getAllEmployees() throws Exception {
        mockMvc.perform(get("/employees").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.size").value(12));
    }

    @Test
    public void test_updateEmployeeWithInvalidEmpId() throws Exception {
        Employee employee = setUpEmployeeWithNullEmpId();
        mockMvc.perform(put("/employees/100").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void test_updateEmployee() throws Exception{
        Employee employee = setUpEmployeeWithNullEmpId();
        employee.setEmpId(1L);
        employee.setEmpName("New-Emp-Name");
        employee.setAddress("New-Adress");
        mockMvc.perform(put("/employees/1").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.empId").value(1))
                .andExpect(jsonPath("$.empName").value(employee.getEmpName()))
                .andExpect(jsonPath("$.salary").value(employee.getSalary()))
                .andExpect(jsonPath("$.department.deptId").value(employee.getDepartment().getDeptId()))
                .andExpect(jsonPath("$.department.deptName").value(employee.getDepartment().getDeptName()));
    }


    private Employee setUpEmployeeWithNullEmpId() {
        Employee employee = new Employee();
        employee.setEmpName("Emp-1");
        employee.setPhoneNumber("1234567891");
        employee.setCity("City-1");
        employee.setAddress("Address-1");
        employee.setStartDate(new Date());
        employee.setSalary(1000.00);
        Department department = new Department();
        department.setDeptId(1);
        department.setDeptName("IT");
        employee.setDepartment(department);
        return employee;
    }
}
