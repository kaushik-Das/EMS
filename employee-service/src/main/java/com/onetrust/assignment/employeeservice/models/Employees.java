package com.onetrust.assignment.employeeservice.models;

import java.util.List;

public class Employees {
    private final List<Employee> employeeList;
    private final int size;

    public Employees(List<Employee> employeeList, int size) {
        this.employeeList = employeeList;
        this.size = size;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public int getSize() {
        return size;
    }
}
