package vehiclerentalsystem.GUI;

import vehiclerentalsystem.Models.User;
import vehiclerentalsystem.Controllers.UserController;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.EmptyBorder;

public class AdminProfile extends javax.swing.JFrame {
    private User currentUser;
    private final UserController userController;

    public AdminProfile(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        
        this.currentUser = user;
        this.userController = new UserController();
        
        // Initialize components first
        initComponents();
        setupUI();
        loadUserData();
    }
    
    private void setupUI() {
        getContentPane().setBackground(Color.BLACK);
        
        // Setup circular avatar
        setupCircularAvatar();
        
        // jPanel1.setOpaque(false);
        // jPanel1.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Style labels
        jLabel2.setForeground(Color.WHITE);
        jLabel3.setForeground(Color.WHITE);
        jLabel4.setForeground(Color.WHITE);
        jLabel5.setForeground(Color.WHITE);

        // Style buttons
        styleButton(jButton2, new Color(0, 180, 0), new Color(0, 150, 0)); // Green for Save Changes
        styleButton(jButton1, new Color(0, 120, 215), new Color(0, 100, 190)); // Blue for Change Password
        
        // Style text fields
        styleTextField(jTextField1);
        styleTextField(jTextField2);

        styleButton(jButton2, new Color(0, 180, 0), new Color(0, 150, 0));
        

        styleButton(jButton1, new Color(0, 120, 215), new Color(0, 100, 190));
        
        // Style text fields
        // styleTextField(jTextField1);
        // styleTextField(jTextField2);
        
        // Set window properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Admin Profile");
        
        ((JPanel)getContentPane()).setBorder(new EmptyBorder(20, 20, 20, 20));
    }
    
