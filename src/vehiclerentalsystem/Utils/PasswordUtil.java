package vehiclerentalsystem.Utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    
    // Number of salt rounds for BCrypt (higher = Stronger but slower).
    private static final int SALT_ROUNDS = 12;
    
    /**
    *@param plainPassword the plain text password to hash
    *@return the hashed password
    */
    public static String hashPassword(String plainPassword){
        if(plainPassword == null || plainPassword.trim().isEmpty()){
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(SALT_ROUNDS));
    }
    
    /**
    * Verify a plain text password against a hashed password
    * @param plainPassword The plain text password to verify
    * @param hashedPassword The hashed password to verify against
    * @return true if the password matches, false otherwise
    */
    
    public static boolean verifyPassword(String plainPassword, String hashedPassword){
        if(plainPassword == null || hashedPassword == null){
            return false;
        }
        try{
            return BCrypt.checkpw(plainPassword, hashedPassword);
        }catch(Exception e){
            System.err.println("Error verifying passowrd: " + e.getMessage());
            return false;
        }
    }
    
    /**
    * Validate password strength
    * @param password The password to validate
    * @return true if password meets criteria, false otherwise
    */
    
    public static boolean isValidPassword(String password){
        if (password == null) return false;
        
        return password.length() >= 8 &&
               password.matches(".*[A-Z].*"); 
//               password.matches(".*[a-z].*") &&
//               password.matches(".*\\\\d.*") &&
//               password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
    }
    
    /**
     * Get password strength requirements message
     *
     * @return String describing password requirements
     */
    
    public static String getPasswordRequirements() {
        return """
               Password must be at least 8 characters long and contain:
               - At least one uppercase letter
               - At least one lowercase letter
               - At least one digit
               - At least one special character (!@#$%^&*()_+-=[]{}|;':"\\,./<>?)""";
    }
}
