package vehiclerentalsystem.Services;

import vehiclerentalsystem.DAO.VehicleDAO;
import vehiclerentalsystem.Models.Vehicle;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class VehicleService {
    private VehicleDAO vehicleDAO;

    private static final String IMAGES_FOLDER = "src/images/vehicles";

    public VehicleService() {
        this.vehicleDAO = new VehicleDAO();
        createImageFolder();
    }

    private void createImageFolder() {
        File folder = new File(IMAGES_FOLDER);
        if (!folder.exists()){
            folder.mkdirs();
        }
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleDAO.getAllVehicles();
    }

    // Business logic validation
    public boolean addVehicle(Vehicle vehicle, File imageFile) {
        // Validate vehicle data
        if (vehicle.getPlateNb() == null || vehicle.getPlateNb().isEmpty()) {
            throw new IllegalArgumentException("Plate number is required.");
        }
        if (vehicle.getDailyRate() <= 0) {
            throw new IllegalArgumentException("Daily rate must be positive.");
        }
        if (vehicle.getBrand() == null || vehicle.getBrand().trim().isEmpty()){
            throw new IllegalArgumentException("Brand is required.");
        }
        if(vehicle.getModel() == null || vehicle.getModel().trim().isEmpty()){
            throw new IllegalArgumentException("Model is required.");
        }
        if(vehicle.getYear() < 1900 || vehicle.getYear() > 2025){
            throw new IllegalArgumentException("Year must be between 1900 and 2025.");
        }

        return vehicleDAO.insertVehicle(vehicle);
    }

    public boolean updateVehicle(Vehicle vehicle) {
        // Validate vehicle data
        if (vehicle.getId() <= 0) {
            throw new IllegalArgumentException("Invalid vehicle ID.");
        }
        if (vehicle.getDailyRate() <= 0){
            throw new IllegalArgumentException("Daily rate must be positive.");
        }

        return vehicleDAO.updateVehicle(vehicle);
    }

    public boolean deleteVehicle(int vehicleId) {
        // Business logic to check if the vehicle is already rented or not.
        Vehicle vehicle = vehicleDAO.getVehicleById(vehicleId);
        if (vehicle != null && "rented".equalsIgnoreCase(vehicle.getStatus())) {
            throw new IllegalArgumentException("Vehicle is currently rented and cannot be deleted.");
        }
        return vehicleDAO.deleteVehicle(vehicleId);
    }

    public Vehicle getVehicleById(int id) {
        return vehicleDAO.getVehicleById(id);
    }

    // Image handling methods
    public String saveVehicleImage(String sourceImagePath, int vehicleId) {
        try {
            Path sourcePath = Paths.get(sourceImagePath);
            String fileName = vehicleId + "_" + sourcePath.getFileName().toString();
            Path destinationPath = Paths.get(IMAGES_FOLDER + fileName);

            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

            return IMAGES_FOLDER + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteVehicleImage(String imagePath){
        try {
            if (imagePath != null && !imagePath.isEmpty()){
                Path path = Paths.get(imagePath);
                return Files.deleteIfExists(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isImageExists(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()){
            return false;
        }
        return Files.exists(Paths.get(imagePath));
    }

    // Business logic for vehicle availability 
    public boolean isVehicleAvailable(int vehicleId) {
        Vehicle vehicle = vehicleDAO.getVehicleById(vehicleId);
        return vehicle != null && "available".equalsIgnoreCase(vehicle.getStatus());
    }

    public List<Vehicle> getAvailableVehicles() {
        return getAllVehicles().stream()
            .filter(v -> "available".equalsIgnoreCase(v.getStatus()))
            .collect(java.util.stream.Collectors.toList());
    }
}
