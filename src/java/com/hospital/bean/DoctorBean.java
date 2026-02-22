package com.hospital.bean;

import com.hospital.dao.DoctorDAO;
import com.hospital.dao.DepartmentDAO;
import com.hospital.model.Doctor;
import com.hospital.model.Department;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

@ManagedBean(name = "doctorBean")
@SessionScoped
public class DoctorBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Doctor doctor = new Doctor();
    private List<Doctor> doctors;
    private DoctorDAO doctorDAO = new DoctorDAO();
    private DepartmentDAO departmentDAO = new DepartmentDAO();
    private String searchTerm = "";

    public DoctorBean() {
        doctors = new ArrayList<>();
        try {
            loadDoctors();
        } catch (Exception e) {
            System.err.println("Error initializing doctor data: " + e.getMessage());
        }
    }

    public void loadDoctors() {
        try {
            List<Doctor> loadedDoctors = doctorDAO.findAll();
            if (loadedDoctors != null) {
                doctors = loadedDoctors;
            } else {
                doctors = new ArrayList<>();
            }
        } catch (Exception e) {
            doctors = new ArrayList<>();
            System.err.println("Error loading doctors: " + e.getMessage());
        }
    }

    public String saveDoctor() {
        try {
            if (doctor.getId() == null) {
                doctorDAO.save(doctor);
                addMessage("Doctor created successfully!");
            } else {
                doctorDAO.update(doctor);
                addMessage("Doctor updated successfully!");
            }

            doctor = new Doctor();
            loadDoctors();
            return "doctors?faces-redirect=true";

        } catch (Exception e) {
            addErrorMessage("Error saving doctor: " + e.getMessage());
            return null;
        }
    }

    public String editDoctor(Doctor doctorToEdit) {
        this.doctor = new Doctor();
        this.doctor.setId(doctorToEdit.getId());
        this.doctor.setFirstName(doctorToEdit.getFirstName());
        this.doctor.setLastName(doctorToEdit.getLastName());
        this.doctor.setEmail(doctorToEdit.getEmail());
        this.doctor.setPhone(doctorToEdit.getPhone());
        this.doctor.setSpecialization(doctorToEdit.getSpecialization());
        this.doctor.setLicenseNumber(doctorToEdit.getLicenseNumber());
        this.doctor.setDepartmentId(doctorToEdit.getDepartmentId());
        this.doctor.setAddress(doctorToEdit.getAddress());
        this.doctor.setDateOfBirth(doctorToEdit.getDateOfBirth());
        this.doctor.setGender(doctorToEdit.getGender());
        this.doctor.setHireDate(doctorToEdit.getHireDate());
        this.doctor.setYearsOfExperience(doctorToEdit.getYearsOfExperience());
        this.doctor.setStatus(doctorToEdit.getStatus());
        return "doctor-form?faces-redirect=true";
    }

    public void deleteDoctor(Doctor doctorToDelete) {
        try {
            doctorDAO.delete(doctorToDelete.getId());
            addMessage("Doctor deleted successfully!");
            loadDoctors();
        } catch (Exception e) {
            addErrorMessage("Error deleting doctor: " + e.getMessage());
        }
    }

    public String createNewDoctor() {
        this.doctor = new Doctor();
        return "doctor-form?faces-redirect=true";
    }

    public void searchDoctors() {
        try {
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                loadDoctors();
            } else {
                List<Doctor> searchResults = doctorDAO.findByName(searchTerm.trim());
                if (searchResults != null) {
                    doctors = searchResults;
                } else {
                    doctors = new ArrayList<>();
                }
            }
        } catch (Exception e) {
            doctors = new ArrayList<>();
            System.err.println("Error searching doctors: " + e.getMessage());
        }
    }

    public List<SelectItem> getDepartmentOptions() {
        List<SelectItem> options = new ArrayList<>();
        options.add(new SelectItem(null, "Select Department"));

        try {
            List<Department> departments = departmentDAO.findAll();
            for (Department dept : departments) {
                options.add(new SelectItem(dept.getId(), dept.getName()));
            }
        } catch (Exception e) {
            System.err.println("Error loading departments: " + e.getMessage());
        }

        return options;
    }

    public void clearSearch() {
        searchTerm = "";
        // Reload all doctors
        loadDoctors();
    }

    private void addMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
    }

    private void addErrorMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }

    public String[] getGenderOptions() {
        return new String[]{"MALE", "FEMALE", "OTHER"};
    }

    public String[] getStatusOptions() {
        return new String[]{"ACTIVE", "INACTIVE", "ON_LEAVE"};
    }

    // Getters and Setters
    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }
}
