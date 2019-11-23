CREATE DATABASE IF NOT EXISTS employeedb;
GRANT ALL PRIVILEGES ON employeedb.* TO root@localhost;

USE employeedb;

CREATE TABLE IF NOT EXISTS departments(
    id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name varchar(30),
    INDEX(id)
) engine = InnoDB;

CREATE TABLE IF NOT EXISTS employees(
    id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    emp_name varchar(30) NOT NULL,
    address varchar(255),
    city varchar(80),
    salary double,
    start_date DATE NOT NULL,
    last_date DATE,
    dept_id INT(4) UNSIGNED NOT NULL,
    phone_number varchar(20),
    INDEX(id),
    FOREIGN KEY (dept_id) REFERENCES departments(id)
) engine = InnoDB;