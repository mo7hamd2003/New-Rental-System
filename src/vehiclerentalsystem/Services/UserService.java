package vehiclerentalsystem.Services;

import vehiclerentalsystem.Models.User;
import vehiclerentalsystem.DAO.UserDAO;
import vehiclerentalsystem.Utils.PasswordUtil;

/**
* Services containing the Business logic for user registration 
*/

public class UserService {
    
    private final UserDAO userDAO;
    
    public UserService() {
        this.userDAO = new UserDAO();
    }
    
    /**
   * Register a new user
   * @param username The desired username
   * @param password The plain text password
   * @param firstName The user's first name
   * @param lastName The user's last name
   * @param email The user's email address
   * @return RegistrationResult object containing success status and message
   */
    
    public RegistrationResult registerUser(String username, String email, String password, String firstName, String lastName){
        
        String validationError = validateRegistrationInput(username, email, password, firstName, lastName);
        
        // Validate input
        if (validationError != null){
            return new RegistrationResult(false, validationError);
        }
        
        // Check if username already exists
        if (userDAO.usernameExists(username)){
            return new RegistrationResult(false, "Username already exists. Please choose a different username.");
        }
        
        // Check if email already exists
         if (userDAO.emailExists(email)) {
            return new RegistrationResult(false, "Email already registered. Please use a different email address.");
        }
         
        // Hash the password
        String hashedPassword; 
        try{
            hashedPassword = PasswordUtil.hashPassword(password);
        }catch(Exception e){
            return new RegistrationResult(false, "Error processing password. Please try again.");
        }
        
        User user = new User(username, email, hashedPassword, firstName, lastName);
        
        // Save user to database
        boolean Success = userDAO.createUser(user);
        
        if (Success){
            return new RegistrationResult(true, "User registered successfully!");
        }else {
            return new RegistrationResult(false, "Registration failed. Please try again.");
        }
    }
    
    /**
     * Authenticate a user
     * @param username The username
     * @param password The plain text password
     * @return LoginResult object containing authentication status and user info
    */
    public LoginResult authenticateUser(String username, String password) {
        
        // Validate input
        if (username == null || username.trim().isEmpty()) {
            return new LoginResult(false, "Username is required.", null);
        }
        
        if (password == null || password.trim().isEmpty()) {
            return new LoginResult(false, "Password is required.", null);
        }
        
        // Find user in database
        User userDB = userDAO.findUserByUsername(username.trim());
        
        if (userDB == null) {
            return new LoginResult(false, "Invalid username or password.", null);
        }
        
        // Verify password
        boolean passwordMatch = PasswordUtil.verifyPassword(password, userDB.getPassword());
        
        if (passwordMatch) {
            User safeUser = userDB.toSafeUser();
            return new LoginResult(true, "Login successful!", safeUser);
        } else {
            return new LoginResult(false, "Invalid username or password.", null);
        }
    }
    
    /**
    * Validates registration input 
    */
    private String validateRegistrationInput(String username, String email, String password, String firstName, String lastName){
        
        if (username == null || username.trim().isEmpty()){
            return "Username is required";
        }
        
        if(email == null || email.trim().isEmpty()){
            return "Email is required";
        }
        
        if(password == null || password.trim().isEmpty()){
            return "Password is required";
        }
        
        if(firstName == null || firstName.trim().isEmpty()){
            return "First Name is required";
        }
        
        if(lastName == null || lastName.trim().isEmpty()){
            return "Last Name is required";
        }
        
        if (!isValidUsername(username)){
            return "Username must be 3-20 characters long and contain only letters, numbers, and underscores.";
        }
        
        if (!PasswordUtil.isValidPassword(password)){
            return PasswordUtil.getPasswordRequirements();
        }
        
        if(!isValidEmail(email)){
            return "Please enter a valid email address.";
        }
        
        return null;
    }
    
    
    
    private boolean isValidUsername(String username){
        return username != null &&
               username.length() >= 3 &&
               username.length() <= 20 &&
               username.matches("^[a-zA-Z0-9_-]+$"); // a-z A-Z 0-9 _-.
    }
    
    private boolean isValidEmail(String email){
         return email != null && 
               email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }
    
    /**
    * Inner class for registration results
    */
    public static class RegistrationResult {
        private final boolean success;
        private final String message;
        
        public RegistrationResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
        
        public boolean isSuccess() {
            return success;
        }
        
        public String getMessage() {
            return message;
        }
    }
    
    /**
    * Inner class for login results
    */
    public static class LoginResult {
        private final boolean success;
        private final String message;
        private final User user;
        
        public LoginResult(boolean success, String message, User user) {
            this.success = success;
            this.message = message;
            this.user = user;
        }
        
        public boolean isSuccess() {
            return success;
        }
        
        public String getMessage() {
            return message;
        }
        
        public User getUser() {
            return user;
        }
    }
}