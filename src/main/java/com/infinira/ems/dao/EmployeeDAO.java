package com.infinira.ems.dao;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import com.infinira.ems.util.DBUtil;
import com.infinira.ems.util.Util;
import com.infinira.ems.util.ResourceUtil;
import com.infinira.ems.model.Employee;
import com.infinira.ems.exception.EmsException;

import static java.sql.Types.INTEGER;

@Repository
public class EmployeeDAO {

    public  int create(Employee employee) {
        validate(employee);

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int empId = 0;

        try {
            con = DBUtil.getInstance().getConnection();
            pstmt = con.prepareStatement(INSERT_QUERY);
            setEmpParameters(pstmt, employee);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                empId = rs.getInt("emp_id");
            } else {
                throw Util.createEx("EMS_0002", null, employee.getFirstName(), employee.getLastName(), employee.getPhone());
            }

        } catch (EmsException emsEx) {
            throw emsEx;
        } catch (Throwable th) {
            throw Util.createEx("EMS_0002", th, employee.getFirstName(), employee.getLastName(), employee.getPhone());
        } finally {
            DBUtil.close(rs, pstmt, con);
        }

        return empId;
    }

    public  int update(Employee employee) {
        validate(employee);
        validate(employee.getEmpId());

        Connection con = null;
        PreparedStatement pstmt = null;
        int count = 0;

        try {
            con = DBUtil.getInstance().getConnection();
            pstmt = con.prepareStatement(UPDATE_QUERY);
            int ix = setEmpParameters(pstmt, employee);
            pstmt.setInt(ix, employee.getEmpId());
            count = pstmt.executeUpdate();

            if (count == 0) {
                throw Util.createEx("EMS_0004", null, employee.getEmpId());
            }

        } catch (EmsException emsEx) {
            throw emsEx;
        } catch (Throwable th) {
            throw Util.createEx("EMS_0003", th, employee.getEmpId(), employee.getFirstName());
        } finally {
            DBUtil.close(null, pstmt, con);
        }

        return count;
    }

    public  Employee getById(int empId) {
        validate(empId);

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBUtil.getInstance().getConnection();
            pstmt = con.prepareStatement(GETID_QUERY);
            pstmt.setInt(1, empId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return getEmpParameters(rs);
            } else {
                throw Util.createEx("EMS_0004", null, empId);
            }

        } catch (EmsException emsEx) {
            throw emsEx;
        } catch (Throwable th) {
            throw Util.createEx("EMS_0004", th, empId);
        } finally {
            DBUtil.close(rs, pstmt, con);
        }
    }

    public  List<Employee> getAll() {
        List<Employee> employees = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBUtil.getInstance().getConnection();
            pstmt = con.prepareStatement(GETALL_QUERY);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                employees.add(getEmpParameters(rs));
            }

            if(employees.isEmpty()) {
                throw Util.createEx("EMS_0005", null);
            }

        } catch(EmsException emsEx) {
            throw emsEx;
        }catch (Throwable th) {
            throw Util.createEx("EMS_0005", th);
        } finally {
            DBUtil.close(rs, pstmt, con);
        }

        return employees;
    }

    public  int delete(int empId) {
        validate(empId);

        Connection con = null;
        PreparedStatement pstmt = null;
        int count = 0;

        try {
            con = DBUtil.getInstance().getConnection();
            pstmt = con.prepareStatement(DELETE_QUERY);
            pstmt.setInt(1, empId);
            count = pstmt.executeUpdate();

            if (count == 0) {
                throw Util.createEx("EMS_0004", null, empId);
            }

        } catch (EmsException emsEx) {
            throw emsEx;
        } catch (Throwable th) {
            throw Util.createEx("EMS_0006", th, empId);
        } finally {
            DBUtil.close(null, pstmt, con);
        }

        return count;
    }

    private  int setEmpParameters(PreparedStatement pstmt, Employee employee) throws Throwable {
        int ix = 1;
        pstmt.setString(ix++, employee.getFirstName());
        pstmt.setString(ix++, employee.getLastName());
        pstmt.setString(ix++, employee.getPhone());
        pstmt.setString(ix++, employee.getEmail());
        pstmt.setString(ix++, employee.getGender());
        pstmt.setString(ix++, employee.getNationalId());
        pstmt.setString(ix++, employee.getPassportId());
        pstmt.setString(ix++, employee.getCitizenship());
        pstmt.setString(ix++, employee.getMaritalStatus());
        pstmt.setString(ix++, employee.getBloodGroup());
        pstmt.setString(ix++, employee.getIdentityMarks());
        pstmt.setInt(ix++, employee.getSalary());
        pstmt.setString(ix++, employee.getJobTitle());
        pstmt.setBoolean(ix++, employee.isDisability());
        pstmt.setString(ix++, employee.getBankName());
        pstmt.setString(ix++, employee.getAccNo());
        if (employee.getDeptId() <= 0 ) {
            throw Util.createEx("EMS_0001", null, "Department ID");
        } else {
            pstmt.setInt(ix++, employee.getDeptId());
        }
        return ix;
    }

    private  Employee getEmpParameters(ResultSet rs) throws Throwable {
        Employee employee = new Employee(
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("phone"),
            rs.getString("email")
        );
        employee.setEmpId(rs.getInt("emp_id"));
        employee.setGender(rs.getString("gender"));
        employee.setNationalId(rs.getString("national_id"));
        employee.setPassportId(rs.getString("passport_id"));
        employee.setCitizenship(rs.getString("citizenship"));
        employee.setMaritalStatus(rs.getString("marital_status"));
        employee.setBloodGroup(rs.getString("blood_group"));
        employee.setIdentityMarks(rs.getString("identity_marks"));
        employee.setSalary(rs.getInt("salary"));
        employee.setJobTitle(rs.getString("job_title"));
        employee.setDisability(rs.getBoolean("disability"));
        employee.setBankName(rs.getString("bank_name"));
        employee.setAccNo(rs.getString("acc_no"));
        employee.setDeptId(rs.getInt("dept_id"));
        return employee;
    }

    private static void validate(Object obj) {
        if (obj instanceof Employee) {
            return;
        } else if (obj instanceof Integer empId) {
            if (empId <= 0) {
                throw Util.createEx("EMS_0004", null, empId);
            }
        } else {
            throw Util.createEx("EMS_0001", null);
        }
    }

    private static final String INSERT_QUERY = """ 
    INSERT INTO employee (first_name, last_name, phone, email, gender, national_id, passport_id, citizenship, marital_status, blood_group,
                          identity_marks, salary, job_title, disability, bank_name, acc_no, dept_id) 
    VALUES (?, ?, ?, ?, ?::gender_enum, ?, ?, ?, ?::marital_enum, ?, ?, ?, ?, ?, ?, ?, ?) 
    RETURNING emp_id;
    """;

    private static final String UPDATE_QUERY = """
    UPDATE employee SET
        first_name = ?, last_name = ?, phone = ?, email = ?, gender = ?::gender_enum,
        national_id = ?, passport_id = ?, citizenship = ?, marital_status = ?::marital_enum, blood_group = ?,
        identity_marks = ?, salary = ?, job_title = ?, disability = ?, bank_name = ?, acc_no = ?, dept_id = ?
    WHERE emp_id = ?;
    """;

    private static final String GETID_QUERY = """
    SELECT emp_id, first_name, last_name, phone, email, gender, national_id, passport_id, citizenship, marital_status, blood_group,
           identity_marks, salary, job_title, disability, bank_name, acc_no, dept_id 
    FROM employee 
    WHERE emp_id = ?;
    """;

    private static final String GETALL_QUERY = """
    SELECT emp_id, first_name, last_name, phone, email, gender, national_id, passport_id, citizenship, marital_status, blood_group,
           identity_marks, salary, job_title, disability, bank_name, acc_no, dept_id 
    FROM employee;
    """;

    private static final String DELETE_QUERY = "DELETE FROM employee WHERE emp_id = ?;";
}
