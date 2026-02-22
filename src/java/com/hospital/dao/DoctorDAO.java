package com.hospital.dao;

import com.hospital.model.Doctor;
import com.hospital.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class DoctorDAO {
    
    public void save(Doctor doctor) throws SQLException {
        String sql = "INSERT INTO doctors (first_name, last_name, email, phone, specialization, license_number, department_id, address, date_of_birth, gender, hire_date, years_of_experience, status, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, doctor.getFirstName());
            stmt.setString(2, doctor.getLastName());
            stmt.setString(3, doctor.getEmail());
            stmt.setString(4, doctor.getPhone());
            stmt.setString(5, doctor.getSpecialization());
            stmt.setString(6, doctor.getLicenseNumber());
            if (doctor.getDepartmentId() != null) {
                stmt.setLong(7, doctor.getDepartmentId());
            } else {
                stmt.setNull(7, Types.BIGINT);
            }
            stmt.setString(8, doctor.getAddress());
            stmt.setDate(9, doctor.getDateOfBirth() != null ? new java.sql.Date(doctor.getDateOfBirth().getTime()) : null);
            stmt.setString(10, doctor.getGender());
            stmt.setDate(11, doctor.getHireDate() != null ? new java.sql.Date(doctor.getHireDate().getTime()) : null);
            stmt.setInt(12, doctor.getYearsOfExperience());
            stmt.setString(13, doctor.getStatus());
            stmt.setTimestamp(14, new Timestamp(doctor.getCreatedAt().getTime()));
            
            stmt.executeUpdate();
            
        } finally {
            if (stmt != null) stmt.close();
            DatabaseUtil.closeConnection(conn);
        }
    }
    
    public void update(Doctor doctor) throws SQLException {
        String sql = "UPDATE doctors SET first_name=?, last_name=?, email=?, phone=?, specialization=?, license_number=?, department_id=?, address=?, date_of_birth=?, gender=?, hire_date=?, years_of_experience=?, status=?, updated_at=? WHERE id=?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, doctor.getFirstName());
            stmt.setString(2, doctor.getLastName());
            stmt.setString(3, doctor.getEmail());
            stmt.setString(4, doctor.getPhone());
            stmt.setString(5, doctor.getSpecialization());
            stmt.setString(6, doctor.getLicenseNumber());
            if (doctor.getDepartmentId() != null) {
                stmt.setLong(7, doctor.getDepartmentId());
            } else {
                stmt.setNull(7, Types.BIGINT);
            }
            stmt.setString(8, doctor.getAddress());
            stmt.setDate(9, doctor.getDateOfBirth() != null ? new java.sql.Date(doctor.getDateOfBirth().getTime()) : null);
            stmt.setString(10, doctor.getGender());
            stmt.setDate(11, doctor.getHireDate() != null ? new java.sql.Date(doctor.getHireDate().getTime()) : null);
            stmt.setInt(12, doctor.getYearsOfExperience());
            stmt.setString(13, doctor.getStatus());
            stmt.setTimestamp(14, new Timestamp(new Date().getTime()));
            stmt.setLong(15, doctor.getId());
            
            stmt.executeUpdate();
            
        } finally {
            if (stmt != null) stmt.close();
            DatabaseUtil.closeConnection(conn);
        }
    }
    
    public List<Doctor> findAll() throws SQLException {
        String sql = "SELECT * FROM doctors ORDER BY first_name, last_name";
        List<Doctor> doctors = new ArrayList<>();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Doctor doctor = new Doctor();
                doctor.setId(rs.getLong("id"));
                doctor.setFirstName(rs.getString("first_name"));
                doctor.setLastName(rs.getString("last_name"));
                doctor.setEmail(rs.getString("email"));
                doctor.setPhone(rs.getString("phone"));
                doctor.setSpecialization(rs.getString("specialization"));
                doctor.setLicenseNumber(rs.getString("license_number"));
                doctor.setDepartmentId(rs.getLong("department_id"));
                doctor.setAddress(rs.getString("address"));
                doctor.setDateOfBirth(rs.getDate("date_of_birth"));
                doctor.setGender(rs.getString("gender"));
                doctor.setHireDate(rs.getDate("hire_date"));
                doctor.setYearsOfExperience(rs.getInt("years_of_experience"));
                doctor.setStatus(rs.getString("status"));
                doctor.setCreatedAt(rs.getTimestamp("created_at"));
                doctor.setUpdatedAt(rs.getTimestamp("updated_at"));
                
                doctors.add(doctor);
            }
            
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DatabaseUtil.closeConnection(conn);
        }
        
        return doctors;
    }
    
    public Doctor findById(Long id) throws SQLException {
        String sql = "SELECT * FROM doctors WHERE id = ?";
        Doctor doctor = null;
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                doctor = new Doctor();
                doctor.setId(rs.getLong("id"));
                doctor.setFirstName(rs.getString("first_name"));
                doctor.setLastName(rs.getString("last_name"));
                doctor.setEmail(rs.getString("email"));
                doctor.setPhone(rs.getString("phone"));
                doctor.setSpecialization(rs.getString("specialization"));
                doctor.setLicenseNumber(rs.getString("license_number"));
                doctor.setDepartmentId(rs.getLong("department_id"));
                doctor.setAddress(rs.getString("address"));
                doctor.setDateOfBirth(rs.getDate("date_of_birth"));
                doctor.setGender(rs.getString("gender"));
                doctor.setHireDate(rs.getDate("hire_date"));
                doctor.setYearsOfExperience(rs.getInt("years_of_experience"));
                doctor.setStatus(rs.getString("status"));
                doctor.setCreatedAt(rs.getTimestamp("created_at"));
                doctor.setUpdatedAt(rs.getTimestamp("updated_at"));
            }
            
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DatabaseUtil.closeConnection(conn);
        }
        
        return doctor;
    }
    
    public void delete(Long doctorId) throws SQLException {
        String sql = "DELETE FROM doctors WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, doctorId);
            stmt.executeUpdate();
            
        } finally {
            if (stmt != null) stmt.close();
            DatabaseUtil.closeConnection(conn);
        }
    }
    
    public List<Doctor> findByName(String name) throws SQLException {
        String sql = "SELECT * FROM doctors WHERE LOWER(CONCAT(first_name, ' ', last_name)) LIKE LOWER(?) ORDER BY first_name, last_name";
        List<Doctor> doctors = new ArrayList<>();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + name + "%");
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Doctor doctor = new Doctor();
                doctor.setId(rs.getLong("id"));
                doctor.setFirstName(rs.getString("first_name"));
                doctor.setLastName(rs.getString("last_name"));
                doctor.setEmail(rs.getString("email"));
                doctor.setPhone(rs.getString("phone"));
                doctor.setSpecialization(rs.getString("specialization"));
                doctor.setLicenseNumber(rs.getString("license_number"));
                doctor.setDepartmentId(rs.getLong("department_id"));
                doctor.setAddress(rs.getString("address"));
                doctor.setDateOfBirth(rs.getDate("date_of_birth"));
                doctor.setGender(rs.getString("gender"));
                doctor.setHireDate(rs.getDate("hire_date"));
                doctor.setYearsOfExperience(rs.getInt("years_of_experience"));
                doctor.setStatus(rs.getString("status"));
                doctor.setCreatedAt(rs.getTimestamp("created_at"));
                doctor.setUpdatedAt(rs.getTimestamp("updated_at"));
                
                doctors.add(doctor);
            }
            
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DatabaseUtil.closeConnection(conn);
        }
        
        return doctors;
    }
    
    public List<Doctor> findByDepartment(Long departmentId) throws SQLException {
        String sql = "SELECT * FROM doctors WHERE department_id = ? ORDER BY first_name, last_name";
        List<Doctor> doctors = new ArrayList<>();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, departmentId);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Doctor doctor = new Doctor();
                doctor.setId(rs.getLong("id"));
                doctor.setFirstName(rs.getString("first_name"));
                doctor.setLastName(rs.getString("last_name"));
                doctor.setEmail(rs.getString("email"));
                doctor.setPhone(rs.getString("phone"));
                doctor.setSpecialization(rs.getString("specialization"));
                doctor.setLicenseNumber(rs.getString("license_number"));
                doctor.setDepartmentId(rs.getLong("department_id"));
                doctor.setAddress(rs.getString("address"));
                doctor.setDateOfBirth(rs.getDate("date_of_birth"));
                doctor.setGender(rs.getString("gender"));
                doctor.setHireDate(rs.getDate("hire_date"));
                doctor.setYearsOfExperience(rs.getInt("years_of_experience"));
                doctor.setStatus(rs.getString("status"));
                doctor.setCreatedAt(rs.getTimestamp("created_at"));
                doctor.setUpdatedAt(rs.getTimestamp("updated_at"));
                
                doctors.add(doctor);
            }
            
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DatabaseUtil.closeConnection(conn);
        }
        
        return doctors;
    }

    public List<Doctor> findActiveDoctor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}