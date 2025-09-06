package vehiclerentalsystem.Controllers;

import vehiclerentalsystem.Services.VehicleService;
import vehiclerentalsystem.Models.Vehicle;
import java.util.List;

public class VehicleController {
    private VehicleService vehicleService;

    public VehicleController() {
        this.vehicleService = new VehicleService();
    }

    public List<Vehicle> loadAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    public boolean createVehicle(Vehicle vehicle) {
        try {
            return vehicleService.addVehicle(vehicle);
        } catch (Exception e){
            System.err.println("Error creating vehicle: " + e.getMessage());
            return false;
        }
    }

    public boolean updateVehicle(Vehicle vehicle) {
        try {
            return vehicleService.updateVehicle(vehicle);
        } catch (Exception e){
            System.err.println("Error updating vehicle: " + e.getMessage());
            return false;
        }
    }

    public boolean removeVehicle(int vehicleId) {
        try {
            return vehicleService.deleteVehicle(vehicleId);        
        } catch (Exception e) {
            System.err.println("Error deleting vehicle: " + e.getMessage());
            return false;
        }
    }

    public Vehicle getVehicleById(int vehicleId) {
        return vehicleService.getVehicleById(vehicleId);
    }

    public String saveVehicleImage(String imagePath, int vehicleId) {
        return vehicleService.saveVehicleImage(imagePath, vehicleId);
    }

    public List<Vehicle> getAvailableVCehicles() {
        return vehicleService.getAvailableVehicles();
    }

    public List<Vehicle> getRentedVehicles() {
        return vehicleService.getRentedVehicles();
    }
}
