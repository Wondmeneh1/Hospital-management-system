package com.hospital.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Room implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String roomNumber;
    private String roomType;
    private Integer capacity;
    private Integer currentOccupancy;
    private BigDecimal dailyRate;
    private Long departmentId;
    private String departmentName; // For display purposes - ADDED THIS
    private String status;
    private Date createdAt;
    private Date updatedAt;
    
    // Constructors
    public Room() {
        this.createdAt = new Date();
        this.status = "AVAILABLE";
        this.capacity = 1;
        this.currentOccupancy = 0;
        this.dailyRate = BigDecimal.ZERO;
    }
    
    public Room(String roomNumber, String roomType, Integer capacity) {
        this();
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.capacity = capacity;
    }
    
    // Helper methods
    public boolean isAvailable() {
        return "AVAILABLE".equals(status) && currentOccupancy < capacity;
    }
    
    public int getAvailableSpaces() {
        return capacity - currentOccupancy;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getRoomNumber() {
        return roomNumber;
    }
    
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
    
    public String getRoomType() {
        return roomType;
    }
    
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
    
    public Integer getCapacity() {
        return capacity;
    }
    
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    
    public Integer getCurrentOccupancy() {
        return currentOccupancy;
    }
    
    public void setCurrentOccupancy(Integer currentOccupancy) {
        this.currentOccupancy = currentOccupancy;
    }
    
    public BigDecimal getDailyRate() {
        return dailyRate;
    }
    
    public void setDailyRate(BigDecimal dailyRate) {
        this.dailyRate = dailyRate;
    }
    
    public Long getDepartmentId() {
        return departmentId;
    }
    
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
    
    // ADDED: Department Name getter and setter
    public String getDepartmentName() {
        return departmentName;
    }
    
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
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
}