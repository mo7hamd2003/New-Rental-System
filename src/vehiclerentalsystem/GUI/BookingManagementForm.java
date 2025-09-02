package vehiclerentalsystem.GUI;

import vehiclerentalsystem.Models.Booking;
import vehiclerentalsystem.Services.BookingService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BookingManagementForm extends JFrame {
    private BookingService bookingService;
    private JTable bookingTable;
    private DefaultTableModel tableModel;

    private JTextField txtVehicleId, txtCustomerId, txtUserId, txtStartDate, txtEndDate, txtReturnDate;

    public BookingManagementForm() {
        bookingService = new BookingService();
        setTitle("Booking Management");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
        loadBookings();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // ===== Table =====
        tableModel = new DefaultTableModel(new Object[] {
                "ID", "VehicleId", "CustomerId", "UserId", "StartDate", "EndDate", "ReturnDate", "TotalAmount",
                "RemainderAmount"
        }, 0);
        bookingTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookingTable);
        add(scrollPane, BorderLayout.CENTER);

        // ===== Form panel =====
        JPanel formPanel = new JPanel(new GridLayout(9, 2, 5, 5));

        formPanel.add(new JLabel("Vehicle ID:"));
        txtVehicleId = new JTextField();
        formPanel.add(txtVehicleId);

        formPanel.add(new JLabel("Customer ID:"));
        txtCustomerId = new JTextField();
        formPanel.add(txtCustomerId);

        formPanel.add(new JLabel("User ID:"));
        txtUserId = new JTextField();
        formPanel.add(txtUserId);

        formPanel.add(new JLabel("Start Date (yyyy-mm-dd):"));
        txtStartDate = new JTextField();
        formPanel.add(txtStartDate);

        formPanel.add(new JLabel("End Date (yyyy-mm-dd):"));
        txtEndDate = new JTextField();
        formPanel.add(txtEndDate);

        formPanel.add(new JLabel("Return Date (yyyy-mm-dd):"));
        txtReturnDate = new JTextField();
        formPanel.add(txtReturnDate);

        add(formPanel, BorderLayout.NORTH);

        // ===== Buttons =====
        JPanel buttonPanel = new JPanel();

        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnBack = new JButton("← Back");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnBack);

        add(buttonPanel, BorderLayout.SOUTH);

        // ===== Button Actions =====
        btnAdd.addActionListener(e -> addBooking());
        btnUpdate.addActionListener(e -> updateBooking());
        btnDelete.addActionListener(e -> deleteBooking());
        btnBack.addActionListener(e -> {
            dispose();
            new AdminDashboard().setVisible(true);
        });
    }

    private void loadBookings() {
        tableModel.setRowCount(0);
        List<Booking> bookings = bookingService.getAllBookings();
        for (Booking booking : bookings) {
            tableModel.addRow(new Object[] {
                    booking.getId(),
                    booking.getVehicleId(),
                    booking.getCustomerId(),
                    booking.getUserId(),
                    booking.getStartDate(),
                    booking.getEndDate(),
                    booking.getReturnDate(),
                    booking.getTotalAmount(),
                    booking.getRemainderAmount()
            });
        }
    }

    private void addBooking() {
        try {
            Booking booking = new Booking(
                    Integer.parseInt(txtVehicleId.getText()),
                    Integer.parseInt(txtCustomerId.getText()),
                    Integer.parseInt(txtUserId.getText()),
                    java.sql.Date.valueOf(txtStartDate.getText()),
                    java.sql.Date.valueOf(txtEndDate.getText()),
                    txtReturnDate.getText().isEmpty() ? null : java.sql.Date.valueOf(txtReturnDate.getText()));

            if (bookingService.addBooking(booking)) {
                JOptionPane.showMessageDialog(this, "Booking added successfully!");
                loadBookings();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add booking.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
        }
    }

    private void updateBooking() {
        int selectedRow = bookingTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to update.");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);

        try {
            Booking booking = new Booking(
                    id,
                    Integer.parseInt(txtVehicleId.getText()),
                    Integer.parseInt(txtCustomerId.getText()),
                    Integer.parseInt(txtUserId.getText()),
                    java.sql.Date.valueOf(txtStartDate.getText()),
                    java.sql.Date.valueOf(txtEndDate.getText()),
                    txtReturnDate.getText().isEmpty() ? null : java.sql.Date.valueOf(txtReturnDate.getText()));

            if (bookingService.updateBooking(booking)) {
                JOptionPane.showMessageDialog(this, "Booking updated successfully!");
                loadBookings();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update booking.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
        }
    }

    private void deleteBooking() {
        int selectedRow = bookingTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to delete.");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);

        if (bookingService.deleteBooking(id)) {
            JOptionPane.showMessageDialog(this, "Booking deleted successfully!");
            loadBookings();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete booking.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BookingManagementForm().setVisible(true));
    }
}
