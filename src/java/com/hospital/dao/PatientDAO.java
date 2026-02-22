package com.hospital.dao;

import com.hospital.model.Patient;
import com.hospital.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class PatientDAO {

    public PatientDAO() {
        // Default constructor
    }

    public void save(Patient patient) throws SQLException {
        String sql = "INSERT INTO patients (first_name, last_name, email, phone, address, date_of_birth, gender, blood_group, emergency_contact_name, emergency_contact_phone, medical_history, allergies, insurance_number, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, patient.getFirstName());
            stmt.setString(2, patient.getLastName());
            stmt.setString(3, patient.getEmail());
            stmt.setString(4, patient.getPhone());
            stmt.setString(5, patient.getAddress());
            stmt.setDate(6, patient.getDateOfBirth() != null ? new java.sql.Date(patient.getDateOfBirth().getTime()) : null);
            stmt.setString(7, patient.getGender());
            stmt.setString(8, patient.getBloodGroup());
            stmt.setString(9, patient.getEmergencyContactName());
            stmt.setString(10, patient.getEmergencyContactPhone());
            stmt.setString(11, patient.getMedicalHistory());
            stmt.setString(12, patient.getAllergies());
            stmt.setString(13, patient.getInsuranceNumber());
            stmt.setTimestamp(14, patient.getCreatedAt() != null
                    ? new Timestamp(patient.getCreatedAt().getTime()) : new Timestamp(System.currentTimeMillis()));

            stmt.executeUpdate();

        } finally {
            if (stmt != null) {
                stmt.close();
            }
            DatabaseUtil.closeConnection(conn);
        }
    }

    public void update(Patient patient) throws SQLException {
        String sql = "UPDATE patients SET first_name=?, last_name=?, email=?, phone=?, address=?, date_of_birth=?, gender=?, blood_group=?, emergency_contact_name=?, emergency_contact_phone=?, medical_history=?, allergies=?, insurance_number=?, updated_at=? WHERE id=?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, patient.getFirstName());
            stmt.setString(2, patient.getLastName());
            stmt.setString(3, patient.getEmail());
            stmt.setString(4, patient.getPhone());
            stmt.setString(5, patient.getAddress());
            stmt.setDate(6, patient.getDateOfBirth() != null ? new java.sql.Date(patient.getDateOfBirth().getTime()) : null);
            stmt.setString(7, patient.getGender());
            stmt.setString(8, patient.getBloodGroup());
            stmt.setString(9, patient.getEmergencyContactName());
            stmt.setString(10, patient.getEmergencyContactPhone());
            stmt.setString(11, patient.getMedicalHistory());
            stmt.setString(12, patient.getAllergies());
            stmt.setString(13, patient.getInsuranceNumber());
            stmt.setTimestamp(14, new Timestamp(new Date().getTime()));
            stmt.setLong(15, patient.getId());

            stmt.executeUpdate();

        } finally {
            if (stmt != null) {
                stmt.close();
            }
            DatabaseUtil.closeConnection(conn);
        }
    }

    public List<Patient> findAll() throws SQLException {
        String sql = "SELECT * FROM patients ORDER BY first_name, last_name";
        List<Patient> patients = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Patient patient = new Patient();
                patient.setId(rs.getLong("id"));
                patient.setFirstName(rs.getString("first_name"));
                patient.setLastName(rs.getString("last_name"));
                patient.setEmail(rs.getString("email"));
                patient.setPhone(rs.getString("phone"));
                patient.setAddress(rs.getString("address"));
                patient.setDateOfBirth(rs.getDate("date_of_birth"));
                patient.setGender(rs.getString("gender"));
                patient.setBloodGroup(rs.getString("blood_group"));
                patient.setEmergencyContactName(rs.getString("emergency_contact_name"));
                patient.setEmergencyContactPhone(rs.getString("emergency_contact_phone"));
                patient.setMedicalHistory(rs.getString("medical_history"));
                patient.setAllergies(rs.getString("allergies"));
                patient.setInsuranceNumber(rs.getString("insurance_number"));
                patient.setCreatedAt(rs.getTimestamp("created_at"));

                patients.add(patient);
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

        return patients;
    }

    public Patient findById(Long id) throws SQLException {
        String sql = "SELECT * FROM patients WHERE id = ?";
        Patient patient = null;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                patient = new Patient();
                patient.setId(rs.getLong("id"));
                patient.setFirstName(rs.getString("first_name"));
                patient.setLastName(rs.getString("last_name"));
                patient.setEmail(rs.getString("email"));
                patient.setPhone(rs.getString("phone"));
                patient.setAddress(rs.getString("address"));
                patient.setDateOfBirth(rs.getDate("date_of_birth"));
                patient.setGender(rs.getString("gender"));
                patient.setBloodGroup(rs.getString("blood_group"));
                patient.setEmergencyContactName(rs.getString("emergency_contact_name"));
                patient.setEmergencyContactPhone(rs.getString("emergency_contact_phone"));
                patient.setMedicalHistory(rs.getString("medical_history"));
                patient.setAllergies(rs.getString("allergies"));
                patient.setInsuranceNumber(rs.getString("insurance_number"));
                patient.setCreatedAt(rs.getTimestamp("created_at"));
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

        return patient;
    }

    public void delete(Long patientId) throws SQLException {
        String sql = "DELETE FROM patients WHERE id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, patientId);
            stmt.executeUpdate();

        } finally {
            if (stmt != null) {
                stmt.close();
            }
            DatabaseUtil.closeConnection(conn);
        }
    }

    public List<Patient> findByName(String name) throws SQLException {
        String sql = "SELECT * FROM patients WHERE LOWER(CONCAT(first_name, ' ', last_name)) LIKE LOWER(?) ORDER BY first_name, last_name";
        List<Patient> patients = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + name + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Patient patient = new Patient();
                patient.setId(rs.getLong("id"));
                patient.setFirstName(rs.getString("first_name"));
                patient.setLastName(rs.getString("last_name"));
                patient.setEmail(rs.getString("email"));
                patient.setPhone(rs.getString("phone"));
                patient.setAddress(rs.getString("address"));
                patient.setDateOfBirth(rs.getDate("date_of_birth"));
                patient.setGender(rs.getString("gender"));
                patient.setBloodGroup(rs.getString("blood_group"));
                patient.setEmergencyContactName(rs.getString("emergency_contact_name"));
                patient.setEmergencyContactPhone(rs.getString("emergency_contact_phone"));
                patient.setMedicalHistory(rs.getString("medical_history"));
                patient.setAllergies(rs.getString("allergies"));
                patient.setInsuranceNumber(rs.getString("insurance_number"));
                patient.setCreatedAt(rs.getTimestamp("created_at"));

                patients.add(patient);
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

        return patients;
    }
}
