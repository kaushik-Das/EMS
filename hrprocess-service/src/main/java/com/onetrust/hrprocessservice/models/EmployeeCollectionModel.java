package com.onetrust.hrprocessservice.models;

import java.util.ArrayList;
import java.util.List;

public class EmployeeCollectionModel {
    private List<Employee> employees;

    public EmployeeCollectionModel() {
        this.employees = new ArrayList<>();
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
