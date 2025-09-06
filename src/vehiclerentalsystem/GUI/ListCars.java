package vehiclerentalsystem.GUI;

import vehiclerentalsystem.Controllers.VehicleController;
import vehiclerentalsystem.Models.Vehicle;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListCars extends JFrame {
    private VehicleController vehicleController;
    private JTable vehicleTable;
    private DefaultTableModel tableModel;
    private Class<?> currentFrame;
    private JPanel headerPanel;
    private vehiclerentalsystem.Models.User currentUser;

    public ListCars() {
        this(null);
    }

    public ListCars(vehiclerentalsystem.Models.User currentUser) {
        this.currentUser = currentUser;
        vehicleController = new VehicleController();
        initComponents();
        currentFrame = this.getClass();
        setupMainContent();
        loadVehicles();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Vehicle List");
        setPreferredSize(new java.awt.Dimension(1200, 800));

        // Create the table model with column names
        String[] columnNames = {"ID", "Brand", "Model", "Year", "Type", "Daily Rate", "Status", "Plate Number"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };

        // Create the table
        vehicleTable = new JTable(tableModel);
        vehicleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        vehicleTable.getTableHeader().setReorderingAllowed(false);
        vehicleTable.setRowHeight(25);

        // Set column widths
        vehicleTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        vehicleTable.getColumnModel().getColumn(1).setPreferredWidth(100); // Brand
        vehicleTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Model
        vehicleTable.getColumnModel().getColumn(3).setPreferredWidth(60);  // Year
        vehicleTable.getColumnModel().getColumn(4).setPreferredWidth(100); // Type
        vehicleTable.getColumnModel().getColumn(5).setPreferredWidth(80);  // Daily Rate
        vehicleTable.getColumnModel().getColumn(6).setPreferredWidth(80);  // Status
        vehicleTable.getColumnModel().getColumn(7).setPreferredWidth(100); // Plate
    }

    private void setupMainContent() {
        // Set main layout
        getContentPane().setLayout(new BorderLayout());

        // Create header panel
        headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create buttons
        JButton editButton = createHeaderButton("Edit Vehicle", new Color(52, 152, 219));
        JButton deleteButton = createHeaderButton("Delete Vehicle", new Color(231, 76, 60));
        JButton refreshButton = createHeaderButton("Refresh", new Color(149, 165, 166));

        // Add action listeners
        editButton.addActionListener(e -> editSelectedVehicle());
        deleteButton.addActionListener(e -> deleteSelectedVehicle());
        refreshButton.addActionListener(e -> loadVehicles());

        // Add buttons to header
        headerPanel.add(editButton);
        headerPanel.add(deleteButton);
        headerPanel.add(refreshButton);

        // Create scroll pane for table
        JScrollPane scrollPane = new JScrollPane(vehicleTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add components to frame
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    private JButton createHeaderButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(120, 35));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void loadVehicles() {
        // Clear existing data
        tableModel.setRowCount(0);
        
        // Load vehicles from controller
        List<Vehicle> vehicles = vehicleController.loadAllVehicles();
        
        // Add vehicles to table
        for (Vehicle vehicle : vehicles) {
            Object[] row = {
                vehicle.getId(),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getYear(),
                vehicle.getType(),
                vehicle.getDailyRate(),
                vehicle.getStatus(),
                vehicle.getPlateNb()
            };
            tableModel.addRow(row);
        }
    }

    private void editSelectedVehicle() {
        int selectedRow = vehicleTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select a vehicle to edit.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Get the vehicle ID from the selected row
        int vehicleId = (int) tableModel.getValueAt(selectedRow, 0);
        
        // Find the vehicle in the controller
        Vehicle selectedVehicle = vehicleController.getVehicleById(vehicleId);
        
        if (selectedVehicle != null) {
            AddEditVehicleDialog dialog = new AddEditVehicleDialog(this, selectedVehicle, vehicleController, currentUser);
            dialog.setVisible(true);
            // Refresh the table after editing
            loadVehicles();
        }
    }

    private void deleteSelectedVehicle() {
        int selectedRow = vehicleTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select a vehicle to delete.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Get the vehicle details for confirmation
        String brand = (String) tableModel.getValueAt(selectedRow, 1);
        String model = (String) tableModel.getValueAt(selectedRow, 2);
        String plate = (String) tableModel.getValueAt(selectedRow, 7);
        int vehicleId = (int) tableModel.getValueAt(selectedRow, 0);

        // Confirm deletion
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete this vehicle?\n" +
            "Brand: " + brand + "\n" +
            "Model: " + model + "\n" +
            "Plate: " + plate,
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean deleted = vehicleController.removeVehicle(vehicleId);
                if (deleted) {
                    JOptionPane.showMessageDialog(this,
                        "Vehicle deleted successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                    loadVehicles(); // Refresh the table
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Failed to delete the vehicle. Please try again.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "An error occurred while deleting the vehicle: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
