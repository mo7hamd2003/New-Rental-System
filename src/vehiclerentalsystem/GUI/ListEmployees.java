package vehiclerentalsystem.GUI;

import vehiclerentalsystem.Controllers.UserController;
import vehiclerentalsystem.Models.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListEmployees extends JFrame {
    private UserController userController;
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private JPanel headerPanel;

    public ListEmployees() {
        userController = new UserController();
        initComponents();
        setupMainContent();
        loadEmployees();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Employee Management");
        setPreferredSize(new java.awt.Dimension(1200, 800));

        // Create the table model with column names
        String[] columnNames = {"ID", "Username", "Email", "Status", "Role"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };

        // Create the table
        employeeTable = new JTable(tableModel);
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        employeeTable.getTableHeader().setReorderingAllowed(false);
        employeeTable.setRowHeight(25);

        // Set column widths
        employeeTable.getColumnModel().getColumn(0).setPreferredWidth(50);   // ID
        employeeTable.getColumnModel().getColumn(1).setPreferredWidth(150);  // Username
        employeeTable.getColumnModel().getColumn(2).setPreferredWidth(200);  // Email
        employeeTable.getColumnModel().getColumn(3).setPreferredWidth(100);  // Status
        employeeTable.getColumnModel().getColumn(4).setPreferredWidth(100);  // Role
    }

    private void setupMainContent() {
        // Set main layout
        getContentPane().setLayout(new BorderLayout());

        // Create header panel
        headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create buttons
        JButton changeRoleButton = createHeaderButton("Change Role", new Color(52, 152, 219));
        JButton deactivateButton = createHeaderButton("Deactivate", new Color(243, 156, 18));
        JButton deleteButton = createHeaderButton("Delete", new Color(231, 76, 60));
        JButton refreshButton = createHeaderButton("Refresh", new Color(149, 165, 166));

        // Add action listeners
        changeRoleButton.addActionListener(e -> changeEmployeeRole());
        deactivateButton.addActionListener(e -> toggleEmployeeStatus());
        deleteButton.addActionListener(e -> deleteEmployee());
        refreshButton.addActionListener(e -> loadEmployees());

        // Add buttons to header
        headerPanel.add(changeRoleButton);
        headerPanel.add(deactivateButton);
        headerPanel.add(deleteButton);
        headerPanel.add(refreshButton);

        // Create scroll pane for table
        JScrollPane scrollPane = new JScrollPane(employeeTable);
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

    private void loadEmployees() {
        // Clear existing data
        tableModel.setRowCount(0);
        
        // Load employees (users with roleID = 2)
        List<User> employees = userController.getAllEmployees();
        
        // Add employees to table
        for (User employee : employees) {
            Object[] row = {
                employee.getID(),
                employee.getUsername(),
                employee.getEmail(),
                employee.isActive() ? "Active" : "Inactive",
                getRoleName(employee.getRoleID())
            };
            tableModel.addRow(row);
        }
    }

    private String getRoleName(int roleId) {
        return roleId == 1 ? "Admin" : "Employee";
    }

    private void changeEmployeeRole() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select an employee.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int employeeId = (int) tableModel.getValueAt(selectedRow, 0);
        String username = (String) tableModel.getValueAt(selectedRow, 1);
        int currentRole = tableModel.getValueAt(selectedRow, 4).equals("Admin") ? 1 : 2;

        String[] options = {"Admin", "Employee"};
        String selectedRole = (String) JOptionPane.showInputDialog(this,
            "Select new role for " + username,
            "Change Role",
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[currentRole - 1]);

        if (selectedRole != null) {
            int newRoleId = selectedRole.equals("Admin") ? 1 : 2;
            try {
                boolean updated = userController.updateUserRole(employeeId, newRoleId);
                if (updated) {
                    JOptionPane.showMessageDialog(this,
                        "Role updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                    loadEmployees();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Failed to update role.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error updating role: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void toggleEmployeeStatus() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select an employee.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int employeeId = (int) tableModel.getValueAt(selectedRow, 0);
        String username = (String) tableModel.getValueAt(selectedRow, 1);
        boolean isCurrentlyActive = tableModel.getValueAt(selectedRow, 3).equals("Active");

        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to " + (isCurrentlyActive ? "deactivate" : "activate") + 
            " the account for " + username + "?",
            "Confirm Status Change",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean updated = userController.toggleUserStatus(employeeId);
                if (updated) {
                    JOptionPane.showMessageDialog(this,
                        "Account status updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                    loadEmployees();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Failed to update account status.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error updating status: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select an employee.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int employeeId = (int) tableModel.getValueAt(selectedRow, 0);
        String username = (String) tableModel.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to permanently delete the account for " + username + "?\n" +
            "This action cannot be undone!",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean deleted = userController.deleteEmployee(employeeId);
                if (deleted) {
                    JOptionPane.showMessageDialog(this,
                        "Employee deleted successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                    loadEmployees();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Failed to delete employee.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error deleting employee: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
