package vehiclerentalsystem.Controllers;

import vehiclerentalsystem.DAO.UserDAO;
import vehiclerentalsystem.Models.User;
import java.util.List;

public class UserController {
    private UserDAO userDAO;

    public UserController() {
        userDAO = new UserDAO();
    }

    public List<User> getAllEmployees() {
        return userDAO.getAllEmployees();
    }

    public boolean updateUserRole(int userId, int newRoleId) {
        return userDAO.updateEmployeeRole(userId, newRoleId);
    }

    public boolean toggleUserStatus(int userId) {
        return userDAO.toggleUserStatus(userId);
    }

    public boolean deleteEmployee(int userId) {
        return userDAO.deleteEmployee(userId);
    }
    
    public boolean updateUserProfile(User user) {
        return userDAO.AdminProfile(user);
    }
    
    public boolean verifyPassword(String username, String password) {
        return userDAO.verifyPassword(username, password);
    }
    
    public boolean updatePassword(int userId, String newPassword) {
        return userDAO.updatePassword(userId, newPassword);
    }

}
