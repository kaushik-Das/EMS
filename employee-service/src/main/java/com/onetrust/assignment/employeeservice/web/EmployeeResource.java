package com.onetrust.assignment.employeeservice.web;

import com.onetrust.assignment.employeeservice.models.Employee;
import com.onetrust.assignment.employeeservice.models.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeResource {
    @Autowired
    private final EmployeeRepository employeeRepository;


    public EmployeeResource(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@Valid @RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping(value = "/{employeeId}")
    public Optional<Employee> getEmployee(@PathVariable("employeeId") long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    @PutMapping(value = "/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateEmployeeDetails(@PathVariable("employeeId") long employeeId, @Valid @RequestBody Employee employee) {
        final Optional<Employee> oldEmployeeDetails = employeeRepository.findById(employeeId);
        final Employee newEmployeeDetails = oldEmployeeDetails.orElseThrow(() -> new ResourceNotFoundException("Employee " + employeeId + " not found"));
        newEmployeeDetails.setCity(employee.getCity());
        newEmployeeDetails.setAddress(employee.getAddress());
        newEmployeeDetails.setDepartment(employee.getDepartment());
        newEmployeeDetails.setEmpName(employee.getEmpName());
        newEmployeeDetails.setLastDate(employee.getLastDate());
        newEmployeeDetails.setSalary(employee.getSalary());
        newEmployeeDetails.setStartDate(employee.getStartDate());
        employeeRepository.save(newEmployeeDetails);
    }
}
