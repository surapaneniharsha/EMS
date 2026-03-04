package com.infinira.ems.service;

import com.infinira.ems.dao.EmployeeDAO;
import com.infinira.ems.model.Employee;
import com.infinira.ems.dao.DepartmentDAO;
import com.infinira.ems.model.Department;
import com.infinira.ems.util.ResourceUtil;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class EmsService {

    @Autowired
    EmployeeDAO employeeDAO;

    @Autowired
    DepartmentDAO departmentDAO;


    public  int createEmployee(Employee employee) {
        return employeeDAO.create(employee);
    }

    public int updateEmployee(int empId, Employee updatedEmployee) {
        Employee existingEmployee = employeeDAO.getById(empId);
        if (existingEmployee == null) {
            throw new RuntimeException(ResourceUtil.getInstance().getMessages("EMS_0004", empId));
        }

        existingEmployee.setFirstName(updatedEmployee.getFirstName());
        existingEmployee.setEmail(updatedEmployee.getEmail());

        return employeeDAO.update(existingEmployee);
    }


    public  Employee getEmployeeById(int empId) {
        return employeeDAO.getById(empId);
    }

    public  List<Employee> getAllEmployees() {
        return employeeDAO.getAll();
    }

    public  int deleteEmployee(int empId) {
        return employeeDAO.delete(empId);
    }


    // Department Table CRUD Operations

    public int createDepartment(Department department) {
        return departmentDAO.create(department);
    }

    public int updateDepartment(int deptId, Department updatedDepartment) {
        Department existingDepartment = departmentDAO.getById(deptId);
        if (existingDepartment == null) {
            throw new RuntimeException(ResourceUtil.getInstance().getMessages("EMS_0010", deptId));
        }


        existingDepartment.setDeptName(updatedDepartment.getDeptName());
        existingDepartment.setLocation(updatedDepartment.getLocation());

        return departmentDAO.update(existingDepartment);
    }


    public  Department getDepartmentById(int deptId) {
        return departmentDAO.getById(deptId);
    }

    public List<Department> getAllDepartments() {
        return departmentDAO.getAll();
    }

    public  int deleteDepartment(int deptId) {
        return departmentDAO.delete(deptId);
    }
}
