package com.infinira.ems;

import com.infinira.ems.model.Employee;
import com.infinira.ems.common.MvcRequestTemplate;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.text.MessageFormat;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestEmployee extends MvcRequestTemplate {

    private static final String BASE = "/ems";
    private static final String CREATE_EMP = BASE + "/createEmployee";
    private static final String GET_EMP = BASE + "/getEmployee/{0}";
    private static final String GET_ALL_EMP = BASE + "/getEmployees";
    private static final String UPDATE_EMP = BASE + "/updateEmployee/{0}";
    private static final String DELETE_EMP = BASE + "/deleteEmployee/{0}";

    private static Employee employee;
    private static int empId;

    @Test
    @Order(1)
    public void testCreateEmployee() {

        employee = prepareEmployee();
        assertNotNull(employee, MSG_004);
        String msgOnEx = MessageFormat.format(MSG_007, employee.getEmail());
        String response = postRequest(CREATE_EMP, employee, msgOnEx);

        empId = Integer.parseInt(response.trim());
        assertTrue(empId > 0, MSG_005);
    }

    @Test
    @Order(2)
    public void testGetEmployee() {

        String url = MessageFormat.format(GET_EMP, empId);
        String msgOnEx = MessageFormat.format(MSG_008, empId);
        String response = getRequest(url, msgOnEx);
        Employee fetched = convertJsonToObj(response, Employee.class);

        assertNotNull(fetched, MSG_004);
        assertEquals(empId, fetched.getEmpId(), MSG_003);
        assertEquals(employee.getFirstName(), fetched.getFirstName(), MSG_001);
        assertEquals(employee.getLastName(), fetched.getLastName(), MSG_0015);
        assertEquals(employee.getEmail(), fetched.getEmail(), MSG_0016);
        assertEquals(employee.getPhone(), fetched.getPhone(), MSG_0017);

    }

    @Test
    @Order(3)
    public void testGetAllEmployees() {

        String response = getRequest(GET_ALL_EMP, MSG_013);
        Employee[] empArray = convertJsonToObj(response, Employee[].class);
        assertNotNull(empArray, MSG_004);
        assertTrue(empArray.length > 0, MSG_014);

        boolean found = Arrays.stream(empArray).anyMatch(e -> e.getEmpId() == empId);
        assertTrue(found, MSG_006);
    }

    @Test
    @Order(4)
    public void testUpdateEmployee() {
        String newName = "Updated-" + System.currentTimeMillis();
        employee.setFirstName(newName);

        String url = MessageFormat.format(UPDATE_EMP, empId);
        String msgOnEx = MessageFormat.format(MSG_009, empId);
        String response = putRequest(url, employee, msgOnEx);

        int updateCount = Integer.parseInt(response.trim());
        assertEquals(1, updateCount, MSG_010);
    }


    @Test
    @Order(5)
    public void testDeleteEmployee() {

        String url = MessageFormat.format(DELETE_EMP, empId);
        String msgOnEx = MessageFormat.format(MSG_011, empId);
        String response = deleteRequest(url, msgOnEx);

        int deleteCount = Integer.parseInt(response.trim());
        assertEquals(1, deleteCount, MSG_012);
    }


    private Employee prepareEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("Harish");
        employee.setLastName("Sai");
        employee.setPhone("9876543210");
        employee.setEmail("harish" + System.currentTimeMillis() + "@test.com");
        employee.setGender("MALE");
        employee.setMaritalStatus("SINGLE");
        employee.setJobTitle("Engineer");
        employee.setIdentityMarks("Mole on left eye");
        employee.setNationalId("NID" + System.currentTimeMillis());
        employee.setSalary(50000);
        employee.setDeptId(3);
        return employee;
    }


    private static final String MSG_001 = "Employee first Name mismatch";
    private static final String MSG_003 = "Employee ID mismatch";
    private static final String MSG_004 = "Employee data should not be null";
    private static final String MSG_005 = "Employee ID should be a positive value";
    private static final String MSG_006 = "Created employee should be present in the list";
    private static final String MSG_007 = "Failed to create employee with email: {0}";
    private static final String MSG_008 = "Failed to fetch employee with id: {0}";
    private static final String MSG_009 = "Failed to update employee with id: {0}";
    private static final String MSG_010 = "Employee update count should be 1";
    private static final String MSG_011 = "Failed to delete employee with id: {0}";
    private static final String MSG_012 = "Employee delete count should be 1";
    private static final String MSG_013 = "Failed to fetch all employees";
    private static final String MSG_014 = "Employee list should not be empty";
    private static final String MSG_0015 = "Employee last Name mismatch";
    private static final String MSG_0016 = "Employee email mismatch";
    private static final String MSG_0017 = "Employee phone number mismatch";
}