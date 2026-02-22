package com.hospital.model;

import java.io.Serializable;
import java.util.Date;

public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long patientId;
    private Long doctorId;
    private Date appointmentDatetime;
    private Integer duration;
    private String status;
    private String reason;
    private String notes;
    private Date createdAt;
    private Date updatedAt;

    // For display purposes
    private String patientName;
    private String doctorName;

    // Constructors
    public Appointment() {
        this.createdAt = new Date();
        this.status = "SCHEDULED";
        this.duration = 30;
    }

    public Appointment(Long patientId, Long doctorId, Date appointmentDatetime) {
        this();
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDatetime = appointmentDatetime;
    }

    // Helper methods
    public boolean isScheduled() {
        return "SCHEDULED".equals(status);
    }

    public boolean isCompleted() {
        return "COMPLETED".equals(status);
    }

    public boolean isCancelled() {
        return "CANCELLED".equals(status);
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Date getAppointmentDatetime() {
        return appointmentDatetime;
    }

    public void setAppointmentDatetime(Date appointmentDatetime) {
        this.appointmentDatetime = appointmentDatetime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
}
