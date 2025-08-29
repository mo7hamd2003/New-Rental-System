// User DAO for handling all user queries.

package vehiclerentalsystem.DAO;

import vehiclerentalsystem.Models.User;
import java.sql.*;
//import java.time.LocalDateTime;

/**
 * Data Access Object for User Entity
 */

public class UserDAO {
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
