package com.infinira.ems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.infinira.ems.model.Employee;
import com.infinira.ems.model.Department;
import com.infinira.ems.service.EmsService;

import java.util.List;

@RestController
@RequestMapping(EmsController.ROOT_PATH)
public class EmsController {

    public static final String ROOT_PATH = "/ems";

    @Autowired
    private EmsService emsService;

    // --- Employee Endpoints ---

    @PostMapping("/createEmployee")
    public ResponseEntity<Integer> createEmployee(@RequestBody Employee employee) {
        int generatedId = emsService.createEmployee(employee);
        return new ResponseEntity<>(generatedId, HttpStatus.CREATED);
    }

    @PutMapping("/updateEmployee/{empId}")
    public ResponseEntity<Integer> updateEmployee(@PathVariable int empId, @RequestBody Employee employee) {
        int updateCount = emsService.updateEmployee(empId, employee);
        return ResponseEntity.ok(updateCount);
    }

    @GetMapping("/getEmployee/{empId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int empId) {
        Employee employee = emsService.getEmployeeById(empId);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/getEmployees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = emsService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @DeleteMapping("/deleteEmployee/{empId}")
    public ResponseEntity<Integer> deleteEmployee(@PathVariable int empId) {
        int delCount = emsService.deleteEmployee(empId);
        return ResponseEntity.ok(delCount);
    }

    // --- Department Endpoints ---

    @PostMapping("/createDepartment")
    public ResponseEntity<Integer> createDepartment(@RequestBody Department department) {
        int deptId = emsService.createDepartment(department);
        return new ResponseEntity<>(deptId, HttpStatus.CREATED);
    }

    @PutMapping("/updateDepartment/{deptId}")
    public ResponseEntity<Integer> updateDepartment(@PathVariable int deptId, @RequestBody Department department) {
        int updateCount = emsService.updateDepartment(deptId, department);
        return ResponseEntity.ok(updateCount);
    }

    @GetMapping("/getDepartment/{deptId}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable int deptId) {
        Department department = emsService.getDepartmentById(deptId);
        return ResponseEntity.ok(department);
    }

    @GetMapping("/getDepartments")
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = emsService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }

    @DeleteMapping("/deleteDepartment/{deptId}")
    public ResponseEntity<Integer> deleteDepartment(@PathVariable int deptId) {
        int deleteCount = emsService.deleteDepartment(deptId);
        return ResponseEntity.ok(deleteCount);
    }
}