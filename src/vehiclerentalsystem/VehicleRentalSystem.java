package vehiclerentalsystem;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;
import vehiclerentalsystem.GUI.LoginForm;
import vehiclerentalsystem.DAO.DBConnection;

public class VehicleRentalSystem {

    public static void main(String[] args) {
        if(DBConnection.testConnection()){
            System.out.println("Database Connection Successfull");
        }else{
            System.out.println("Database Connection Failed");
        }
        
        
        try {
            // Choose theme
            FlatLightLaf.setup();  // light theme
            // FlatDarkLaf.setup(); // dark theme
            
            UIManager.put("Button.arc", 20);
            UIManager.put("Component.arc", 20);
            UIManager.put("ProgressBar.arc", 20);
            UIManager.put("TextComponent.arc", 20);
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF: " + ex.getMessage());
        }


        LoginForm LoginFrame = new LoginForm();
        LoginFrame.setVisible(true);
        LoginFrame.pack();
        LoginFrame.setLocationRelativeTo(null);
    }
    
}
