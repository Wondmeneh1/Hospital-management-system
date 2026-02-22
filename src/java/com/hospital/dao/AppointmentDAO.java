package com.hospital.dao;

import com.hospital.model.Appointment;
import com.hospital.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Calendar;

public class AppointmentDAO {

    public void save(Appointment appointment) throws SQLException {
        String sql = "INSERT INTO appointments (patient_id, doctor_id, appointment_datetime, duration, status, reason, notes, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, appointment.getPatientId());
            stmt.setLong(2, appointment.getDoctorId());
            stmt.setTimestamp(3, new Timestamp(appointment.getAppointmentDatetime().getTime()));
            stmt.setInt(4, appointment.getDuration());
            stmt.setString(5, appointment.getStatus());
            stmt.setString(6, appointment.getReason());
            stmt.setString(7, appointment.getNotes());
            stmt.setTimestamp(8, new Timestamp(appointment.getCreatedAt().getTime()));

            stmt.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            DatabaseUtil.closeConnection(conn);
        }
    }

    public void update(Appointment appointment) throws SQLException {
        String sql = "UPDATE appointments SET patient_id=?, doctor_id=?, appointment_datetime=?, duration=?, status=?, reason=?, notes=?, updated_at=? WHERE id=?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, appointment.getPatientId());
            stmt.setLong(2, appointment.getDoctorId());
            stmt.setTimestamp(3, new Timestamp(appointment.getAppointmentDatetime().getTime()));
            stmt.setInt(4, appointment.getDuration());
            stmt.setString(5, appointment.getStatus());
            stmt.setString(6, appointment.getReason());
            stmt.setString(7, appointment.getNotes());
            stmt.setTimestamp(8, new Timestamp(new Date().getTime()));
            stmt.setLong(9, appointment.getId());

            stmt.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            DatabaseUtil.closeConnection(conn);
        }
    }

    public List<Appointment> findAll() throws SQLException {
        String sql = "SELECT a.*, "
                + "CONCAT(p.first_name, ' ', p.last_name) as patient_name, "
                + "CONCAT(d.first_name, ' ', d.last_name) as doctor_name "
                + "FROM appointments a "
                + "LEFT JOIN patients p ON a.patient_id = p.id "
                + "LEFT JOIN doctors d ON a.doctor_id = d.id "
                + "ORDER BY a.appointment_datetime DESC";

        List<Appointment> appointments = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(rs.getLong("id"));
                appointment.setPatientId(rs.getLong("patient_id"));
                appointment.setDoctorId(rs.getLong("doctor_id"));
                appointment.setAppointmentDatetime(rs.getTimestamp("appointment_datetime"));
                appointment.setDuration(rs.getInt("duration"));
                appointment.setStatus(rs.getString("status"));
                appointment.setReason(rs.getString("reason"));
                appointment.setNotes(rs.getString("notes"));
                appointment.setCreatedAt(rs.getTimestamp("created_at"));
                appointment.setUpdatedAt(rs.getTimestamp("updated_at"));
                appointment.setPatientName(rs.getString("patient_name"));
                appointment.setDoctorName(rs.getString("doctor_name"));
                appointments.add(appointment);
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

        return appointments;
    }

    public Appointment findById(Long id) throws SQLException {
        String sql = "SELECT a.*, "
                + "CONCAT(p.first_name, ' ', p.last_name) as patient_name, "
                + "CONCAT(d.first_name, ' ', d.last_name) as doctor_name "
                + "FROM appointments a "
                + "LEFT JOIN patients p ON a.patient_id = p.id "
                + "LEFT JOIN doctors d ON a.doctor_id = d.id "
                + "WHERE a.id = ?";

        Appointment appointment = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                appointment = new Appointment();
                appointment.setId(rs.getLong("id"));
                appointment.setPatientId(rs.getLong("patient_id"));
                appointment.setDoctorId(rs.getLong("doctor_id"));
                appointment.setAppointmentDatetime(rs.getTimestamp("appointment_datetime"));
                appointment.setDuration(rs.getInt("duration"));
                appointment.setStatus(rs.getString("status"));
                appointment.setReason(rs.getString("reason"));
                appointment.setNotes(rs.getString("notes"));
                appointment.setCreatedAt(rs.getTimestamp("created_at"));
                appointment.setUpdatedAt(rs.getTimestamp("updated_at"));
                appointment.setPatientName(rs.getString("patient_name"));
                appointment.setDoctorName(rs.getString("doctor_name"));
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

        return appointment;
    }

    public void delete(Long appointmentId) throws SQLException {
        String sql = "DELETE FROM appointments WHERE id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, appointmentId);
            stmt.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            DatabaseUtil.closeConnection(conn);
        }
    }

    public List<Appointment> findTodayAppointments() throws SQLException {
        String sql = "SELECT a.*, "
                + "CONCAT(p.first_name, ' ', p.last_name) as patient_name, "
                + "CONCAT(d.first_name, ' ', d.last_name) as doctor_name "
                + "FROM appointments a "
                + "LEFT JOIN patients p ON a.patient_id = p.id "
                + "LEFT JOIN doctors d ON a.doctor_id = d.id "
                + "WHERE DATE(a.appointment_datetime) = CURDATE() "
                + "ORDER BY a.appointment_datetime ASC";

        List<Appointment> appointments = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(rs.getLong("id"));
                appointment.setPatientId(rs.getLong("patient_id"));
                appointment.setDoctorId(rs.getLong("doctor_id"));
                appointment.setAppointmentDatetime(rs.getTimestamp("appointment_datetime"));
                appointment.setDuration(rs.getInt("duration"));
                appointment.setStatus(rs.getString("status"));
                appointment.setReason(rs.getString("reason"));
                appointment.setNotes(rs.getString("notes"));
                appointment.setCreatedAt(rs.getTimestamp("created_at"));
                appointment.setUpdatedAt(rs.getTimestamp("updated_at"));
                appointment.setPatientName(rs.getString("patient_name"));
                appointment.setDoctorName(rs.getString("doctor_name"));
                appointments.add(appointment);
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

        return appointments;
    }
}
