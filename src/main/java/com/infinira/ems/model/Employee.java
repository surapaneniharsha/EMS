package com.infinira.ems.model;

import com.infinira.ems.enums.Gender;
import com.infinira.ems.enums.MaritalStatus;
import java.text.MessageFormat;

public class Employee {
    private int empId;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String gender;
    private String nationalId;
    private String passportId;
    private String citizenship;
    private String maritalStatus;
    private String bloodGroup;
    private String identityMarks;
    private int salary;
    private String jobTitle;
    private boolean disability;
    private String bankName;
    private String accNo;
    private int deptId;

    public Employee() { }

    public Employee(String firstName, String lastName, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;

        validate();
    }

    //  Setters
    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public void setFirstName(String firstName) {
        validate("firstName", firstName);
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        validate("lastName", lastName);
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        validate("phone", phone);
        this.phone = phone;
    }

    public void setEmail(String email) {
        validate("email", email);
        this.email = email;
    }

    public void setGender(String gender) {
        validate("gender", gender);
        this.gender = Gender.getValue(gender);
    }

    public void setNationalId(String nationalId) {
        validate("nationalId", nationalId);
        this.nationalId = nationalId;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;

    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public void setMaritalStatus(String maritalStatus) {
        validate("marital_status", maritalStatus);
        this.maritalStatus = MaritalStatus.getValue(maritalStatus);
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public void setIdentityMarks(String identityMarks) {
        validate("identityMarks", identityMarks);
        this.identityMarks = identityMarks;
    }

    public void setSalary(int salary) {
        validate("salary", salary);
        this.salary = salary;
    }

    public void setJobTitle(String jobTitle) {
        validate("jobTitle", jobTitle);
        this.jobTitle = jobTitle;
    }

    public void setDisability(boolean disability) {
        this.disability = disability;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }


    // Getters
    public int getEmpId() {
        return empId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getNationalId() {
        return nationalId;
    }

    public String getPassportId() {
        return passportId;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getIdentityMarks() {
        return identityMarks;
    }

    public int getSalary() {
        return salary;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public boolean isDisability() {
        return disability;
    }

    public String getBankName() {
        return bankName;
    }

    public String getAccNo() {
        return accNo;
    }

    public int getDeptId() {
        return deptId;
    }

    public void validate() {
        validate("firstName", firstName);
        validate("lastName", lastName);
        validate("phone", phone);
        validate("email", email);
    }

    private static void validate(String paramName, Object paramValue) {
        if(paramValue == null) {
            throw new RuntimeException(MessageFormat.format(EMS_XXX, paramName));
        }
        if(paramValue instanceof String str) {
            if(str.isBlank()) {
                throw new RuntimeException(MessageFormat.format(EMS_XXX, paramName));
            }
        } else if (paramValue instanceof Integer intValue) {
            if (intValue <= 0) {
                throw new RuntimeException(MessageFormat.format(EMS_XXX, paramName));
            }
        
        }
    }

    private static final String EMS_XXX = "Invalid value for {0}";
}
