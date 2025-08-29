package vehiclerentalsystem.Utils;
import javax.swing.*;
public class UIUtil {
       /**
     * Makes a Swing component rounded using FlatLaf properties.
     * 
     * @param comp    The component to round (JPanel, JButton, etc.)
     * @param radius  The corner radius in pixels
     */
    public static void makeRounded(JComponent comp, int radius) {
        // Set the corner radius using UIManager property
        comp.putClientProperty("JComponent.cornerRadius", radius);
        
        // Optional: enable background color for opaque components
        // Ensure background is painted for JPanel/JButton
        comp.setOpaque(true);

        // Repaint to force the look update
        comp.repaint();
    }
}
