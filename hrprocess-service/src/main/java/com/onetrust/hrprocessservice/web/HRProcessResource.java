package com.onetrust.hrprocessservice.web;

import com.onetrust.hrprocessservice.models.Employee;
import com.onetrust.hrprocessservice.models.UpdateSalaryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path = "/hr")
public class HRProcessResource {
    private static final String EMPLOYEE_SERVICE = "http://employee-service/employees/";
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping(path = "/updateSalary")
    public void updateEmployeeSalary(@RequestBody final UpdateSalaryModel salaryModel) {
        final long empId = salaryModel.getEmployeeId();
        final double percentageHike = salaryModel.getPercentage();
        Employee employee = fetchEmployeeDetails(empId);
        employee.setSalary(employee.getSalary() + percentageHike * employee.getSalary());
        restTemplate.put(EMPLOYEE_SERVICE + empId, employee, empId);
    }

    private Employee fetchEmployeeDetails(long empId) {
        return restTemplate.getForObject(EMPLOYEE_SERVICE + empId, Employee.class);
    }

}
