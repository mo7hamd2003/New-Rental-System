package vehiclerentalsystem.DAO;

import vehiclerentalsystem.Models.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public boolean addBooking(Booking booking) {
        String sql = "INSERT INTO Booking (VehicleId, CustomerId, UserId, StartDate, EndDate, ReturnDate) "
                +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, booking.getVehicleId());
            stmt.setInt(2, booking.getCustomerId());
            stmt.setInt(3, booking.getUserId());
            stmt.setDate(4, new java.sql.Date(booking.getStartDate().getTime()));
            stmt.setDate(5, new java.sql.Date(booking.getEndDate().getTime()));
            stmt.setDate(6,
                    booking.getReturnDate() != null ? new java.sql.Date(booking.getReturnDate().getTime()) : null);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateBooking(Booking booking) {
        String sql = "UPDATE Booking SET VehicleId=?, CustomerId=?, UserId=?, StartDate=?, EndDate=?, ReturnDate=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, booking.getVehicleId());
            stmt.setInt(2, booking.getCustomerId());
            stmt.setInt(3, booking.getUserId());
            stmt.setDate(4, new java.sql.Date(booking.getStartDate().getTime()));
            stmt.setDate(5, new java.sql.Date(booking.getEndDate().getTime()));
            stmt.setDate(6,
                    booking.getReturnDate() != null ? new java.sql.Date(booking.getReturnDate().getTime()) : null);
            stmt.setInt(7, booking.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteBooking(int id) {
        String sql = "DELETE FROM Booking WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Booking> getAllBookings() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM Booking";
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Booking booking = new Booking(
                        rs.getInt("id"),
                        rs.getInt("VehicleId"),
                        rs.getInt("CustomerId"),
                        rs.getInt("UserId"),
                        rs.getDate("StartDate"),
                        rs.getDate("EndDate"),
                        rs.getDate("ReturnDate"),
                        rs.getDouble("TotalAmount"),
                        rs.getDouble("RemainderAmount"));
                list.add(booking);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
