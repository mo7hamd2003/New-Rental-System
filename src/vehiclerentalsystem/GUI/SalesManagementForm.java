package vehiclerentalsystem.GUI;

import vehiclerentalsystem.Controllers.BookingController;
import vehiclerentalsystem.Controllers.VehicleController;
import vehiclerentalsystem.Models.Booking;
import vehiclerentalsystem.Utils.UIUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class SalesManagementForm extends JFrame {
    private BookingController bookingController;
    private VehicleController vehicleController;

    // UI Components
    private JTable salesTable;
    private DefaultTableModel tableModel;
    private JLabel totalRevenueLabel;
    private JLabel totalBookingsLabel;
    private JLabel avgBookingValueLabel;
    private JComboBox<String> periodComboBox;
    private JButton refreshButton;
    private JButton exportButton;

    public SalesManagementForm() {
        bookingController = new BookingController();
        vehicleController = new VehicleController();

        initializeComponents();
        setupLayout();
        setupStyling();
        loadSalesData();

        setTitle("Sales Management");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void initializeComponents() {
        // Table setup
        String[] columnNames = {"Booking ID", "Customer", "Vehicle", "Start Date", "End Date",
                               "Duration", "Total Amount", "Status", "Payment Method"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        salesTable = new JTable(tableModel);
        salesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        salesTable.getTableHeader().setReorderingAllowed(false);
        salesTable.setRowHeight(25);

        // Labels for statistics
        totalRevenueLabel = new JLabel("Total Revenue: $0.00");
        totalBookingsLabel = new JLabel("Total Bookings: 0");
        avgBookingValueLabel = new JLabel("Average Booking Value: $0.00");

        // Controls
        periodComboBox = new JComboBox<>(new String[]{"All Time", "This Month", "Last Month", "This Year", "Last Year"});
        refreshButton = new JButton("Refresh");
        exportButton = new JButton("Export Report");

        // Event listeners
        periodComboBox.addActionListener(this::periodChanged);
        refreshButton.addActionListener(this::refreshData);
        exportButton.addActionListener(this::exportReport);
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Top panel with controls and statistics
        JPanel topPanel = new JPanel(new BorderLayout());

        // Controls panel
        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlsPanel.add(new JLabel("Period:"));
        controlsPanel.add(periodComboBox);
        controlsPanel.add(refreshButton);
        controlsPanel.add(exportButton);

        // Statistics panel
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        UIUtil.stylePanel(statsPanel);
        statsPanel.add(totalRevenueLabel);
        statsPanel.add(totalBookingsLabel);
        statsPanel.add(avgBookingValueLabel);

        topPanel.add(controlsPanel, BorderLayout.NORTH);
        topPanel.add(statsPanel, BorderLayout.CENTER);

        // Table panel
        JScrollPane scrollPane = new JScrollPane(salesTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Sales Details"));

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void setupStyling() {
        UIUtil.styleFrame(this);

        // Style labels
        Font statFont = new Font("Segoe UI", Font.BOLD, 16);
        totalRevenueLabel.setFont(statFont);
        totalBookingsLabel.setFont(statFont);
        avgBookingValueLabel.setFont(statFont);

        totalRevenueLabel.setForeground(new Color(46, 204, 113));
        totalBookingsLabel.setForeground(new Color(52, 152, 219));
        avgBookingValueLabel.setForeground(new Color(155, 89, 182));

        // Style buttons
        UIUtil.styleButton(refreshButton, new Color(52, 152, 219));
        UIUtil.styleButton(exportButton, new Color(46, 204, 113));
    }

    private void loadSalesData() {
        tableModel.setRowCount(0);

        List<Booking> bookings = bookingController.getAllBookings();
        String selectedPeriod = (String) periodComboBox.getSelectedItem();
        bookings = filterBookingsByPeriod(bookings, selectedPeriod);

        double totalRevenue = 0.0;
        int totalBookings = bookings.size();

        for (Booking booking : bookings) {
            // Calculate booking duration and amount (simplified)
            long duration = 1; // Default 1 day
            if (booking.getStartDate() != null && booking.getEndDate() != null) {
                LocalDate startDate = new java.sql.Date(booking.getStartDate().getTime()).toLocalDate();
                LocalDate endDate = new java.sql.Date(booking.getEndDate().getTime()).toLocalDate();
                duration = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);
                if (duration == 0) duration = 1; // Minimum 1 day
            }

            double amount = duration * 50.0; // Simplified: $50 per day
            totalRevenue += amount;

            // Get customer and vehicle info
            String customerName = "Customer #" + booking.getCustomerId();
            String vehicleModel = "Vehicle #" + booking.getVehicleId();

            // Try to get actual names if available
            try {
                var vehicle = vehicleController.getVehicleById(booking.getVehicleId());
                if (vehicle != null) {
                    vehicleModel = vehicle.getBrand() + " " + vehicle.getModel();
                }
            } catch (Exception e) {
                // Use default names if lookup fails
            }

            String status = booking.getReturnDate() != null ? "Completed" : "Active";
            String paymentMethod = "Credit Card"; // Default payment method

            Object[] row = {
                booking.getId(),
                customerName,
                vehicleModel,
                booking.getStartDate() != null ? new java.sql.Date(booking.getStartDate().getTime()).toLocalDate().toString() : "N/A",
                booking.getEndDate() != null ? new java.sql.Date(booking.getEndDate().getTime()).toLocalDate().toString() : "N/A",
                duration + " days",
                String.format("$%.2f", amount),
                status,
                paymentMethod
            };
            tableModel.addRow(row);
        }

        // Update statistics
        totalRevenueLabel.setText(String.format("Total Revenue: $%.2f", totalRevenue));
        totalBookingsLabel.setText("Total Bookings: " + totalBookings);
        avgBookingValueLabel.setText(String.format("Average Booking Value: $%.2f",
            totalBookings > 0 ? totalRevenue / totalBookings : 0.0));
    }

    private List<Booking> filterBookingsByPeriod(List<Booking> bookings, String period) {
        LocalDate now = LocalDate.now();

        return bookings.stream().filter(booking -> {
            if (booking.getStartDate() == null) return true;

            LocalDate bookingDate = new java.sql.Date(booking.getStartDate().getTime()).toLocalDate();

            switch (period) {
                case "This Month":
                    return bookingDate.getMonth() == now.getMonth() &&
                           bookingDate.getYear() == now.getYear();
                case "Last Month":
                    LocalDate lastMonth = now.minusMonths(1);
                    return bookingDate.getMonth() == lastMonth.getMonth() &&
                           bookingDate.getYear() == lastMonth.getYear();
                case "This Year":
                    return bookingDate.getYear() == now.getYear();
                case "Last Year":
                    return bookingDate.getYear() == now.getYear() - 1;
                default:
                    return true; // All Time
            }
        }).collect(Collectors.toList());
    }

    private void periodChanged(ActionEvent e) {
        loadSalesData();
    }

    private void refreshData(ActionEvent e) {
        loadSalesData();
        JOptionPane.showMessageDialog(this, "Sales data refreshed!", "Success",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void exportReport(ActionEvent e) {
        JOptionPane.showMessageDialog(this,
            "Export functionality would generate a PDF/Excel report with sales data.\n" +
            "This feature can be implemented with libraries like Apache POI or iText.",
            "Export Report", JOptionPane.INFORMATION_MESSAGE);
    }

    // Helper method to get customer by ID (simplified)
    private Object getCustomerById(int customerId) {
        // This would normally use CustomerController
        // For now, return null to use default names
        return null;
    }
}