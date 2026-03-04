   CREATE TYPE gender_enum AS ENUM ('MALE', 'FEMALE', 'OTHERS');
   CREATE TYPE marital_enum AS ENUM ('MARRIED', 'SINGLE');

  
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

CREATE TABLE department (
    dept_id SERIAL PRIMARY KEY,
    dept_name VARCHAR(100) NOT NULL UNIQUE,
    location VARCHAR(100)
);
