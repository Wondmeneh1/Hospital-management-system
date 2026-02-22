package com.hospital.bean;

import com.hospital.dao.RoomDAO;
import com.hospital.dao.DepartmentDAO;
import com.hospital.model.Room;
import com.hospital.model.Department;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

@ManagedBean(name = "roomBean")
@SessionScoped
public class RoomBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Room room = new Room();
    private List<Room> rooms;
    private RoomDAO roomDAO = new RoomDAO();
    private DepartmentDAO departmentDAO = new DepartmentDAO();
    private String searchTerm = "";

    public RoomBean() {
        rooms = new ArrayList<>();
        try {
            loadRooms();
        } catch (Exception e) {
            System.err.println("Error initializing room data: " + e.getMessage());
        }
    }

    public void loadRooms() {
        try {
            List<Room> loadedRooms = roomDAO.findAll();
            if (loadedRooms != null) {
                rooms = loadedRooms;
            } else {
                rooms = new ArrayList<>();
            }
        } catch (Exception e) {
            rooms = new ArrayList<>();
            System.err.println("Error loading rooms: " + e.getMessage());
        }
    }

    public String saveRoom() {
        try {
            if (room.getId() == null) {
                roomDAO.save(room);
                addMessage("Room created successfully!");
            } else {
                roomDAO.update(room);
                addMessage("Room updated successfully!");
            }

            room = new Room();
            loadRooms();
            return "rooms?faces-redirect=true";

        } catch (Exception e) {
            addErrorMessage("Error saving room: " + e.getMessage());
            return null;
        }
    }

    public String editRoom(Room roomToEdit) {
        this.room = new Room();
        this.room.setId(roomToEdit.getId());
        this.room.setRoomNumber(roomToEdit.getRoomNumber());
        this.room.setRoomType(roomToEdit.getRoomType());
        this.room.setCapacity(roomToEdit.getCapacity());
        this.room.setCurrentOccupancy(roomToEdit.getCurrentOccupancy());
        this.room.setDailyRate(roomToEdit.getDailyRate());
        this.room.setDepartmentId(roomToEdit.getDepartmentId());
        this.room.setStatus(roomToEdit.getStatus());
        return "room-form?faces-redirect=true";
    }

    public void deleteRoom(Room roomToDelete) {
        try {
            roomDAO.delete(roomToDelete.getId());
            addMessage("Room deleted successfully!");
            loadRooms();
        } catch (Exception e) {
            addErrorMessage("Error deleting room: " + e.getMessage());
        }
    }

    public String createNewRoom() {
        this.room = new Room();
        return "room-form?faces-redirect=true";
    }

    public void searchRooms() {
        try {
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                loadRooms();
            } else {
                List<Room> searchResults = roomDAO.findByRoomNumber(searchTerm.trim());
                if (searchResults != null) {
                    rooms = searchResults;
                } else {
                    rooms = new ArrayList<>();
                }
            }
        } catch (Exception e) {
            rooms = new ArrayList<>();
            System.err.println("Error searching rooms: " + e.getMessage());
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

    private void addMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
    }

    private void addErrorMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }

    public String[] getRoomTypeOptions() {
        return new String[]{"GENERAL", "PRIVATE", "ICU", "EMERGENCY", "SURGERY", "MATERNITY", "PEDIATRIC"};
    }

    public String[] getStatusOptions() {
        return new String[]{"AVAILABLE", "OCCUPIED", "MAINTENANCE", "CLEANING", "OUT_OF_ORDER"};
    }

    // Getters and Setters
    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }
}
