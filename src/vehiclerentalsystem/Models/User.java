package vehiclerentalsystem.Models;
/**
 * User Entity
 * Maybe I should consider putting local date time and loginTimeStampe.
 */

public class User{
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private int roleID;
    private String roleName;
    private boolean isActive;
//    private LocalDateTime createdDate;
    
    public User() {} // Default Constructure.
    
    // Where are going to create three constructores one for registration(with password), the other for login(without password)
    // for the last one it will be for DAO to have an internall manuplation of the database.
    
    // 1. Constructor for new user registration.
    public User(String username, String email, String password, String firstName, String lastName){
        this.username = username;
        this.email = email;
        this.password = password; // password will be hashed before being passed into the constructor.
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleID = 1; // Default Employee.
        this.isActive = true; // We can do a soft delete for user(de-activate his/here account);
    }
    
    // 2. Constructor for SAFE USER (for login responses)
    public User(int id, String username, String email, String firstName, String lastName, int roleID, String roleName){
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleID = roleID;
        this.roleName = roleName;
    }
    
    // Full constructure, used internally for DAO to retrieve data from database.
    public User(int id, String username, String email, String password, String firstName,
            String lastName, int roleID, String roleName, boolean isActive){
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleID = roleID;
        this.roleName = roleName;
        this.isActive = isActive; // Soft(Delete)
    }
    
    // Creating the toSafeUser, it's a way to create a safe copy of the current user.
    public User toSafeUser() {
        User safeUser = new User();
        safeUser.setID(this.id);
        safeUser.setUsername(this.username);
        safeUser.setEmail(this.email);
        safeUser.setFirstName(this.firstName);
        safeUser.setLastName(this.lastName);
        safeUser.setRoleID(this.roleID);
        safeUser.setRoleName(this.roleName);
        safeUser.setActive(this.isActive);
        return safeUser;
    }
    
    public int getID(){
        return id;
    }
    
    public void setID(int id){
        this.id = id;
    }
    
    public String getUsername(){
        return username;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public String getFirstName(){
        return firstName;
    }
    
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    
    public String getLastName(){
        return lastName;
    }
    
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public int getRoleID(){
        return roleID;
    }
    
    public void setRoleID(int roleID){
        this.roleID = roleID;
    }
    
    public String getRoleName(){
        return roleName;
    }
    
    public void setRoleName(String roleName){
        this.roleName = roleName;
    }
    
    public boolean isActive(){
        return isActive;
    }
    
    public void setActive(boolean active){
        this.isActive = active;
    }
    
//    public LocalDateTime getCreatedDate() {
//        return createdDate;
//    }
//
//    public void setCreatedDate(LocalDateTime createdDate) {
//        this.createdDate = createdDate;
//    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    // Check if user has Admin role.
    public boolean isAdmin(){
        return roleID == 0;
    }
    
    // Check if user has Employee role.
    public boolean isEmployee(){
        return roleID == 1;
    }

    @Override
    public String toString(){
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", roleID=" + roleID +
                ", roleName='" + roleName + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}

