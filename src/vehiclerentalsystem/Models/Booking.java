package vehiclerentalsystem.Models;

import java.util.Date;

public class Booking {
    private int id;
    private int vehicleId;
    private int customerId;
    private int userId;
    private Date startDate;
    private Date endDate;
    private Date returnDate;
    private double totalAmount;
    private double remainderAmount;

    // Constructors
    public Booking() {
    }

    public Booking(int id, int vehicleId, int customerId, int userId,
            Date startDate, Date endDate, Date returnDate, double totalAmount, double remainderAmount) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.customerId = customerId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.returnDate = returnDate;
        this.totalAmount = totalAmount;
        this.remainderAmount = remainderAmount;
    }

    public Booking(int id, int vehicleId, int customerId, int userId,
            Date startDate, Date endDate, Date returnDate) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.customerId = customerId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.returnDate = returnDate;
    }

    public Booking(int vehicleId, int customerId, int userId,
            Date startDate, Date endDate, Date returnDate) {
        this.vehicleId = vehicleId;
        this.customerId = customerId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.returnDate = returnDate;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getRemainderAmount() {
        return remainderAmount;
    }

    public void setRemainderAmount(double remainderAmount) {
        this.remainderAmount = remainderAmount;
    }
}
