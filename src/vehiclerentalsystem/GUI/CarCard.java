package vehiclerentalsystem.GUI;

import vehiclerentalsystem.Models.Vehicle;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;


public class CarCard extends JPanel {
    private Vehicle vehicle;
    private JLabel imageLabel;
    private JLabel brandLabel;
    private JLabel modelLabel;
    private JLabel yearLabel;
    private JLabel statusLabel;
    private JLabel plateLabel;
    private JButton showMoreButton;
    private boolean isSelected = false;


    // Selection colors;
    private static final Color SELECTED_COLOR = new Color(52, 152, 219);
    private static final Color DEFAULT_COLOR = Color.WHITE;
    private static final Color HOVER_COLOR = new Color(248, 248, 250);

    public CarCard(Vehicle vehicle) {
        this.vehicle = vehicle;
        initializeComponents();
        setupLayout();
        loadData();
        setupMouseEvent();
    }

    private void initializeComponents() {
        setPreferredSize(new Dimension(280, 420));
        setBackground(DEFAULT_COLOR);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Image label
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(260, 180));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

        // Text labels
        brandLabel = new JLabel();
        brandLabel.setHorizontalAlignment(JLabel.CENTER);
        brandLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        brandLabel.setForeground(new Color(44, 62, 80));
        
        modelLabel = new JLabel();
        modelLabel.setHorizontalAlignment(JLabel.CENTER);
        modelLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        modelLabel.setForeground(new Color(127, 140, 141));
        
        yearLabel = new JLabel();
        yearLabel.setHorizontalAlignment(JLabel.CENTER);
        yearLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        yearLabel.setForeground(new Color(149, 165, 166));
        
        plateLabel = new JLabel();
        plateLabel.setHorizontalAlignment(JLabel.CENTER);
        plateLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        plateLabel.setForeground(new Color(52, 73, 94));
        
        statusLabel = new JLabel();
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        statusLabel.setOpaque(true);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 8));
        
        showMoreButton = new JButton("Show More");
        showMoreButton.setBackground(new Color(108, 117, 125));
        showMoreButton.setForeground(Color.WHITE);
        showMoreButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        showMoreButton.setFocusPainted(false);
        showMoreButton.setBorderPainted(false);
        showMoreButton.setPreferredSize(new Dimension(100, 25));
        showMoreButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Show more button action
        showMoreButton.addActionListener(e -> showVehicleDetails());
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setOpaque(false);

        // Add Components with spacing
        contentPanel.add(imageLabel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(brandLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(modelLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(yearLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(plateLabel);
        contentPanel.add(Box.createVerticalStrut(8));
        contentPanel.add(statusLabel);
        contentPanel.add(Box.createVerticalStrut(12));
        
         // Center the button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(showMoreButton);
        contentPanel.add(buttonPanel);

        add(contentPanel, BorderLayout.CENTER);
    }

    private void setupMouseEvent() {
                MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!isSelected) {
                    setBackground(HOVER_COLOR);
                    repaint();
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (!isSelected) {
                    setBackground(DEFAULT_COLOR);
                    repaint();
                }
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                toggleSelection();
                // Notify parent component about selection change
                firePropertyChange("vehicleSelected", null, vehicle);
            }
        };
        
        addMouseListener(mouseAdapter);
    }

    private void loadData() {
        brandLabel.setText(vehicle.getBrand());
        modelLabel.setText(vehicle.getModel());
        yearLabel.setText(String.valueOf(vehicle.getYear()));
        plateLabel.setText("Plate: " + vehicle.getPlateNb());

        // Set status with appropriate color
        statusLabel.setText(vehicle.getStatus().toUpperCase());
        setStatusColor();

        // load vehicle image
        loadVehicleImage();
    }

     private void setStatusColor() {
        String status = vehicle.getStatus().toLowerCase();
        switch (status) {
            case "available":
                statusLabel.setBackground(new Color(46, 204, 113));
                statusLabel.setForeground(Color.WHITE);
                break;
            case "rented":
                statusLabel.setBackground(new Color(231, 76, 60));
                statusLabel.setForeground(Color.WHITE);
                break;
            case "maintenance":
                statusLabel.setBackground(new Color(243, 156, 18));
                statusLabel.setForeground(Color.WHITE);
                break;
            default:
                statusLabel.setBackground(new Color(149, 165, 166));
                statusLabel.setForeground(Color.WHITE);
        }
    }

    private void loadVehicleImage() {
        try {
            
            String imagePath = vehicle.getImagePath();
            if (imagePath != null && !imagePath.isEmpty()){
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    ImageIcon originalIcon = new ImageIcon(imagePath);
                    Image img = originalIcon.getImage();
                    Image scaledImg = img.getScaledInstance(260, 180, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(scaledImg));
                    imageLabel.setText("");
                } else {
                    setPlaceholderImage();
                }
            } else {
                setPlaceholderImage();
            }

        } catch(Exception e){

        }
    }

    private void setPlaceholderImage() {
        imageLabel.setIcon(null);
        imageLabel.setText("No Image");
        imageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        imageLabel.setBackground(new Color(236, 240, 241));
        imageLabel.setForeground(new Color(149, 165, 166));
        imageLabel.setOpaque(true);
    }

        private void showVehicleDetails() {
        String details = String.format(
            "<html><body style='width: 300px; padding: 10px;'>" +
            "<h2>%s %s</h2>" +
            "<p><strong>Year:</strong> %d</p>" +
            "<p><strong>Plate Number:</strong> %s</p>" +
            "<p><strong>Type:</strong> %s</p>" +
            "<p><strong>Status:</strong> %s</p>" +
            "<p><strong>Daily Rate:</strong> $%s</p>" +
            "<p><strong>Company ID:</strong> %s</p>" +
            "<p><strong>Description:</strong><br>%s</p>" +
            "</body></html>",
            vehicle.getBrand(), vehicle.getModel(),
            vehicle.getYear(),
            vehicle.getPlateNb(),
            vehicle.getType(),
            vehicle.getStatus(),
            vehicle.getDailyRate(),
            vehicle.getCompanyId(),
            vehicle.getDescription() != null ? vehicle.getDescription() : "No description available"
        );
        
        JOptionPane.showMessageDialog(
            this,
            details,
            "Vehicle Details",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    public void toggleSelection() {
        isSelected = !isSelected;
        if (isSelected) {
            setBackground(SELECTED_COLOR);
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SELECTED_COLOR, 2),
                BorderFactory.createEmptyBorder(9, 9, 9, 9)
            ));
        } else {
            setBackground(DEFAULT_COLOR);
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
        }
        repaint();
    }

     public void setSelected(boolean selected) {
        if (this.isSelected != selected) {
            toggleSelection();
        }
    }
    
    public boolean isSelected() {
        return isSelected;
    }
    
    public Vehicle getVehicle() {
        return vehicle;
    }
    
    public void refreshData(Vehicle updatedVehicle) {
        this.vehicle = updatedVehicle;
        loadData();
        repaint();
    }
}