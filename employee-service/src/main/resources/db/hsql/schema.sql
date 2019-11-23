DROP TABLE employees IF EXISTS;
DROP TABLE departments IF EXISTS;

CREATE TABLE departments(
    id INTEGER IDENTITY PRIMARY KEY ,
    name varchar (30)
);
CREATE INDEX departments_id on departments(id);

CREATE TABLE employees(
    id INTEGER IDENTITY PRIMARY KEY ,
    emp_name varchar(30) NOT NULL,
    address varchar(255),
    city varchar(80),
    salary double,
    start_date DATE NOT NULL,
    last_date DATE,
    dept_id INTEGER NOT NULL,
    phone_number varchar(20)
);
CREATE INDEX employees_id ON employees(id);
ALTER TABLE employees ADD CONSTRAINT fk_departments FOREIGN KEY (dept_id) REFERENCES departments(id);

