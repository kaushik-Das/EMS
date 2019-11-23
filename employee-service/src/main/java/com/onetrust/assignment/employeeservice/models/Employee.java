package com.onetrust.assignment.employeeservice.models;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long empId;

    @Column(name = "emp_name")
    @NotEmpty
    private String empName;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "salary")
    private double salary;

    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date startDate;

    @Column(name = "last_date")
    @Temporal(TemporalType.DATE)
    private Date lastDate;

    @ManyToOne
    @JoinColumn(name = "dept_id")
    private Department department;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(name = "phone_number")
    @NotEmpty
    @Digits(fraction = 0, integer = 10)
    private String phoneNumber;

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", empName='" + empName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", salary=" + salary +
                ", startDate=" + startDate +
                ", lastDate=" + lastDate +
                ", department=" + department +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
