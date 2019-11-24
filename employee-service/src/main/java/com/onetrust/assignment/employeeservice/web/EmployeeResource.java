package com.onetrust.assignment.employeeservice.web;

import com.onetrust.assignment.employeeservice.constants.ExceptionMessages;
import com.onetrust.assignment.employeeservice.models.Employee;
import com.onetrust.assignment.employeeservice.models.EmployeeRepository;
import com.onetrust.assignment.employeeservice.models.Employees;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeResource {
    @Autowired
    private final EmployeeRepository employeeRepository;

    @Autowired
    private final Logger logger;

    public EmployeeResource(EmployeeRepository employeeRepository, Logger logger) {
        this.employeeRepository = employeeRepository;
        this.logger = logger;
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        return createOrUpdateEmployee(employee);
    }

    @GetMapping
    public ResponseEntity<Employees> getAllEmployees() {
        try {
            List<Employee> allEmployees = employeeRepository.findAll();
            return new ResponseEntity<>(new Employees(allEmployees, allEmployees.size()), HttpStatus.OK);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/{employeeId}")
    public ResponseEntity<Employee> getEmployee(@PathVariable("employeeId") long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (!employee.isPresent()) {
            logger.error(String.format(ExceptionMessages.EMPLOYEE_NOT_FOUND_EXCEPTION, employeeId));
            throw new EmployeeNotFoundException(employeeId);
        }
        return new ResponseEntity<>(employee.get(), HttpStatus.OK);
    }

    @PutMapping(value = "/{employeeId}")
    public ResponseEntity<Employee> updateEmployeeDetails(@PathVariable("employeeId") long employeeId, @Valid @RequestBody Employee employee) {
        final Optional<Employee> oldEmployeeDetails = employeeRepository.findById(employeeId);
        final Employee newEmployeeDetails = oldEmployeeDetails.orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        newEmployeeDetails.setCity(employee.getCity());
        newEmployeeDetails.setAddress(employee.getAddress());
        newEmployeeDetails.setDepartment(employee.getDepartment());
        newEmployeeDetails.setEmpName(employee.getEmpName());
        newEmployeeDetails.setLastDate(employee.getLastDate());
        newEmployeeDetails.setSalary(employee.getSalary());
        newEmployeeDetails.setStartDate(employee.getStartDate());
        newEmployeeDetails.setPhoneNumber(employee.getPhoneNumber());
        return createOrUpdateEmployee(newEmployeeDetails);
    }

    private ResponseEntity<Employee> createOrUpdateEmployee(Employee employee) {
        try {
            return new ResponseEntity<>(employeeRepository.save(employee), HttpStatus.CREATED);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
