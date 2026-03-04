package com.infinira.ems;

import com.infinira.ems.model.Department;
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
public class TestDepartment extends MvcRequestTemplate {

    private static final String BASE = "/ems";
    private static final String CREATE_DEPT = BASE + "/createDepartment";
    private static final String GET_DEPT = BASE + "/getDepartment/{0}";
    private static final String GET_ALL_DEPTS = BASE + "/getDepartments";
    private static final String UPDATE_DEPT = BASE + "/updateDepartment/{0}";
    private static final String DELETE_DEPT = BASE + "/deleteDepartment/{0}";

    private static Department department;
    private static int deptId;

    @Test
    @Order(1)
    public void testCreateDepartment() {
        department = prepareDepartment();
        assertNotNull(department, MSG_004);
        String msgOnEx = MessageFormat.format(MSG_007, department.getDeptName());
        String response = postRequest(CREATE_DEPT, department, msgOnEx);

        deptId = Integer.parseInt(response.trim());
        assertTrue(deptId > 0, MSG_005);
    }


    @Test
    @Order(2)
    public void testGetDepartment() {
        String url = MessageFormat.format(GET_DEPT, deptId);
        String msgOnEx = MessageFormat.format(MSG_008, deptId);
        String response = getRequest(url, msgOnEx);
        Department fetched = convertJsonToObj(response, Department.class);

        assertNotNull(fetched, MSG_004);
        assertEquals(deptId, fetched.getDeptId(), MSG_003);
        assertEquals(department.getDeptName(), fetched.getDeptName(), MSG_001);
        assertEquals(department.getLocation(), fetched.getLocation(), MSG_002);
    }

    @Test
    @Order(3)
    public void testGetAllDepartments() {

        String response = getRequest(GET_ALL_DEPTS, MSG_013);
        Department[] deptArray = convertJsonToObj(response, Department[].class);

        assertNotNull(deptArray, MSG_004);
        assertTrue(deptArray.length > 0, MSG_014);

        boolean exist = Arrays.stream(deptArray).anyMatch(d -> d.getDeptId() == deptId);
        assertTrue(exist, MSG_006);
    }

    @Test
    @Order(4)
    public void testUpdateDepartment() {

        String newName = "Updated-" + System.currentTimeMillis();
        department.setDeptName(newName);
        String url = MessageFormat.format(UPDATE_DEPT, deptId);
        String msgOnEx = MessageFormat.format(MSG_009, deptId);
        String response = putRequest(url, department, msgOnEx);

        int updateCount = Integer.parseInt(response.trim());
        assertEquals(1, updateCount, MSG_010);
    }

    @Test
    @Order(5)
    public void testDeleteDepartment() {

        String url = MessageFormat.format(DELETE_DEPT, deptId);
        String msgOnEx = MessageFormat.format(MSG_011, deptId);

        String response = deleteRequest(url, msgOnEx);

        int deleteCount = Integer.parseInt(response.trim());
        assertEquals(1, deleteCount, MSG_012);
    }

    private Department prepareDepartment() {
        Department dept = new Department();
        dept.setDeptName("Engineering-" + System.currentTimeMillis());
        dept.setLocation("Hyderabad");
        return dept;
    }


    private static final String MSG_001 = "Department Name mismatch";
    private static final String MSG_002 = "Department Location mismatch";
    private static final String MSG_003 = "Department ID mismatch";
    private static final String MSG_004 = "Department data should not be null";
    private static final String MSG_005 = "Department ID should be a positive value";
    private static final String MSG_007 = "Failed to create department with name: {0}";
    private static final String MSG_008 = "Failed to fetch department with id: {0}";
    private static final String MSG_009 = "Failed to update department with id: {0}";
    private static final String MSG_010 = "Department update count should be 1";
    private static final String MSG_011 = "Failed to delete department with id: {0}";
    private static final String MSG_012 = "Department delete count should be 1";
    private static final String MSG_006 = "Created department should be present in the list";
    private static final String MSG_013 = "Failed to fetch all departments";
    private static final String MSG_014 = "Department list should not be empty";
}