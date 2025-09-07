package vehiclerentalsystem.GUI;

import vehiclerentalsystem.Models.Payment;
import vehiclerentalsystem.Services.PaymentService;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class PaymentForm extends JFrame {
    private PaymentService paymentService = new PaymentService();

    private JTextField txtBookingId, txtAmount;
    private JButton btnPay;

    public PaymentForm() {
        setTitle("Payment Form");
        setSize(400, 300);
        setLayout(new GridLayout(6, 2, 5, 5));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new JLabel("Booking ID:"));
        txtBookingId = new JTextField();
        add(txtBookingId);

        add(new JLabel("Amount:"));
        txtAmount = new JTextField();
        add(txtAmount);

        btnPay = new JButton("Make Payment");
        add(btnPay);

        btnPay.addActionListener(e -> makePayment());

        setLocationRelativeTo(null);

        // ===== Buttons =====
        JPanel buttonPanel = new JPanel();

        JButton btnBack = new JButton("← Back");

        buttonPanel.add(btnBack);

        add(buttonPanel, BorderLayout.SOUTH);

        btnBack.addActionListener(e -> {
            dispose();
            new AdminDashboard().setVisible(true);

        });
    }

    private void makePayment() {
        try {
            int bookingId = Integer.parseInt(txtBookingId.getText());
            double amount = Double.parseDouble(txtAmount.getText());

            Payment payment = new Payment(bookingId, new Date(), amount);

            if (paymentService.addPayment(payment)) {
                double remainder = paymentService.getRemainderAmount(bookingId);
                JOptionPane.showMessageDialog(this, "Payment successful! Remaining balance: " + remainder);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add payment.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaymentForm().setVisible(true));
    }
}
