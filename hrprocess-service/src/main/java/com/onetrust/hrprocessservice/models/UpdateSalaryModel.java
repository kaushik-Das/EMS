package com.onetrust.hrprocessservice.models;

public class UpdateSalaryModel {
    private final long employeeId;
    private final double percentage;

    public UpdateSalaryModel(long employeeId, double percentage) {
        this.employeeId = employeeId;
        this.percentage = percentage;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public double getPercentage() {
        return percentage;
    }
}
