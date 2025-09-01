package vehiclerentalsystem.GUI;

import vehiclerentalsystem.Models.Customer;
import vehiclerentalsystem.Services.CustomerService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class CustomerManagementForm extends JFrame {
    private CustomerService customerService;

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtFirstname, txtLastname, txtPhone, txtEmail;

    public CustomerManagementForm() {
        customerService = new CustomerService();
        initUI();
        loadCustomers();
    }

    private void initUI() {
        setTitle("Customer Management");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Table
        tableModel = new DefaultTableModel(new Object[] { "ID", "Firstname", "Lastname", "Phone", "Email" }, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Form fields
        JPanel formPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        txtFirstname = new JTextField();
        txtLastname = new JTextField();
        txtPhone = new JTextField();
        txtEmail = new JTextField();

        formPanel.add(new JLabel("Firstname:"));
        formPanel.add(txtFirstname);
        formPanel.add(new JLabel("Lastname:"));
        formPanel.add(txtLastname);
        formPanel.add(new JLabel("Phone:"));
        formPanel.add(txtPhone);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(txtEmail);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnRefresh = new JButton("Refresh");
        JButton btnBack = new JButton("← Back");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnBack);

        // Add components
        setLayout(new BorderLayout(10, 10));
        add(scrollPane, BorderLayout.CENTER);
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        // Event listeners
        btnAdd.addActionListener(this::addCustomer);
        btnUpdate.addActionListener(this::updateCustomer);
        btnDelete.addActionListener(this::deleteCustomer);
        btnRefresh.addActionListener(e -> loadCustomers());
        btnBack.addActionListener(e -> {
            dispose(); // close this window
            new AdminDashboard().setVisible(true); // go back to dashboard
        });

        // Select row → fill fields
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                txtFirstname.setText(tableModel.getValueAt(row, 1).toString());
                txtLastname.setText(tableModel.getValueAt(row, 2).toString());
                txtPhone.setText(tableModel.getValueAt(row, 3).toString());
                txtEmail.setText(tableModel.getValueAt(row, 4).toString());
            }
        });
    }

    private void loadCustomers() {
        tableModel.setRowCount(0); // clear
        List<Customer> customers = customerService.getAllCustomers();
        for (Customer c : customers) {
            tableModel.addRow(
                    new Object[] { c.getId(), c.getFirstname(), c.getLastname(), c.getPhoneNb(), c.getEmail() });
        }
    }

    private void addCustomer(ActionEvent e) {
        try {
            String firstname = txtFirstname.getText();
            String lastname = txtLastname.getText();
            int phone = Integer.parseInt(txtPhone.getText());
            String email = txtEmail.getText();

            Customer customer = new Customer(firstname, lastname, phone, email);
            if (customerService.addCustomer(customer)) {
                JOptionPane.showMessageDialog(this, "Customer added successfully!");
                loadCustomers();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add customer.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
        }
    }

    private void updateCustomer(ActionEvent e) {
        try {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a customer to update.");
                return;
            }

            int id = (int) tableModel.getValueAt(row, 0);
            String firstname = txtFirstname.getText();
            String lastname = txtLastname.getText();
            int phone = Integer.parseInt(txtPhone.getText());
            String email = txtEmail.getText();

            Customer customer = new Customer(id, firstname, lastname, phone, email);
            if (customerService.updateCustomer(customer)) {
                JOptionPane.showMessageDialog(this, "Customer updated successfully!");
                loadCustomers();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update customer.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
        }
    }

    private void deleteCustomer(ActionEvent e) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a customer to delete.");
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this customer?");
        if (confirm == JOptionPane.YES_OPTION) {
            if (customerService.deleteCustomer(id)) {
                JOptionPane.showMessageDialog(this, "Customer deleted successfully!");
                loadCustomers();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete customer.");
            }
        }
    }

    // For quick testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CustomerManagementForm().setVisible(true));
    }
}
