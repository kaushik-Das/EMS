package com.onetrust.assignment.employeeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;


@EnableDiscoveryClient
@SpringBootApplication
public class EmployeeServiceApplication {

    @Bean
    Logger logger() {
        return LoggerFactory.getLogger(EmployeeServiceApplication.class);
    }

    /*@Bean
    CommandLineRunner commandLineRunner(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        return employees -> {
            List<Department> departments = new ArrayList<>();
            departments.add(new Department(1, "IT"));
            departments.add(new Department(2, "SALES"));
            departments.add(new Department(3, "FINANCE"));
            departments.add(new Department(4, "TECHNOLOGY"));
            departments.add(new Department(5, "LEADERSHIP"));
            for (Department department : departments) {
                departmentRepository.save(department);
            }
            final int year = 1980;
            for (int i = 1; i <= 10; i++) {
                Employee employee = new Employee();
                    employee.setStartDate(Date.from(LocalDate.of(year + i, i, i).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                employee.setSalary(i * 1000.00);
                employee.setEmpName("Employee_" + i);
                employee.setDepartment(departments.get(Math.min(i - 1, 4)));
                employee.setAddress("Adress_" + i);
                employee.setCity("City_" + i);
                employeeRepository.save(employee);
            }
        };
    }*/


    public static void main(String[] args) {
        SpringApplication.run(EmployeeServiceApplication.class, args);
    }

}
