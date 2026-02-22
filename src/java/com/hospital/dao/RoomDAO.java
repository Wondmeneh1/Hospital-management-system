package com.hospital.dao;

import com.hospital.model.Room;
import com.hospital.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class RoomDAO {

    public void save(Room room) throws SQLException {
        String sql = "INSERT INTO rooms (room_number, room_type, capacity, current_occupancy, daily_rate, department_id, status, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, room.getRoomNumber());
            stmt.setString(2, room.getRoomType());
            stmt.setInt(3, room.getCapacity());
            stmt.setInt(4, room.getCurrentOccupancy());
            stmt.setBigDecimal(5, room.getDailyRate());
            if (room.getDepartmentId() != null) {
                stmt.setLong(6, room.getDepartmentId());
            } else {
                stmt.setNull(6, Types.BIGINT);
            }
            stmt.setString(7, room.getStatus());
            stmt.setTimestamp(8, new Timestamp(room.getCreatedAt().getTime()));

            stmt.executeUpdate();

        } finally {
            if (stmt != null) {
                stmt.close();
            }
            DatabaseUtil.closeConnection(conn);
        }
    }

    public void update(Room room) throws SQLException {
        String sql = "UPDATE rooms SET room_number=?, room_type=?, capacity=?, current_occupancy=?, daily_rate=?, department_id=?, status=?, updated_at=? WHERE id=?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, room.getRoomNumber());
            stmt.setString(2, room.getRoomType());
            stmt.setInt(3, room.getCapacity());
            stmt.setInt(4, room.getCurrentOccupancy());
            stmt.setBigDecimal(5, room.getDailyRate());
            if (room.getDepartmentId() != null) {
                stmt.setLong(6, room.getDepartmentId());
            } else {
                stmt.setNull(6, Types.BIGINT);
            }
            stmt.setString(7, room.getStatus());
            stmt.setTimestamp(8, new Timestamp(new Date().getTime()));
            stmt.setLong(9, room.getId());

            stmt.executeUpdate();

        } finally {
            if (stmt != null) {
                stmt.close();
            }
            DatabaseUtil.closeConnection(conn);
        }
    }

    public List<Room> findAll() throws SQLException {
        String sql = "SELECT * FROM rooms ORDER BY room_number";
        List<Room> rooms = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Room room = new Room();
                room.setId(rs.getLong("id"));
                room.setRoomNumber(rs.getString("room_number"));
                room.setRoomType(rs.getString("room_type"));
                room.setCapacity(rs.getInt("capacity"));
                room.setCurrentOccupancy(rs.getInt("current_occupancy"));
                room.setDailyRate(rs.getBigDecimal("daily_rate"));
                room.setDepartmentId(rs.getLong("department_id"));
                room.setStatus(rs.getString("status"));
                room.setCreatedAt(rs.getTimestamp("created_at"));
                room.setUpdatedAt(rs.getTimestamp("updated_at"));

                rooms.add(room);
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

        return rooms;
    }

    public Room findById(Long id) throws SQLException {
        String sql = "SELECT * FROM rooms WHERE id = ?";
        Room room = null;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                room = new Room();
                room.setId(rs.getLong("id"));
                room.setRoomNumber(rs.getString("room_number"));
                room.setRoomType(rs.getString("room_type"));
                room.setCapacity(rs.getInt("capacity"));
                room.setCurrentOccupancy(rs.getInt("current_occupancy"));
                room.setDailyRate(rs.getBigDecimal("daily_rate"));
                room.setDepartmentId(rs.getLong("department_id"));
                room.setStatus(rs.getString("status"));
                room.setCreatedAt(rs.getTimestamp("created_at"));
                room.setUpdatedAt(rs.getTimestamp("updated_at"));
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

        return room;
    }

    public void delete(Long roomId) throws SQLException {
        String sql = "DELETE FROM rooms WHERE id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, roomId);
            stmt.executeUpdate();

        } finally {
            if (stmt != null) {
                stmt.close();
            }
            DatabaseUtil.closeConnection(conn);
        }
    }

    public List<Room> findByRoomNumber(String roomNumber) throws SQLException {
        String sql = "SELECT * FROM rooms WHERE LOWER(room_number) LIKE LOWER(?) ORDER BY room_number";
        List<Room> rooms = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + roomNumber + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Room room = new Room();
                room.setId(rs.getLong("id"));
                room.setRoomNumber(rs.getString("room_number"));
                room.setRoomType(rs.getString("room_type"));
                room.setCapacity(rs.getInt("capacity"));
                room.setCurrentOccupancy(rs.getInt("current_occupancy"));
                room.setDailyRate(rs.getBigDecimal("daily_rate"));
                room.setDepartmentId(rs.getLong("department_id"));
                room.setStatus(rs.getString("status"));
                room.setCreatedAt(rs.getTimestamp("created_at"));
                room.setUpdatedAt(rs.getTimestamp("updated_at"));

                rooms.add(room);
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

        return rooms;
    }

    public List<Room> findAvailableRooms() throws SQLException {
        String sql = "SELECT * FROM rooms WHERE status = 'AVAILABLE' AND current_occupancy < capacity ORDER BY room_number";
        List<Room> rooms = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Room room = new Room();
                room.setId(rs.getLong("id"));
                room.setRoomNumber(rs.getString("room_number"));
                room.setRoomType(rs.getString("room_type"));
                room.setCapacity(rs.getInt("capacity"));
                room.setCurrentOccupancy(rs.getInt("current_occupancy"));
                room.setDailyRate(rs.getBigDecimal("daily_rate"));
                room.setDepartmentId(rs.getLong("department_id"));
                room.setStatus(rs.getString("status"));
                room.setCreatedAt(rs.getTimestamp("created_at"));
                room.setUpdatedAt(rs.getTimestamp("updated_at"));

                rooms.add(room);
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

        return rooms;
    }
}
