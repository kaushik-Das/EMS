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
        //allowing empId's to be generated from DB/ORM layer.
        employee.setEmpId(null);
        return employeeRepository.save(employee);
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping(value = "/{employeeId}")
    public Employee getEmployee(@PathVariable("employeeId") long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (!employee.isPresent())
            throw new EmployeeNotFoundException(employeeId);
        return employee.get();
    }

    @PutMapping(value = "/{employeeId}")
    public Employee updateEmployeeDetails(@PathVariable("employeeId") long employeeId, @Valid @RequestBody Employee employee) {
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
        return employeeRepository.save(newEmployeeDetails);
    }
}
