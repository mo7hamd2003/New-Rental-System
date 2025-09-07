package vehiclerentalsystem.DAO;

import vehiclerentalsystem.Models.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    public boolean addPayment(Payment payment) {
        String sql = "INSERT INTO Payment (BookingId, PaymentDate, Amount) " +
                "VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, payment.getBookingId());
            stmt.setDate(2, new java.sql.Date(payment.getPaymentDate().getTime()));
            stmt.setDouble(3, payment.getAmount());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Payment> getPaymentsByBooking(int bookingId) {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM Payment WHERE BookingId=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Payment payment = new Payment(
                            rs.getInt("id"),
                            rs.getInt("BookingId"),
                            rs.getDate("PaymentDate"),
                            rs.getDouble("Amount"),
                            rs.getDouble("DownPayment"),
                            rs.getDouble("FeePayment"),
                            rs.getDouble("CustomerPaid"));
                    payments.add(payment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    public double getRemainderAmount(int bookingId) {
        String sql = "SELECT dbo.fn_GetRemainderAmount(?) AS Remainder";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("Remainder");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
