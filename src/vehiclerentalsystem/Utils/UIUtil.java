package vehiclerentalsystem.Utils;

import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.FlatClientProperties;

/**
 * UIUtil - Utility class for consistent FlatLaf styling across the application
 *
 * Usage Examples:
 *
 * // Style a panel with rounded corners
 * JPanel panel = new JPanel();
 * UIUtil.stylePanel(panel);
 *
 * // Style a button with custom color
 * JButton button = new JButton("Click me");
 * UIUtil.styleButton(button, Color.BLUE);
 *
 * // Style text fields
 * JTextField textField = new JTextField();
 * UIUtil.styleTextField(textField);
 *
 * // Style a frame
 * UIUtil.styleFrame(myFrame);
 *
 * // Apply custom rounded corners
 * UIUtil.makeRounded(component, UIUtil.RADIUS_LARGE);
 */

public class UIUtil {

    // Standard radius values for consistency
    public static final int RADIUS_SMALL = 8;
    public static final int RADIUS_MEDIUM = 12;
    public static final int RADIUS_LARGE = 16;
    public static final int RADIUS_XLARGE = 20;

    /**
     * Makes a Swing component rounded using FlatLaf properties.
     *
     * @param comp    The component to round (JPanel, JButton, etc.)
     * @param radius  The corner radius in pixels
     */
    public static void makeRounded(JComponent comp, int radius) {
        comp.putClientProperty(FlatClientProperties.STYLE, "arc: " + radius);
        comp.setOpaque(true);
        comp.repaint();
    }

    /**
     * Applies standard rounded styling to a panel.
     *
     * @param panel The JPanel to style
     */
    public static void stylePanel(JPanel panel) {
        panel.putClientProperty(FlatClientProperties.STYLE, "arc: " + RADIUS_LARGE);
        panel.setOpaque(true);
    }

    /**
     * Applies standard rounded styling to a button.
     *
     * @param button The JButton to style
     * @param backgroundColor The background color
     */
    public static void styleButton(JButton button, Color backgroundColor) {
        button.putClientProperty(FlatClientProperties.STYLE, "arc: " + RADIUS_MEDIUM);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(140, 35));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.darker());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
            }
        });
    }

    /**
     * Applies standard rounded styling to a text field.
     *
     * @param textField The JTextField to style
     */
    public static void styleTextField(JTextField textField) {
        textField.putClientProperty(FlatClientProperties.STYLE,
            "arc: " + RADIUS_MEDIUM + "; borderWidth: 1; focusWidth: 2");
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }

    /**
     * Applies standard rounded styling to a password field.
     *
     * @param passwordField The JPasswordField to style
     */
    public static void stylePasswordField(JPasswordField passwordField) {
        passwordField.putClientProperty(FlatClientProperties.STYLE,
            "arc: " + RADIUS_MEDIUM + "; borderWidth: 1; focusWidth: 2");
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }

    /**
     * Applies standard rounded styling to a dialog.
     *
     * @param dialog The JDialog to style
     */
    public static void styleDialog(JDialog dialog) {
        ((JComponent) dialog.getContentPane()).putClientProperty(FlatClientProperties.STYLE,
            "arc: " + RADIUS_LARGE);
        dialog.getContentPane().setBackground(new Color(248, 249, 250));
    }

    /**
     * Applies standard rounded styling to a frame.
     *
     * @param frame The JFrame to style
     */
    public static void styleFrame(JFrame frame) {
        ((JComponent) frame.getContentPane()).putClientProperty(FlatClientProperties.STYLE,
            "arc: " + RADIUS_XLARGE + "; background: #f8f9fa");
    }

    /**
     * Creates a standard styled label.
     *
     * @param text The label text
     * @return Styled JLabel
     */
    public static JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(44, 62, 80));
        return label;
    }

    /**
     * Creates a standard styled panel with padding.
     *
     * @return Styled JPanel with padding
     */
    public static JPanel createStyledPanel() {
        JPanel panel = new JPanel();
        stylePanel(panel);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return panel;
    }

    /**
     * Applies hover effect to any component.
     *
     * @param component The component to add hover effect to
     * @param normalColor The normal background color
     * @param hoverColor The hover background color
     */
    public static void addHoverEffect(JComponent component, Color normalColor, Color hoverColor) {
        component.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                component.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                component.setBackground(normalColor);
            }
        });
    }
}
