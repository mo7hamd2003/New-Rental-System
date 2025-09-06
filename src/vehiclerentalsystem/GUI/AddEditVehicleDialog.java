package vehiclerentalsystem.GUI;

import vehiclerentalsystem.Controllers.VehicleController;
import vehiclerentalsystem.Models.Vehicle;
import vehiclerentalsystem.Models.User;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class AddEditVehicleDialog extends JDialog {
    private VehicleController vehicleController;
    private Vehicle vehicleToEdit;
    private User currentUser;
    private boolean isEditMode;
    
    // Form components
    private JTextField companyIdField;
    private JTextField plateNoField;
    private JTextField brandField;
    private JTextField modelField;
    private JTextField yearField;
    private JTextField typeField;
    private JComboBox<String> statusComboBox;
    private JTextField dailyRateField;
    private JTextArea descriptionArea;
    private JLabel imagePreviewLabel;
    private JButton selectImageButton;
    private JButton saveButton;
    private JButton cancelButton;
    
    private String selectedImagePath;
    
    public AddEditVehicleDialog(JFrame parent, Vehicle vehicle, VehicleController controller, User currentUser) {
        super(parent, vehicle == null ? "Add New Vehicle" : "Edit Vehicle", true);

        this.vehicleController = controller;
        this.vehicleToEdit = vehicle;
        this.currentUser = currentUser;
        this.isEditMode = vehicle != null;
        
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        
        if (isEditMode) {
            populateFields();
        }
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 700);
        setLocationRelativeTo(parent);
        setResizable(false);
    }
    
    private void initializeComponents() {
        // Text fields
        companyIdField = new JTextField(20);
        plateNoField = new JTextField(20);
        brandField = new JTextField(20);
        modelField = new JTextField(20);
        yearField = new JTextField(20);
        typeField = new JTextField(20);
        dailyRateField = new JTextField(20);
        
        // Status combo box
        String[] statuses = {"available", "rented", "maintenance", "out_of_service"};
        statusComboBox = new JComboBox<>(statuses);
        
        // Description text area
        descriptionArea = new JTextArea(4, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        // Image preview
        imagePreviewLabel = new JLabel();
        imagePreviewLabel.setPreferredSize(new Dimension(200, 150));
        imagePreviewLabel.setHorizontalAlignment(JLabel.CENTER);
        imagePreviewLabel.setVerticalAlignment(JLabel.CENTER);
        imagePreviewLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        imagePreviewLabel.setText("No Image Selected");
        imagePreviewLabel.setBackground(new Color(248, 249, 250));
        imagePreviewLabel.setOpaque(true);
        
        // Buttons
        selectImageButton = new JButton("Select Image");
        selectImageButton.setBackground(new Color(52, 152, 219));
        selectImageButton.setForeground(Color.WHITE);
        selectImageButton.setFocusPainted(false);
        
        saveButton = new JButton(isEditMode ? "Update Vehicle" : "Add Vehicle");
        saveButton.setBackground(new Color(39, 174, 96));
        saveButton.setForeground(Color.WHITE);
        saveButton.setPreferredSize(new Dimension(120, 35));
        saveButton.setFocusPainted(false);
        
        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(149, 165, 166));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setPreferredSize(new Dimension(120, 35));
        cancelButton.setFocusPainted(false);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Add form fields
        addFormField(formPanel, gbc, "Company ID:", companyIdField, 0);
        addFormField(formPanel, gbc, "Plate Number:", plateNoField, 1);
        addFormField(formPanel, gbc, "Brand:", brandField, 2);
        addFormField(formPanel, gbc, "Model:", modelField, 3);
        addFormField(formPanel, gbc, "Year:", yearField, 4);
        addFormField(formPanel, gbc, "Type:", typeField, 5);
        addFormField(formPanel, gbc, "Status:", statusComboBox, 6);
        addFormField(formPanel, gbc, "Daily Rate ($):", dailyRateField, 7);
        
        // Description field
        gbc.gridx = 0;
        gbc.gridy = 8;
        formPanel.add(new JLabel("Description:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 0.3;
        JScrollPane descScrollPane = new JScrollPane(descriptionArea);
        formPanel.add(descScrollPane, gbc);
        
        // Image section
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBorder(BorderFactory.createTitledBorder("Vehicle Image"));
        imagePanel.add(imagePreviewLabel, BorderLayout.CENTER);
        
        JPanel imageButtonPanel = new JPanel(new FlowLayout());
        imageButtonPanel.add(selectImageButton);
        imagePanel.add(imageButtonPanel, BorderLayout.SOUTH);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        // Layout assembly
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(formPanel, BorderLayout.CENTER);
        contentPanel.add(imagePanel, BorderLayout.EAST);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText, JComponent component, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0;
        panel.add(new JLabel(labelText), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(component, gbc);
    }
    
    private void setupEventHandlers() {
        selectImageButton.addActionListener(e -> selectImage());
        saveButton.addActionListener(e -> saveVehicle());
        cancelButton.addActionListener(e -> dispose());
    }
    
    private void populateFields() {
        if (vehicleToEdit != null) {
            companyIdField.setText(String.valueOf(vehicleToEdit.getCompanyId()));
            plateNoField.setText(vehicleToEdit.getPlateNb());
            brandField.setText(vehicleToEdit.getBrand());
            modelField.setText(vehicleToEdit.getModel());
            yearField.setText(String.valueOf(vehicleToEdit.getYear()));
            typeField.setText(vehicleToEdit.getType());
            statusComboBox.setSelectedItem(vehicleToEdit.getStatus());
            dailyRateField.setText(String.valueOf(vehicleToEdit.getDailyRate()));
            descriptionArea.setText(vehicleToEdit.getDescription());
            
            // Load existing image
            if (vehicleToEdit.getImagePath() != null && !vehicleToEdit.getImagePath().isEmpty()) {
                selectedImagePath = vehicleToEdit.getImagePath();
                loadImagePreview(selectedImagePath);
            }
        }
    }
    
    private void selectImage() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Image files", "jpg", "jpeg", "png", "gif", "bmp");
        fileChooser.setFileFilter(filter);
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            selectedImagePath = selectedFile.getAbsolutePath();
            loadImagePreview(selectedImagePath);
        }
    }
    
    private void loadImagePreview(String imagePath) {
        try {
            ImageIcon originalIcon = new ImageIcon(imagePath);
            Image img = originalIcon.getImage();
            Image scaledImg = img.getScaledInstance(200, 150, Image.SCALE_SMOOTH);
            imagePreviewLabel.setIcon(new ImageIcon(scaledImg));
            imagePreviewLabel.setText("");
        } catch (Exception e) {
            imagePreviewLabel.setIcon(null);
            imagePreviewLabel.setText("Error loading image");
        }
    }
    
    private void saveVehicle() {
        if (!validateInput()) {
            return;
        }
        
        try {
            Vehicle vehicle = createVehicleFromInput();
            
            boolean success;
            if (isEditMode) {
                vehicle.setId(vehicleToEdit.getId());
                success = vehicleController.updateVehicle(vehicle);
            } else {
                success = vehicleController.createVehicle(vehicle);
            }
            
            if (success) {
                JOptionPane.showMessageDialog(this, 
                    "Vehicle " + (isEditMode ? "updated" : "added") + " successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to " + (isEditMode ? "update" : "add") + " vehicle. Please try again.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + e.getMessage(), 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validateInput() {
        if (brandField.getText().trim().isEmpty()) {
            showErrorMessage("Brand is required!");
            brandField.requestFocus();
            return false;
        }
        
        if (modelField.getText().trim().isEmpty()) {
            showErrorMessage("Model is required!");
            modelField.requestFocus();
            return false;
        }
        
        if (plateNoField.getText().trim().isEmpty()) {
            showErrorMessage("Plate number is required!");
            plateNoField.requestFocus();
            return false;
        }
        
        try {
            int year = Integer.parseInt(yearField.getText().trim());
            if (year < 1900 || year > 2025) {
                throw new NumberFormatException("Year must be between 1900 and 2025");
            }
        } catch (NumberFormatException e) {
            showErrorMessage("Please enter a valid year (1900-2025)!");
            yearField.requestFocus();
            return false;
        }
        
        if (dailyRateField.getText().trim().isEmpty()) {
            showErrorMessage("Daily rate is required!");
            dailyRateField.requestFocus();
            return false;
        }
        
        try {
            Double.parseDouble(dailyRateField.getText().trim());
        } catch (NumberFormatException e) {
            showErrorMessage("Please enter a valid daily rate!");
            dailyRateField.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private Vehicle createVehicleFromInput() {
        Vehicle vehicle = new Vehicle();

        vehicle.setCompanyId(Integer.parseInt(companyIdField.getText().trim()));
        vehicle.setPlateNb(plateNoField.getText().trim());
        vehicle.setBrand(brandField.getText().trim());
        vehicle.setModel(modelField.getText().trim());
        vehicle.setYear(Integer.parseInt(yearField.getText().trim()));
        vehicle.setType(typeField.getText().trim());
        vehicle.setStatus((String) statusComboBox.getSelectedItem());
        vehicle.setDailyRate(Integer.parseInt(dailyRateField.getText().trim()));
        vehicle.setDescription(descriptionArea.getText().trim());

        // Set the user who added this vehicle
        if (currentUser != null) {
            vehicle.setAddedByUserId(currentUser.getID());
        }
        
        // Handle image
        if (selectedImagePath != null && !selectedImagePath.isEmpty()) {
            // For new vehicles, save image to project folder
            if (!isEditMode) {
                // Generate a temporary ID for image naming (you might want to get actual ID after insert)
                int tempId = (int) (System.currentTimeMillis() % 10000);
                String savedImagePath = vehicleController.saveVehicleImage(selectedImagePath, tempId);
                vehicle.setImagePath(savedImagePath);
            } else {
                // For existing vehicles, check if it's a new image
                if (!selectedImagePath.equals(vehicleToEdit.getImagePath())) {
                    String savedImagePath = vehicleController.saveVehicleImage(selectedImagePath, vehicleToEdit.getId());
                    vehicle.setImagePath(savedImagePath);
                } else {
                    vehicle.setImagePath(selectedImagePath);
                }
            }
        }
        
        return vehicle;
    }
    
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Input Error", JOptionPane.ERROR_MESSAGE);
    }
}
