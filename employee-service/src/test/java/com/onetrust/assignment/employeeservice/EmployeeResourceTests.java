package com.onetrust.assignment.employeeservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetrust.assignment.employeeservice.models.Department;
import com.onetrust.assignment.employeeservice.models.DepartmentRepository;
import com.onetrust.assignment.employeeservice.models.Employee;
import com.onetrust.assignment.employeeservice.models.EmployeeRepository;
import com.onetrust.assignment.employeeservice.web.EmployeeNotFoundException;
import com.onetrust.assignment.employeeservice.web.EmployeeResource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeResource.class)
@ActiveProfiles("test")
class EmployeeResourceTests {

    @Autowired
    MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    EmployeeRepository employeeRepository;

    @MockBean
    DepartmentRepository departmentRepository;

    @Test
    void shouldGetAnEmployeeInJsonFormat() throws Exception {
        Employee employee = setUpEmployee();
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        mvc.perform(get("/employees/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.empId").value(1))
                .andExpect(jsonPath("$.empName").value("Emp-1"))
                .andExpect(jsonPath("$.salary").value(1000.00))
                .andExpect(jsonPath("$.department.deptId").value(1))
                .andExpect(jsonPath("$.department.deptName").value("IT"));
    }

    @Test
    void shouldThrowExceptionWhenEmployeeNotFound() throws Exception {
        Employee employee = setUpEmployee();
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));
        mvc.perform(get("/employees/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

    }

    @Test
    void shouldCreateEmployeeCorrectly() throws Exception {
        Employee employee = setUpEmployee();
        given(employeeRepository.save(any(Employee.class))).willReturn(employee);
        mvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.empId").value(1))
                .andExpect(jsonPath("$.empName").value("Emp-1"))
                .andExpect(jsonPath("$.salary").value(1000.00))
                .andExpect(jsonPath("$.department.deptId").value(1))
                .andExpect(jsonPath("$.department.deptName").value("IT"));
    }

    @Test
    void shouldThrowExceptionOnUpdateEmployeeWhenEmpIdNotFound() throws Exception {
        Employee employee = setUpEmployee();
        given(employeeRepository.findById(any())).willThrow(EmployeeNotFoundException.class);
        mvc.perform(put("/employees/1").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldUpdateEmployeeCorrectly() throws Exception {
        Employee employee = setUpEmployee();
        Employee employee1 = setUpEmployee();
        employee1.setEmpName("NewEmpName");
        given(employeeRepository.findById(anyLong())).willReturn(Optional.of(employee));
        given(employeeRepository.save(any(Employee.class))).willReturn(employee1);
        mvc.perform(put("/employees/1").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(employee1)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.empName").value("NewEmpName"));


    }

    private Employee setUpEmployee() {
        Employee employee = new Employee();
        employee.setEmpId(1L);
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
