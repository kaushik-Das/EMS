package com.onetrust.assignment.employeeservice.web;

import com.onetrust.assignment.employeeservice.constants.ExceptionMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(long empId) {
        super(String.format(ExceptionMessages.EMPLOYEE_NOT_FOUND_EXCEPTION, empId));
    }

}
