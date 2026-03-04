package com.infinira.ems.dao;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.infinira.ems.model.Department;
import com.infinira.ems.util.DBUtil;
import com.infinira.ems.util.Util;
import com.infinira.ems.exception.EmsException;

@Repository
public class DepartmentDAO {

    public static int create(Department department) {
        validate(department);

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int deptId = 0;

        try {
            con = DBUtil.getInstance().getConnection();
            pstmt = con.prepareStatement(INSERT_QUERY);
            setDeptParameters(pstmt, department);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                deptId = rs.getInt("dept_id");
            } else {
                throw Util.createEx("EMS_0008", null, department.getDeptName());
            }

        } catch (EmsException emsEx) {
            throw emsEx;
        } catch (Throwable th) {
            throw Util.createEx("EMS_0008", th, department.getDeptName());
        } finally {
            DBUtil.close(rs, pstmt, con);
        }

        return deptId;
    }

    public static int update(Department department) {
        validate(department);
        validate(department.getDeptId());

        Connection con = null;
        PreparedStatement pstmt = null;
        int count = 0;

        try {
            con = DBUtil.getInstance().getConnection();
            pstmt = con.prepareStatement(UPDATE_QUERY);
            int ix = setDeptParameters(pstmt, department);
            pstmt.setInt(ix, department.getDeptId());
            count = pstmt.executeUpdate();

            if (count == 0) {
                throw Util.createEx("EMS_0009", null, department.getDeptId());
            }

        } catch (EmsException emsEx) {
            throw emsEx;
        } catch (Throwable th) {
            throw Util.createEx("EMS_0009", th, department.getDeptId());
        } finally {
            DBUtil.close(null, pstmt, con);
        }

        return count;
    }

    public static Department getById(int deptId) {
        validate(deptId);

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBUtil.getInstance().getConnection();
            pstmt = con.prepareStatement(GETID_QUERY);
            pstmt.setInt(1, deptId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return getDeptParameters(rs);
            } else {
                throw Util.createEx("EMS_0010", null, deptId);
            }

        } catch (EmsException emsEx) {
            throw emsEx;
        } catch (Throwable th) {
            throw Util.createEx("EMS_0010", th, deptId);
        } finally {
            DBUtil.close(rs, pstmt, con);
        }
    }

    public static List<Department> getAll() {
        List<Department> departments = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBUtil.getInstance().getConnection();
            pstmt = con.prepareStatement(GETALL_QUERY);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                departments.add(getDeptParameters(rs));
            }

            if(departments.isEmpty()) {
                throw Util.createEx("EMS_0011", null);
            }

        } catch(EmsException emsEx) {
            throw emsEx;
        } catch (Throwable th) {
            throw Util.createEx("EMS_0011", th);
        } finally {
            DBUtil.close(rs, pstmt, con);
        }

        return departments;
    }

    public static int delete(int deptId) {
        validate(deptId);

        Connection con = null;
        PreparedStatement pstmt = null;
        int count = 0;

        try {
            con = DBUtil.getInstance().getConnection();
            pstmt = con.prepareStatement(DELETE_QUERY);
            pstmt.setInt(1, deptId);
            count = pstmt.executeUpdate();

            if (count == 0) {
                throw Util.createEx("EMS_0010", null, deptId);
            }

        } catch (EmsException emsEx) {
            throw emsEx;
        } catch (Throwable th) {
            throw Util.createEx("EMS_0012", th, deptId);
        } finally {
            DBUtil.close(null, pstmt, con);
        }

        return count;
    }

    private static int setDeptParameters(PreparedStatement pstmt, Department department) throws Throwable {
        int ix = 1;
        pstmt.setString(ix++, department.getDeptName());
        pstmt.setString(ix++, department.getLocation());
        return ix;
    }

    private static Department getDeptParameters(ResultSet rs) throws Throwable {
        // Using default constructor to avoid validation logic during DB mapping
        Department department = new Department();
        department.setDeptId(rs.getInt("dept_id"));
        department.setDeptName(rs.getString("dept_name"));
        department.setLocation(rs.getString("location"));
        return department;
    }

    private static void validate(Object obj) {
        if (obj instanceof Department) {
            return;
        } else if (obj instanceof Integer id) {
            if (id <= 0) {
                throw Util.createEx("EMS_0010", null, id);
            }
        } else {
            throw Util.createEx("EMS_0007", null);
        }
    }

    // --- UPDATED QUERIES ---
    private static final String INSERT_QUERY = """
        INSERT INTO department (dept_name, location) VALUES (?, ?)
        RETURNING dept_id;
    """;

    private static final String UPDATE_QUERY = """
        UPDATE department SET dept_name = ?, location = ?
        WHERE dept_id = ?;
    """;

    private static final String GETID_QUERY = """
        SELECT dept_id, dept_name, location
        FROM department
        WHERE dept_id = ?;
    """;

    private static final String GETALL_QUERY = """
        SELECT dept_id, dept_name, location
        FROM department;
    """;

    private static final String DELETE_QUERY = "DELETE FROM department WHERE dept_id = ?;";
}