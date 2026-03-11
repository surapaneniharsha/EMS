-- =====================================
-- Drop Child Table first (due to FK)
-- =====================================
DROP TABLE IF EXISTS employee;

-- =====================================
-- Drop Parent Table
-- =====================================
DROP TABLE IF EXISTS department;

-- =====================================
-- Drop Enums
-- =====================================
DROP TYPE IF EXISTS gender_enum;
DROP TYPE IF EXISTS marital_enum;

-- =====================================
-- Create Enums
-- =====================================
CREATE TYPE gender_enum AS ENUM ('MALE', 'FEMALE', 'OTHERS');
CREATE TYPE marital_enum AS ENUM ('MARRIED', 'SINGLE');

-- =====================================
-- Create Department Table
-- =====================================
CREATE TABLE department (
    dept_id SERIAL PRIMARY KEY,
    dept_name VARCHAR(100) NOT NULL UNIQUE,
    location VARCHAR(100)
);

-- =====================================
-- Create Employee Table
-- =====================================
CREATE TABLE employee (
    emp_id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    email VARCHAR(254) UNIQUE NOT NULL,
    gender gender_enum NOT NULL,
    national_id VARCHAR(20) UNIQUE NOT NULL,
    passport_id VARCHAR(15),
    citizenship CHAR(2),
    marital_status marital_enum NOT NULL,
    blood_group VARCHAR(3),
    identity_marks VARCHAR(75) NOT NULL,
    salary INT NOT NULL,
    job_title VARCHAR(50) NOT NULL,
    disability BOOLEAN DEFAULT FALSE,
    bank_name VARCHAR(75),
    acc_no VARCHAR(34),
    dept_id INT,
    
    CONSTRAINT fk_department
        FOREIGN KEY (dept_id)
        REFERENCES department(dept_id)
        ON DELETE SET NULL
);

-- =====================================
-- Insert Departments
-- =====================================
INSERT INTO department (dept_name, location) VALUES
('Human Resources', 'New York'),
('Finance', 'London'),
('Engineering', 'San Francisco'),
('Marketing', 'Chicago'),
('Sales', 'Berlin'),
('Operations', 'Tokyo'),
('Research', 'Boston'),
('IT Support', 'Sydney'),
('Legal', 'Toronto'),
('Customer Service', 'Dubai');

-- =====================================
-- Insert Employees
-- =====================================
INSERT INTO employee 
(first_name, last_name, phone, email, gender, national_id, passport_id, citizenship, marital_status, blood_group, identity_marks, salary, job_title, disability, bank_name, acc_no, dept_id)
VALUES
('John','Doe','555-0100','john.doe@example.com','MALE','NID1001','P1001','US','SINGLE','O+','Mole on cheek',70000,'HR Manager',FALSE,'Bank of America','US123456789',1),
('Anna','Clark','555-0101','anna.clark@example.com','FEMALE','NID1002','P1002','US','MARRIED','A+','Tattoo on wrist',65000,'HR Specialist',FALSE,'Bank of America','US223456789',1),
('Robert','Hall','555-0102','robert.hall@example.com','MALE','NID1003','P1003','US','SINGLE','B+','Scar on hand',60000,'Recruiter',FALSE,'Bank of America','US323456789',1),
('Laura','Young','555-0103','laura.young@example.com','FEMALE','NID1004','P1004','US','MARRIED','AB+','Mole on arm',62000,'HR Coordinator',FALSE,'Bank of America','US423456789',1),
('David','King','555-0104','david.king@example.com','MALE','NID1005','P1005','US','SINGLE','O-','Birthmark on neck',58000,'HR Assistant',FALSE,'Bank of America','US523456789',1),

('Jane','Smith','555-0105','jane.smith@example.com','FEMALE','NID1006','P1006','GB','MARRIED','A+','Scar on hand',80000,'Finance Analyst',FALSE,'HSBC','GB987654321',2),
('Paul','Wright','555-0106','paul.wright@example.com','MALE','NID1007','P1007','GB','SINGLE','B+','Mole on cheek',75000,'Accountant',FALSE,'HSBC','GB876543210',2),
('Emily','Scott','555-0107','emily.scott@example.com','FEMALE','NID1008','P1008','GB','MARRIED','AB+','Tattoo on arm',72000,'Finance Coordinator',FALSE,'HSBC','GB765432109',2),
('Mark','Evans','555-0108','mark.evans@example.com','MALE','NID1009','P1009','GB','SINGLE','O-','Scar on forehead',71000,'Budget Analyst',FALSE,'HSBC','GB654321098',2),
('Sophia','Morris','555-0110','sophia.morris@example.com','FEMALE','NID1010','P1010','GB','MARRIED','A-','Mole on hand',68000,'Financial Auditor',FALSE,'HSBC','GB543210987',2),

('Michael','Brown','555-0111','michael.brown@example.com','MALE','NID1011','P1011','US','SINGLE','B+','Tattoo on arm',95000,'Software Engineer',FALSE,'Chase Bank','US234567890',3),
('Linda','Green','555-0112','linda.green@example.com','FEMALE','NID1012','P1012','US','MARRIED','O+','Scar on neck',90000,'Frontend Developer',FALSE,'Chase Bank','US334567890',3),
('Kevin','Baker','555-0113','kevin.baker@example.com','MALE','NID1013','P1013','US','SINGLE','AB+','Mole on hand',88000,'Backend Developer',FALSE,'Chase Bank','US434567890',3),
('Rachel','Adams','555-0114','rachel.adams@example.com','FEMALE','NID1014','P1014','US','MARRIED','A+','Tattoo on leg',87000,'DevOps Engineer',FALSE,'Chase Bank','US534567890',3),
('Brian','Mitchell','555-0115','brian.mitchell@example.com','MALE','NID1015','P1015','US','SINGLE','B-','Scar on arm',86000,'QA Engineer',FALSE,'Chase Bank','US634567890',3);
