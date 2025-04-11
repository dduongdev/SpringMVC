package com.abc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.abc.config.DatabaseConfig;
import com.abc.entities.ExtendedUser;
import com.abc.entities.User;

@Repository
public class UserDAO {


    public User getUserByUserName(String userName) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("created_at")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public boolean registerUser(User user) {
        String sql = "INSERT INTO users (username, password, created_at) VALUES (?, ?, NOW())";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassWord());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public List<User> searchOnUsername(String searchKeyword) {
    	List<User> searchResult = new ArrayList<User>();
    	String sql = "SELECT * FROM users WHERE username LIKE ?";

    	try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
    		stmt.setString(1, "%" + searchKeyword + "%");

    			ResultSet rs = stmt.executeQuery();
                
                while (rs.next()) {
                	searchResult.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("created_at")));
                }
		} catch (SQLException e) {
            e.printStackTrace();
        }
    	
    	return searchResult;
	}
    
    public boolean update(ExtendedUser user) {
        String sql = "UPDATE users SET " +
                     "email = ?, birth_date = ?, " +
                     "province_id = ?, avatar_file_name = ? " +
                     "WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getEmail());
            stmt.setDate(2, user.getBirthDate() != null ? Date.valueOf(user.getBirthDate()) : null);
            stmt.setInt(3, user.getProvinceId());
            stmt.setString(4, user.getAvatarFileName());
            stmt.setInt(5, user.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace(); 
            return false;
        }
    }

    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace(); 
        }

        return false;
    }

}
