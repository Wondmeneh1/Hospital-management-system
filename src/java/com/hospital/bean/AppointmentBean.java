package com.hospital.bean;

import com.hospital.dao.AppointmentDAO;
import com.hospital.dao.DoctorDAO;
import com.hospital.dao.PatientDAO;
import com.hospital.model.Appointment;
import com.hospital.model.Doctor;
import com.hospital.model.Patient;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@ManagedBean(name = "appointmentBean")
@SessionScoped
public class AppointmentBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Appointment appointment = new Appointment();
    private List<Appointment> appointments = new ArrayList<>();
    private List<Appointment> filteredAppointments = new ArrayList<>();
    private List<Doctor> doctors = new ArrayList<>();
    private List<Patient> patients = new ArrayList<>();

    private AppointmentDAO appointmentDAO = new AppointmentDAO();
    private DoctorDAO doctorDAO = new DoctorDAO();
    private PatientDAO patientDAO = new PatientDAO();

    private String searchTerm = "";
    private String statusFilter = "";

    // Status options
    private List<String> statusOptions = Arrays.asList("SCHEDULED", "COMPLETED", "CANCELLED", "NO_SHOW");

    public AppointmentBean() {
        loadAppointments();
        loadDoctors();
        loadPatients();
    }

    public void loadAppointments() {
        try {
            appointments = appointmentDAO.findAll();
            filteredAppointments = new ArrayList<>(appointments);
        } catch (SQLException e) {
            addErrorMessage("Error loading appointments: " + e.getMessage());
        }
    }

    public void loadDoctors() {
        try {
            doctors = doctorDAO.findAll();
        } catch (SQLException e) {
            addErrorMessage("Error loading doctors: " + e.getMessage());
        }
    }

    public void loadPatients() {
        try {
            patients = patientDAO.findAll();
        } catch (SQLException e) {
            addErrorMessage("Error loading patients: " + e.getMessage());
        }
    }

    public String saveAppointment() {
        try {
            if (appointment.getId() == null) {
                appointmentDAO.save(appointment);
                addMessage("Appointment created successfully!");
            } else {
                appointmentDAO.update(appointment);
                addMessage("Appointment updated successfully!");
            }
            appointment = new Appointment();
            loadAppointments();
            return "appointments?faces-redirect=true";
        } catch (SQLException e) {
            addErrorMessage("Error saving appointment: " + e.getMessage());
            return null;
        }
    }

    public String editAppointment(Appointment appointmentToEdit) {
        try {
            this.appointment = appointmentDAO.findById(appointmentToEdit.getId());
            if (this.appointment == null) {
                this.appointment = new Appointment();
                addErrorMessage("Appointment not found!");
                return null;
            }
            return "appointment-form?faces-redirect=true";
        } catch (SQLException e) {
            addErrorMessage("Error loading appointment: " + e.getMessage());
            return null;
        }
    }

    public void deleteAppointment(Appointment appointmentToDelete) {
        try {
            appointmentDAO.delete(appointmentToDelete.getId());
            addMessage("Appointment deleted successfully!");
            loadAppointments();
        } catch (SQLException e) {
            addErrorMessage("Error deleting appointment: " + e.getMessage());
        }
    }

    public void cancelAppointment(Appointment appointmentToCancel) {
        try {
            appointmentToCancel.setStatus("CANCELLED");
            appointmentDAO.update(appointmentToCancel);
            addMessage("Appointment cancelled successfully!");
            loadAppointments();
        } catch (SQLException e) {
            addErrorMessage("Error cancelling appointment: " + e.getMessage());
        }
    }

    public void completeAppointment(Appointment appointmentToComplete) {
        try {
            appointmentToComplete.setStatus("COMPLETED");
            appointmentDAO.update(appointmentToComplete);
            addMessage("Appointment marked as completed!");
            loadAppointments();
        } catch (SQLException e) {
            addErrorMessage("Error completing appointment: " + e.getMessage());
        }
    }

    public String createNewAppointment() {
        this.appointment = new Appointment();
        this.appointment.setAppointmentDatetime(new Date());
        return "appointment-form?faces-redirect=true";
    }

    public void searchAppointments() {
        try {
            filteredAppointments = new ArrayList<>();
            for (Appointment apt : appointments) {
                boolean matchesSearch = true;
                boolean matchesStatus = true;

                // Check search term
                if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                    matchesSearch = (apt.getPatientName() != null && apt.getPatientName().toLowerCase().contains(searchTerm.toLowerCase()))
                            || (apt.getDoctorName() != null && apt.getDoctorName().toLowerCase().contains(searchTerm.toLowerCase()))
                            || (apt.getReason() != null && apt.getReason().toLowerCase().contains(searchTerm.toLowerCase()));
                }

                // Check status filter
                if (statusFilter != null && !statusFilter.trim().isEmpty()) {
                    matchesStatus = apt.getStatus() != null && apt.getStatus().equals(statusFilter);
                }

                if (matchesSearch && matchesStatus) {
                    filteredAppointments.add(apt);
                }
            }
        } catch (Exception e) {
            addErrorMessage("Error searching appointments: " + e.getMessage());
        }
    }

    public void clearSearch() {
        searchTerm = "";
        statusFilter = "";
        filteredAppointments = new ArrayList<>(appointments);
    }

    public List<Appointment> getTodayAppointments() {
        try {
            return appointmentDAO.findTodayAppointments();
        } catch (Exception e) {
            addErrorMessage("Error loading today's appointments: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void addMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", message));
    }

    private void addErrorMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", message));
    }

    // Getters and Setters
    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<Appointment> getFilteredAppointments() {
        return filteredAppointments;
    }

    public void setFilteredAppointments(List<Appointment> filteredAppointments) {
        this.filteredAppointments = filteredAppointments;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getStatusFilter() {
        return statusFilter;
    }

    public void setStatusFilter(String statusFilter) {
        this.statusFilter = statusFilter;
    }

    public List<String> getStatusOptions() {
        return statusOptions;
    }
}
