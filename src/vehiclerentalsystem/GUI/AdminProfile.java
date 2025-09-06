package vehiclerentalsystem.GUI;

import vehiclerentalsystem.Models.User;
import vehiclerentalsystem.Controllers.UserController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import vehiclerentalsystem.Utils.UIUtil;

public class AdminProfile extends JFrame {
    private User currentUser;
    private UserController userController;
    private JLabel avatarLabel;
    private JLabel nameLabel;
    private JLabel emailLabel;
    private JTextField nameField;
    private JTextField emailField;
    private JButton saveButton;
    private JButton changePasswordButton;
    private JButton changeAvatarButton;

    public AdminProfile(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        this.currentUser = user;
        this.userController = new UserController();

        initializeComponents();
        setupLayout();
        setupStyling();
        loadUserData();

        setTitle("Admin Profile");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void initializeComponents() {
        avatarLabel = new JLabel();
        avatarLabel.setPreferredSize(new Dimension(120, 120));
        avatarLabel.setHorizontalAlignment(SwingConstants.CENTER);
        avatarLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        nameLabel = new JLabel("Username:");
        emailLabel = new JLabel("Email:");

        nameField = new JTextField(20);
        emailField = new JTextField(20);

        saveButton = new JButton("Save Changes");
        changePasswordButton = new JButton("Change Password");
        changeAvatarButton = new JButton("Change Avatar");

        saveButton.addActionListener(this::saveChanges);
        changePasswordButton.addActionListener(this::changePassword);
        changeAvatarButton.addActionListener(this::changeAvatar);
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(20, 20, 20, 20));

        // Header panel with avatar
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.add(avatarLabel);
        headerPanel.add(changeAvatarButton);

        // Info panel
        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        infoPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        infoPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        infoPanel.add(emailLabel, gbc);
        gbc.gridx = 1;
        infoPanel.add(emailField, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(saveButton);
        buttonPanel.add(changePasswordButton);

        add(headerPanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupStyling() {
        // Modern color scheme
        Color primaryColor = new Color(52, 152, 219);
        Color secondaryColor = new Color(46, 204, 113);
        Color backgroundColor = new Color(248, 249, 250);
        Color textColor = new Color(44, 62, 80);

        getContentPane().setBackground(backgroundColor);

        // Apply FlatLaf rounded styling using UIUtil
        UIUtil.styleFrame(this);

        // Style labels
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        nameLabel.setFont(labelFont);
        emailLabel.setFont(labelFont);
        nameLabel.setForeground(textColor);
        emailLabel.setForeground(textColor);

        // Style text fields with UIUtil
        UIUtil.styleTextField(nameField);
        UIUtil.styleTextField(emailField);

        // Style buttons
        UIUtil.styleButton(saveButton, primaryColor);
        UIUtil.styleButton(changePasswordButton, secondaryColor);
        UIUtil.styleButton(changeAvatarButton, new Color(155, 89, 182));

        // Avatar styling
        avatarLabel.setOpaque(true);
        avatarLabel.setBackground(Color.WHITE);
        UIUtil.makeRounded(avatarLabel, UIUtil.RADIUS_LARGE);
    }


    private void loadUserData() {
        if (currentUser != null) {
            nameField.setText(currentUser.getUsername());
            emailField.setText(currentUser.getEmail());
            loadAvatar();
        }
    }

    private void loadAvatar() {
        try {
            // Try to load user-specific avatar first
            String avatarPath = "src/images/avatars/" + currentUser.getID() + ".png";
            File avatarFile = new File(avatarPath);

            if (avatarFile.exists()) {
                ImageIcon icon = new ImageIcon(avatarPath);
                Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                avatarLabel.setIcon(new ImageIcon(img));
                avatarLabel.setText("");
            } else {
                // Default avatar with initial
                avatarLabel.setIcon(null);
                avatarLabel.setText(currentUser.getUsername().substring(0, 1).toUpperCase());
                avatarLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
                avatarLabel.setForeground(new Color(52, 152, 219));
            }
        } catch (Exception e) {
            // Fallback to initial
            avatarLabel.setIcon(null);
            avatarLabel.setText(currentUser.getUsername().substring(0, 1).toUpperCase());
            avatarLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
            avatarLabel.setForeground(new Color(52, 152, 219));
        }
    }

    private void saveChanges(ActionEvent e) {
        String username = nameField.getText().trim();
        String email = emailField.getText().trim();

        if (username.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and email cannot be empty", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            currentUser.setUsername(username);
            currentUser.setEmail(email);

            boolean success = userController.updateUserProfile(currentUser);

            if (success) {
                JOptionPane.showMessageDialog(this, "Profile updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update profile", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating profile: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void changePassword(ActionEvent e) {
        JDialog passwordDialog = new JDialog(this, "Change Password", true);
        passwordDialog.setLayout(new GridBagLayout());
        passwordDialog.setSize(400, 250);
        passwordDialog.setLocationRelativeTo(this);

        // Apply FlatLaf styling to dialog using UIUtil
        UIUtil.styleDialog(passwordDialog);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JPasswordField currentPassField = new JPasswordField(20);
        JPasswordField newPassField = new JPasswordField(20);
        JPasswordField confirmPassField = new JPasswordField(20);

        JButton changeBtn = new JButton("Change Password");
        JButton cancelBtn = new JButton("Cancel");

        // Style dialog buttons with UIUtil
        UIUtil.styleButton(changeBtn, new Color(46, 204, 113));
        UIUtil.styleButton(cancelBtn, new Color(149, 165, 166));

        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        passwordDialog.add(new JLabel("Current Password:"), gbc);
        gbc.gridx = 1;
        passwordDialog.add(currentPassField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        passwordDialog.add(new JLabel("New Password:"), gbc);
        gbc.gridx = 1;
        passwordDialog.add(newPassField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        passwordDialog.add(new JLabel("Confirm Password:"), gbc);
        gbc.gridx = 1;
        passwordDialog.add(confirmPassField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(changeBtn);
        buttonPanel.add(cancelBtn);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        passwordDialog.add(buttonPanel, gbc);

        changeBtn.addActionListener(evt -> {
            String currentPass = new String(currentPassField.getPassword());
            String newPass = new String(newPassField.getPassword());
            String confirmPass = new String(confirmPassField.getPassword());

            if (currentPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
                JOptionPane.showMessageDialog(passwordDialog, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!userController.verifyPassword(currentUser.getUsername(), currentPass)) {
                JOptionPane.showMessageDialog(passwordDialog, "Current password is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!newPass.equals(confirmPass)) {
                JOptionPane.showMessageDialog(passwordDialog, "New passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                boolean success = userController.updatePassword(currentUser.getID(), newPass);
                if (success) {
                    JOptionPane.showMessageDialog(passwordDialog, "Password changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    passwordDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(passwordDialog, "Failed to change password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(passwordDialog, "Error changing password: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelBtn.addActionListener(evt -> passwordDialog.dispose());

        passwordDialog.setVisible(true);
    }

    private void changeAvatar(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                // Copy file to avatars directory
                File avatarsDir = new File("src/images/avatars");
                if (!avatarsDir.exists()) {
                    avatarsDir.mkdirs();
                }

                File destFile = new File(avatarsDir, currentUser.getID() + ".png");
                BufferedImage img = ImageIO.read(selectedFile);
                ImageIO.write(img, "png", destFile);

                loadAvatar();
                JOptionPane.showMessageDialog(this, "Avatar updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error updating avatar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}