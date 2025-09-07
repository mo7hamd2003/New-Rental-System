package vehiclerentalsystem.Services;

import vehiclerentalsystem.DAO.PaymentDAO;
import vehiclerentalsystem.Models.Payment;

import java.util.List;

public class PaymentService {
    private PaymentDAO paymentDAO = new PaymentDAO();

    public boolean addPayment(Payment payment) {
        return paymentDAO.addPayment(payment);
    }

    public List<Payment> getPaymentsByBooking(int bookingId) {
        return paymentDAO.getPaymentsByBooking(bookingId);
    }

    public double getRemainderAmount(int bookingId) {
        return paymentDAO.getRemainderAmount(bookingId);
    }
}
