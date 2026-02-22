package com.hospital.bean;

import com.hospital.dao.PatientDAO;
import com.hospital.model.Patient;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

@ManagedBean(name = "patientBean")
@SessionScoped
public class PatientBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Patient patient = new Patient();
    private List<Patient> patients;
    private PatientDAO patientDAO = new PatientDAO();
    private String searchTerm = "";

    public PatientBean() {
        // Initialize empty list to prevent null pointer exceptions
        patients = new ArrayList<>();

        // Load data - if this fails, the list will remain empty but functional
        try {
            loadPatients();
        } catch (Exception e) {
            System.err.println("Error initializing patient data: " + e.getMessage());
        }
    }

    public void loadPatients() {
        try {
            List<Patient> loadedPatients = patientDAO.findAll();
            if (loadedPatients != null) {
                patients = loadedPatients;
            } else {
                patients = new ArrayList<>();
            }
        } catch (Exception e) {
            patients = new ArrayList<>(); // Ensure list is never null
            System.err.println("Error loading patients: " + e.getMessage());
        }
    }

    public String savePatient() {
        try {
            if (patient.getId() == null) {
                patientDAO.save(patient);
                addMessage("Patient created successfully!");
            } else {
                patientDAO.update(patient);
                addMessage("Patient updated successfully!");
            }

            patient = new Patient();
            loadPatients();
            return "patients?faces-redirect=true";

        } catch (Exception e) {
            addErrorMessage("Error saving patient: " + e.getMessage());
            return null;
        }
    }

    public String editPatient(Patient patientToEdit) {
        this.patient = new Patient();
        this.patient.setId(patientToEdit.getId());
        this.patient.setFirstName(patientToEdit.getFirstName());
        this.patient.setLastName(patientToEdit.getLastName());
        this.patient.setEmail(patientToEdit.getEmail());
        this.patient.setPhone(patientToEdit.getPhone());
        this.patient.setAddress(patientToEdit.getAddress());
        this.patient.setDateOfBirth(patientToEdit.getDateOfBirth());
        this.patient.setGender(patientToEdit.getGender());
        this.patient.setBloodGroup(patientToEdit.getBloodGroup());
        this.patient.setEmergencyContactName(patientToEdit.getEmergencyContactName());
        this.patient.setEmergencyContactPhone(patientToEdit.getEmergencyContactPhone());
        this.patient.setMedicalHistory(patientToEdit.getMedicalHistory());
        this.patient.setAllergies(patientToEdit.getAllergies());
        this.patient.setInsuranceNumber(patientToEdit.getInsuranceNumber());
        return "patient-form?faces-redirect=true";
    }

    public void deletePatient(Patient patientToDelete) {
        try {
            patientDAO.delete(patientToDelete.getId());
            addMessage("Patient deleted successfully!");
            loadPatients();
        } catch (Exception e) {
            addErrorMessage("Error deleting patient: " + e.getMessage());
        }
    }

    public String createNewPatient() {
        this.patient = new Patient();
        return "patient-form?faces-redirect=true";
    }

    public void searchPatients() {
        try {
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                loadPatients();
            } else {
                List<Patient> searchResults = patientDAO.findByName(searchTerm.trim());
                if (searchResults != null) {
                    patients = searchResults;
                } else {
                    patients = new ArrayList<>();
                }
            }
        } catch (Exception e) {
            patients = new ArrayList<>();
            System.err.println("Error searching patients: " + e.getMessage());
        }
    }

    private void safeAddMessage(String message) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null) {
                context.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
            } else {
                System.out.println("INFO: " + message);
            }
        } catch (Exception e) {
            System.out.println("INFO: " + message);
        }
    }

    private void safeAddErrorMessage(String message) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null) {
                context.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            } else {
                System.err.println("ERROR: " + message);
            }
        } catch (Exception e) {
            System.err.println("ERROR: " + message);
        }
    }

    public void clearSearch() {
        searchTerm = "";
        loadPatients(); // This will reload all patients and reset filteredPatients
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

    // Getters and Setters
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
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
}
