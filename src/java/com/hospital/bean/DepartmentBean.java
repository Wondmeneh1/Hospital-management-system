package com.hospital.bean;

import com.hospital.dao.DepartmentDAO;
import com.hospital.model.Department;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;


@ManagedBean(name = "departmentBean")
@SessionScoped
public class DepartmentBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Department department = new Department();
    private List<Department> departments;
    private DepartmentDAO departmentDAO = new DepartmentDAO();
    private String searchTerm = "";

    public DepartmentBean() {
        departments = new ArrayList<>();
        try {
            loadDepartments();
        } catch (Exception e) {
            System.err.println("Error initializing department data: " + e.getMessage());
        }
    }

    public void loadDepartments() {
        try {
            List<Department> loadedDepartments = departmentDAO.findAll();
            if (loadedDepartments != null) {
                departments = loadedDepartments;
            } else {
                departments = new ArrayList<>();
            }
        } catch (Exception e) {
            departments = new ArrayList<>();
            System.err.println("Error loading departments: " + e.getMessage());
        }
    }

    public String saveDepartment() {
        try {
            if (department.getId() == null) {
                departmentDAO.save(department);
                addMessage("Department created successfully!");
            } else {
                departmentDAO.update(department);
                addMessage("Department updated successfully!");
            }

            department = new Department();
            loadDepartments();
            return "departments?faces-redirect=true";

        } catch (Exception e) {
            addErrorMessage("Error saving department: " + e.getMessage());
            return null;
        }
    }

    public String editDepartment(Department departmentToEdit) {
        this.department = new Department();
        this.department.setId(departmentToEdit.getId());
        this.department.setName(departmentToEdit.getName());
        this.department.setDescription(departmentToEdit.getDescription());
        this.department.setHeadDoctor(departmentToEdit.getHeadDoctor());
        this.department.setLocation(departmentToEdit.getLocation());
        this.department.setPhone(departmentToEdit.getPhone());
        this.department.setEmail(departmentToEdit.getEmail());
        this.department.setStatus(departmentToEdit.getStatus());
        return "department-form?faces-redirect=true";
    }

    public void deleteDepartment(Department departmentToDelete) {
        try {
            departmentDAO.delete(departmentToDelete.getId());
            addMessage("Department deleted successfully!");
            loadDepartments();
        } catch (Exception e) {
            addErrorMessage("Error deleting department: " + e.getMessage());
        }
    }

    public String createNewDepartment() {
        this.department = new Department();
        return "department-form?faces-redirect=true";
    }

    public void searchDepartments() {
        try {
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                loadDepartments();
            } else {
                List<Department> searchResults = departmentDAO.findByName(searchTerm.trim());
                if (searchResults != null) {
                    departments = searchResults;
                } else {
                    departments = new ArrayList<>();
                }
            }
        } catch (Exception e) {
            departments = new ArrayList<>();
            System.err.println("Error searching departments: " + e.getMessage());
        }
    }
 public void clearSearch() {
        searchTerm = "";
        ArrayList<Department> filteredDepartments = new ArrayList<>(departments);
    }
    private void addMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
    }

    private void addErrorMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }

    public String[] getStatusOptions() {
        return new String[]{"ACTIVE", "INACTIVE", "UNDER_RENOVATION"};
    }

    // Getters and Setters
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }
}
