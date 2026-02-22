package com.hospital.dao;

import com.hospital.model.Department;
import com.hospital.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class DepartmentDAO {

    public void save(Department department) throws SQLException {
        String sql = "INSERT INTO departments (name, description, head_doctor, location, phone, email, status, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, department.getName());
            stmt.setString(2, department.getDescription());
            stmt.setString(3, department.getHeadDoctor());
            stmt.setString(4, department.getLocation());
            stmt.setString(5, department.getPhone());
            stmt.setString(6, department.getEmail());
            stmt.setString(7, department.getStatus());
            stmt.setTimestamp(8, new Timestamp(department.getCreatedAt().getTime()));

            stmt.executeUpdate();

        } finally {
            if (stmt != null) {
                stmt.close();
            }
            DatabaseUtil.closeConnection(conn);
        }
    }

    public void update(Department department) throws SQLException {
        String sql = "UPDATE departments SET name=?, description=?, head_doctor=?, location=?, phone=?, email=?, status=?, updated_at=? WHERE id=?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, department.getName());
            stmt.setString(2, department.getDescription());
            stmt.setString(3, department.getHeadDoctor());
            stmt.setString(4, department.getLocation());
            stmt.setString(5, department.getPhone());
            stmt.setString(6, department.getEmail());
            stmt.setString(7, department.getStatus());
            stmt.setTimestamp(8, new Timestamp(new Date().getTime()));
            stmt.setLong(9, department.getId());

            stmt.executeUpdate();

        } finally {
            if (stmt != null) {
                stmt.close();
            }
            DatabaseUtil.closeConnection(conn);
        }
    }

    public List<Department> findAll() throws SQLException {
        String sql = "SELECT * FROM departments ORDER BY name";
        List<Department> departments = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Department department = new Department();
                department.setId(rs.getLong("id"));
                department.setName(rs.getString("name"));
                department.setDescription(rs.getString("description"));
                department.setHeadDoctor(rs.getString("head_doctor"));
                department.setLocation(rs.getString("location"));
                department.setPhone(rs.getString("phone"));
                department.setEmail(rs.getString("email"));
                department.setStatus(rs.getString("status"));
                department.setCreatedAt(rs.getTimestamp("created_at"));
                department.setUpdatedAt(rs.getTimestamp("updated_at"));

                departments.add(department);
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            DatabaseUtil.closeConnection(conn);
        }

        return departments;
    }

    public Department findById(Long id) throws SQLException {
        String sql = "SELECT * FROM departments WHERE id = ?";
        Department department = null;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                department = new Department();
                department.setId(rs.getLong("id"));
                department.setName(rs.getString("name"));
                department.setDescription(rs.getString("description"));
                department.setHeadDoctor(rs.getString("head_doctor"));
                department.setLocation(rs.getString("location"));
                department.setPhone(rs.getString("phone"));
                department.setEmail(rs.getString("email"));
                department.setStatus(rs.getString("status"));
                department.setCreatedAt(rs.getTimestamp("created_at"));
                department.setUpdatedAt(rs.getTimestamp("updated_at"));
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            DatabaseUtil.closeConnection(conn);
        }

        return department;
    }

    public void delete(Long departmentId) throws SQLException {
        String sql = "DELETE FROM departments WHERE id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, departmentId);
            stmt.executeUpdate();

        } finally {
            if (stmt != null) {
                stmt.close();
            }
            DatabaseUtil.closeConnection(conn);
        }
    }

    public List<Department> findByName(String name) throws SQLException {
        String sql = "SELECT * FROM departments WHERE LOWER(name) LIKE LOWER(?) ORDER BY name";
        List<Department> departments = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + name + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Department department = new Department();
                department.setId(rs.getLong("id"));
                department.setName(rs.getString("name"));
                department.setDescription(rs.getString("description"));
                department.setHeadDoctor(rs.getString("head_doctor"));
                department.setLocation(rs.getString("location"));
                department.setPhone(rs.getString("phone"));
                department.setEmail(rs.getString("email"));
                department.setStatus(rs.getString("status"));
                department.setCreatedAt(rs.getTimestamp("created_at"));
                department.setUpdatedAt(rs.getTimestamp("updated_at"));

                departments.add(department);
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            DatabaseUtil.closeConnection(conn);
        }

        return departments;
    }
}
