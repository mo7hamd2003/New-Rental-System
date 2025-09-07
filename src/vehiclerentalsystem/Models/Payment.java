package vehiclerentalsystem.Models;

import java.util.Date;

public class Payment {
    private int id;
    private int bookingId;
    private Date paymentDate;
    private double amount;
    private double downPayment;
    private double feePayment;
    private double customerPaid;

    // Constructors
    public Payment() {
    }

    public Payment(int bookingId, Date paymentDate, double amount) {
        this.bookingId = bookingId;
        this.paymentDate = paymentDate;
        this.amount = amount;
    }

    public Payment(int bookingId, Date paymentDate, double amount, double downPayment, double feePayment,
            double customerPaid) {
        this.bookingId = bookingId;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.downPayment = downPayment;
        this.feePayment = feePayment;
        this.customerPaid = customerPaid;
    }

    public Payment(int id, int bookingId, Date paymentDate, double amount, double downPayment, double feePayment,
            double customerPaid) {
        this.id = id;
        this.bookingId = bookingId;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.downPayment = downPayment;
        this.feePayment = feePayment;
        this.customerPaid = customerPaid;
    }

    // Getters & Setters
    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(double downPayment) {
        this.downPayment = downPayment;
    }

    public double getFeePayment() {
        return feePayment;
    }

    public void setFeePayment(double feePayment) {
        this.feePayment = feePayment;
    }

    public double getCustomerPaid() {
        return customerPaid;
    }

    public void setCustomerPaid(double customerPaid) {
        this.customerPaid = customerPaid;
    }
}
