package com.onetrust.hrprocessservice.web;

import com.onetrust.hrprocessservice.models.Employee;
import com.onetrust.hrprocessservice.models.UpdateSalaryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@RestController
@RequestMapping(path = "/hr")
public class HRProcessResource {
    private static final String EMPLOYEE_SERVICE = "http://employee-service/employees/";
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping(path = "/updateSalary")
    public ResponseEntity<Employee> updateEmployeeSalary(@RequestBody final UpdateSalaryModel salaryModel) throws URISyntaxException {
        final long empId = salaryModel.getEmployeeId();
        final double percentageHike = salaryModel.getPercentage();
        URI url = new URI(EMPLOYEE_SERVICE + empId);
        RequestEntity<Void> getRequest = RequestEntity.get(url).accept(MediaType.APPLICATION_JSON).build();
        ResponseEntity<Employee> getEmployeeResponse = restTemplate.exchange(getRequest, Employee.class);
        if (getEmployeeResponse.getStatusCode().is2xxSuccessful()) {
            Employee employee = getEmployeeResponse.getBody();
            employee.setSalary(employee.getSalary() + percentageHike * employee.getSalary());
            RequestEntity<Employee> requestEntity = RequestEntity.put(url).accept(MediaType.APPLICATION_JSON)
                    .body(employee, Employee.class);
            ResponseEntity<Employee> response = restTemplate.exchange(requestEntity, Employee.class);
            return response;
        }
        return getEmployeeResponse;
    }
}