    private void styleButton(JButton button, Color baseColor, Color hoverColor) {
        button.setBackground(baseColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor);
            }
        });
}
    
    private void styleTextField(JTextField textField) {
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), 
            BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBackground(new Color(250, 250, 250));
        textField.setForeground(Color.BLACK);
        textField.setCaretColor(Color.BLACK);
        
        // Add focus effect and placeholder behavior
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(52, 152, 219)), 
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)));
                
                if (textField.getText().equals("Enter your name") || 
                    textField.getText().equals("Enter your email")) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)), 
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)));
                
                if (textField.getText().isEmpty()) {
                    if (textField == jTextField1) {
                        textField.setText("Enter your name");
                    } else if (textField == jTextField2) {
                        textField.setText("Enter your email");
                    }
                    textField.setForeground(Color.GRAY);
                }
            }
        });
    }
    
    private void setupCircularAvatar() {
        // Create a circular panel for the avatar
        JPanel avatarPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int diameter = Math.min(getWidth(), getHeight());
                int x = (getWidth() - diameter) / 2;
                int y = (getHeight() - diameter) / 2;
                
                // Draw circle background
                g2.setColor(new Color(52, 152, 219));
                g2.fillOval(x, y, diameter, diameter);
                
                // Draw user's initial
                if (currentUser != null && currentUser.getFirstName() != null) {
                    String initial = currentUser.getFirstName().substring(0, 1).toUpperCase();
                    g2.setColor(Color.WHITE);
                    g2.setFont(new Font("Segoe UI", Font.BOLD, 36));
                    
                    FontMetrics fm = g2.getFontMetrics();
                    int textX = x + (diameter - fm.stringWidth(initial)) / 2;
                    int textY = y + ((diameter - fm.getHeight()) / 2) + fm.getAscent();
                    
                    g2.drawString(initial, textX, textY);
                }
                
                g2.dispose();
            }
            
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(80, 80);
            }
        };
        
        avatarPanel.setOpaque(false);
        jLabel1.setText("");
        jLabel1.setLayout(new BorderLayout());
        jLabel1.add(avatarPanel, BorderLayout.CENTER);
    }
    
    private void loadUserData() {
        if (currentUser != null) {
            String firstName = currentUser.getFirstName();
            String lastName = currentUser.getLastName();
            String fullName = (firstName != null ? firstName : "") + 
                            ((lastName != null && !lastName.isEmpty()) ? " " + lastName : "");
            String email = currentUser.getEmail();
            
            // Set default values if data is missing
            if (fullName.trim().isEmpty()) {
                fullName = "Admin User";
            }
            if (email == null || email.trim().isEmpty()) {
                email = "No email set";
            }
            
            // Update all text components with user data
            jLabel2.setText(fullName);
            jLabel3.setText(email);
            // jTextField1.setText(fullName);
            // jTextField2.setText(email);
            
            // Update window title with user name
            setTitle("Admin Profile - " + fullName);
            
            // Debug output
            System.out.println("Loading user data:");
            System.out.println("First Name: " + firstName);
            System.out.println("Last Name: " + lastName);
            System.out.println("Full Name: " + fullName);
            System.out.println("Email: " + email);
        } else {
            System.err.println("Current user is null in loadUserData()");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")                    
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setText("");  
        jLabel3.setText("");  

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3))
        );

        jButton1.setText("Change Password");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        // Set and resize the image
        try {
            String imagePath = System.getProperty("user.dir") + "/src/images/resources/admin_avatar.png";
            ImageIcon originalIcon = new javax.swing.ImageIcon(imagePath);
            if (originalIcon.getIconWidth() == -1) {
                // Fallback image if admin_avatar.png is not found
                originalIcon = new javax.swing.ImageIcon(getClass().getResource("/images/resources/default_avatar.png"));
            }
            Image image = originalIcon.getImage();
            Image newimg = image.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
            jLabel1.setIcon(new ImageIcon(newimg));
        } catch (Exception e) {
            System.err.println("Error loading profile image: " + e.getMessage());
            // Set a colored circle as fallback
            jLabel1.setPreferredSize(new Dimension(100, 100));
            jLabel1.setOpaque(true);
            jLabel1.setBackground(new Color(52, 152, 219));
            jLabel1.setText(currentUser != null ? currentUser.getFirstName().substring(0, 1).toUpperCase() : "A");
            jLabel1.setForeground(Color.WHITE);
            jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 36));
            jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        }

        jButton2.setText("Save Changes");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel4.setText("Name ");

        jTextField1.setText("");  // Will be set in loadUserData
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel5.setText("Email");

        jTextField2.setText("");  // Will be set in loadUserData
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(181, 181, 181)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(138, 138, 138))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        setPreferredSize(new Dimension(600, 500)); // choose width & height
        setResizable(true); // optional: prevent resizing
        pack();
        setLocationRelativeTo(null); // center the window
    }                   

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {                                            
    }                                           

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // Save Changes Button
        String newName = jTextField1.getText().trim();
        String newEmail = jTextField2.getText().trim();
        
        // Validate inputs
        if (newName.isEmpty() || newEmail.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Name and email cannot be empty",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!isValidEmail(newEmail)) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid email address",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // Update the current user object
            currentUser.setEmail(newEmail);
            String[] nameParts = newName.split(" ", 2);
            currentUser.setFirstName(nameParts[0]);
            currentUser.setLastName(nameParts.length > 1 ? nameParts[1] : "");
            
            // Update in database
            boolean success = userController.updateUserProfile(currentUser);
            
            if (success) {
                JOptionPane.showMessageDialog(this,
                    "Profile updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                loadUserData(); // Reload the displayed data
            } else {
                JOptionPane.showMessageDialog(this,
                    "Failed to update profile. Please try again.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error updating profile: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }                                        

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // Change Password Button
        String currentPassword = JOptionPane.showInputDialog(this,
            "Enter your current password:",
            "Change Password",
            JOptionPane.PLAIN_MESSAGE);
            
        if (currentPassword == null) return; // User cancelled
        
        // Verify current password
        if (!userController.verifyPassword(currentUser.getUsername(), currentPassword)) {
            JOptionPane.showMessageDialog(this,
                "Current password is incorrect",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Get new password
        JPasswordField newPass = new JPasswordField();
        JPasswordField confirmPass = new JPasswordField();
        Object[] message = {
            "New Password:", newPass,
            "Confirm Password:", confirmPass
        };
        
        int option = JOptionPane.showConfirmDialog(this,
            message,
            "Change Password",
            JOptionPane.OK_CANCEL_OPTION);
            
        if (option == JOptionPane.OK_OPTION) {
            String newPassword = new String(newPass.getPassword());
            String confirmPassword = new String(confirmPass.getPassword());
            
            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this,
                    "Passwords do not match",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                boolean success = userController.updatePassword(currentUser.getID(), newPassword);
                if (success) {
                    JOptionPane.showMessageDialog(this,
                        "Password changed successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Failed to change password. Please try again.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error changing password: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }                                        

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {                                            
    }                                           
    
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

              
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;                  
}