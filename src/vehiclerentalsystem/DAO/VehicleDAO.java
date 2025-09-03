package vehiclerentalsystem.DAO;

import vehiclerentalsystem.Models.Vehicle;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {

    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicle";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Vehicle vehicle = new Vehicle(
                        rs.getInt("id"),
                        rs.getInt("companyId"),
                        rs.getString("PlateNb"),
                        rs.getString("Brand"),
                        rs.getString("Model"),
                        rs.getInt("year"),
                        rs.getString("Type"),
                        rs.getString("status"),
                        rs.getInt("DailyRate"),
                        rs.getString("Description"),
                        rs.getString("imagePath"));
                vehicles.add(vehicle);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicles;
    }

    public boolean insertVehicle(Vehicle vehicle) {
        String sql = "INSERT INTO Vehicle (companyId, PlateNb, Brand, Model, year, Type, status, DailyRate, Description, imagePath) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, vehicle.getCompanyId());
            pstmt.setString(2, vehicle.getPlateNb());
            pstmt.setString(3, vehicle.getBrand());
            pstmt.setString(4, vehicle.getModel());
            pstmt.setInt(5, vehicle.getYear());
            pstmt.setString(6, vehicle.getType());
            pstmt.setString(7, vehicle.getStatus());
            pstmt.setInt(8, vehicle.getDailyRate());
            pstmt.setString(9, vehicle.getDescription());
            pstmt.setString(10, vehicle.getImagePath());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateVehicle(Vehicle vehicle) {
        String sql = "UPDATE Vehicle SET companyId = ?, PlateNb = ?, Brand = ?, Model = ?, year = ?, Type = ?, status = ?, DailyRate = ?, Description = ?, imagePath = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, vehicle.getCompanyId());
            pstmt.setString(2, vehicle.getPlateNb());
            pstmt.setString(3, vehicle.getBrand());
            pstmt.setString(4, vehicle.getModel());
            pstmt.setInt(5, vehicle.getYear());
            pstmt.setString(6, vehicle.getType());
            pstmt.setString(7, vehicle.getStatus());
            pstmt.setInt(8, vehicle.getDailyRate());
            pstmt.setString(9, vehicle.getDescription());
            pstmt.setString(10, vehicle.getImagePath());
            pstmt.setInt(11, vehicle.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteVehicle(int vehicleId) {
        String sql = "DELETE FROM Vehicle WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, vehicleId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Vehicle getVehicleById(int id) {
        String sql = "SELECT * FROM Vehicle WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Vehicle(
                        rs.getInt("id"),
                        rs.getInt("companyId"),
                        rs.getString("PlateNb"),
                        rs.getString("Brand"),
                        rs.getString("Model"),
                        rs.getInt("year"),
                        rs.getString("Type"),
                        rs.getString("status"),
                        rs.getInt("DailyRate"),
                        rs.getString("Description"),
                        rs.getString("imagePath"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}