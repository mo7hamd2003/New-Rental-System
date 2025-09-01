// User DAO for handling all user queries.

package vehiclerentalsystem.DAO;

import vehiclerentalsystem.Models.User;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
//import java.time.LocalDateTime;

/**
 * Data Access Object for User Entity
 */

public class UserDAO {
    /**
     * Get all employees (users with roleId = 2)
     * @return List of employees
     */
    public List<User> getAllEmployees() {
        String sql = """
            SELECT u.id, u.Username, u.Email, u.FirstName, u.LastName, 
                   u.roleId, r.RoleName, u.IsActive
            FROM Users u
            INNER JOIN Role r ON u.roleId = r.id
            WHERE u.roleId = 2
        """;
        
        List<User> employees = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                User user = new User();
                user.setID(rs.getInt("id"));
                user.setUsername(rs.getString("Username"));
                user.setFirstName(rs.getString("FirstName"));
                user.setLastName(rs.getString("LastName"));
                user.setEmail(rs.getString("Email"));
                user.setRoleID(rs.getInt("roleId"));
                user.setRoleName(rs.getString("RoleName"));
                user.setActive(rs.getBoolean("IsActive"));
                employees.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Error getting employees: " + e.getMessage());
        }
        
        return employees;
    }
    
    /**
     * Update employee active status (soft delete)
     * @param userId ID of the user to update
     * @param isActive new active status
     * @return true if successful, false otherwise
     */
    public boolean updateEmployeeStatus(int userId, boolean isActive) {
        String sql = "UPDATE Users SET IsActive = ? WHERE id = ? AND roleId = 2";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setBoolean(1, isActive);
            pstmt.setInt(2, userId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating employee status: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Hard delete an employee from the database
     * @param userId ID of the user to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteEmployee(int userId) {
        String sql = "DELETE FROM Users WHERE id = ? AND roleId = 2";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting employee: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Update employee role
     * @param userId ID of the user to update
     * @param roleId new role ID
     * @return true if successful, false otherwise
     */
    public boolean updateEmployeeRole(int userId, int roleId) {
        String sql = "UPDATE Users SET roleId = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, roleId);
            pstmt.setInt(2, userId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating employee role: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Toggle user's active status (soft delete)
     * @param userId ID of the user to toggle status for
     * @return true if successful, false otherwise
     */
    public boolean toggleUserStatus(int userId) {
        // We can't use NOT the sql server will complain about the mismatch data types.
        // This is the safest way to do it.
        String sql = "UPDATE Users SET IsActive = CASE WHEN IsActive = 1 THEN 0 ELSE 1 END WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error toggling user status: " + e.getMessage());
            return false;
        }
    }
    
    // Create a new User in the database, using INSERT, 
    // we will call the function registerUser in services in the Register button.
    
    public boolean createUser(User user){
        String sql = "INSERT INTO Users (Username, Email, Password, FirstName, LastName, roleId, isActive) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getFirstName());
            pstmt.setString(5, user.getLastName());
            pstmt.setInt(6, user.getRoleID());
            pstmt.setBoolean(7, user.isActive());
            
            int rowsAffected = pstmt.executeUpdate();
            if(rowsAffected > 0){
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if(generatedKeys.next()){
                    user.setID(generatedKeys.getInt(1));
                }
                return true;
            }
        }catch(SQLException e){
            System.err.println("Error creating user: " + e.getMessage());
        }
        return false;
    }
    
    /**
    * Check if username exists
    * @param  username
    * @return
    */
     public boolean usernameExists(String username){
        String sql = "SELECT 1 FROM Users WHERE Username = ?";
         
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
             
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
             
        }catch(SQLException e){
            System.err.println("Error Checking Username existence: " + e.getMessage());
            return true;
         }
     }
     
     public boolean emailExists(String email){
        String sql = "SELECT 1 FROM Users WHERE Email = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
            
        }catch(SQLException e){
            System.err.println("Error Checking Email existence: " + e.getMessage());
            return true;
        }
     }
     
     // Finding User by name and retrieve info about him/her using roleID.
    public User findUserByUsername(String username){
         String sql = """
            SELECT u.id, u.Username, u.Email, u.Password, u.FirstName, u.LastName, 
                   u.roleId, r.RoleName, u.IsActive
            FROM Users u
            INNER JOIN Role r ON u.roleId = r.id
            WHERE u.Username = ? AND u.IsActive = 1
        """;
         
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                User user = new User();
                user.setID(rs.getInt("id"));
                user.setUsername(rs.getString("Username"));
                user.setPassword(rs.getString("Password"));
                user.setFirstName(rs.getString("FirstName"));
                user.setLastName(rs.getString("LastName"));
                user.setEmail(rs.getString("Email"));
                user.setRoleID(rs.getInt("RoleID"));
                user.setRoleName(rs.getString("RoleName"));
                user.setActive(rs.getBoolean("IsActive"));
                return user;
            }
        } catch (SQLException e){
            System.err.println("Error finding user: " + e.getMessage());
        }
        
        return null;
    }
}
