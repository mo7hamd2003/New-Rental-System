package vehiclerentalsystem.DAO;

import vehiclerentalsystem.Models.Customer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO Customer (Firstname, Lastname, PhoneNb, Email) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getFirstname());
            stmt.setString(2, customer.getLastname());
            stmt.setInt(3, customer.getPhoneNb());
            stmt.setString(4, customer.getEmail());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATE Customer SET Firstname=?, Lastname=?, PhoneNb=?, Email=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getFirstname());
            stmt.setString(2, customer.getLastname());
            stmt.setInt(3, customer.getPhoneNb());
            stmt.setString(4, customer.getEmail());
            stmt.setInt(5, customer.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCustomer(int id) {
        String sql = "DELETE FROM Customer WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM Customer";
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Customer c = new Customer(
                        rs.getInt("id"),
                        rs.getString("Firstname"),
                        rs.getString("Lastname"),
                        rs.getInt("PhoneNb"),
                        rs.getString("Email"));
                list.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
