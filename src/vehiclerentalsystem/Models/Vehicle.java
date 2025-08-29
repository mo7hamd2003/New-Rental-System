package vehiclerentalsystem.Models;

public class Vehicle {
    private int id;
    private int companyId;
    private String plateNb;
    private String brand;
    private String model;
    private int year;
    private String type;
    private String status;
    private int dailyRate;
    private String description;
    private String imagePath;

    public Vehicle() {}

    public Vehicle(int id, int companyId, String plateNb, String brand, String model, int year, String type, String status, int dailyRate, String description, String imagePath) {
        this.id = id;
        this.companyId = companyId;
        this.plateNb = plateNb;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.type = type;
        this.status = status;
        this.dailyRate = dailyRate;
        this.description = description;
        this.imagePath = imagePath;

    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCompanyId() { return companyId; }
    public void setCompanyId(int companyId) { this.companyId = companyId; }

    public String getPlateNb() { return plateNb; }
    public void setPlateNb(String plateNb) { this.plateNb = plateNb; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getDailyRate() { return dailyRate; }
    public void setDailyRate(int dailyRate) { this.dailyRate = dailyRate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}
