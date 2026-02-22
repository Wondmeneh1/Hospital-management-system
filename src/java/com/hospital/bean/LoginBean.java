package com.hospital.bean;

import com.hospital.dao.UserDAO;
import com.hospital.model.User;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.sql.SQLException;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private User currentUser;
    private UserDAO userDAO = new UserDAO();

    public LoginBean() {
        // Constructor
    }

    public String login() {
        try {
            if (username == null || username.trim().isEmpty()) {
                addErrorMessage("Username is required");
                return null;
            }

            if (password == null || password.trim().isEmpty()) {
                addErrorMessage("Password is required");
                return null;
            }

            User user = userDAO.authenticate(username.trim(), password);

            if (user != null) {
                currentUser = user;

                // Store user in session
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                        .getExternalContext().getSession(true);
                session.setAttribute("currentUser", user);
                session.setAttribute("userRole", user.getRole());

                addMessage("Welcome, " + user.getFullName() + "!");

                // Clear login form
                username = "";
                password = "";

                // Redirect to dashboard
                return "index?faces-redirect=true";

            } else {
                addErrorMessage("Invalid username or password");
                return null;
            }

        } catch (SQLException e) {
            addErrorMessage("Login failed: " + e.getMessage());
            return null;
        }
    }

    public String logout() {
        try {
            // Clear session
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(false);

            if (session != null) {
                session.invalidate();
            }

            // Clear current user
            currentUser = null;
            username = "";
            password = "";

            addMessage("You have been logged out successfully");

            // Redirect to login page
            return "login?faces-redirect=true";

        } catch (Exception e) {
            addErrorMessage("Logout failed: " + e.getMessage());
            return null;
        }
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public boolean isAdmin() {
        return currentUser != null && "ADMIN".equals(currentUser.getRole());
    }

    public boolean isDoctor() {
        return currentUser != null && "DOCTOR".equals(currentUser.getRole());
    }

    public boolean isNurse() {
        return currentUser != null && "NURSE".equals(currentUser.getRole());
    }

    public boolean isReceptionist() {
        return currentUser != null && "RECEPTIONIST".equals(currentUser.getRole());
    }

    public String getCurrentUserRole() {
        return currentUser != null ? currentUser.getRole() : "GUEST";
    }

    public String getCurrentUserName() {
        return currentUser != null ? currentUser.getFullName() : "Guest";
    }

    public String checkAuthentication() {
        if (!isLoggedIn()) {
            return "login?faces-redirect=true";
        }
        return null;
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
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
