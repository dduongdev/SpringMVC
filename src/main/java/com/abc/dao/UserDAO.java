package com.abc.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.abc.entities.ExtendedUser;
import com.abc.entities.User;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public class UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    // Helper method to get the current session
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    // Method to get a user by username
    public User getUserByUserName(String userName) {
        String hql = "FROM User u WHERE u.username = :username";
        
        try {
            Query<User> query = getCurrentSession().createQuery(hql, User.class);
            query.setParameter("username", userName);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to register a new user
    public boolean registerUser(User user) {
        try {
            getCurrentSession().save(user); // Hibernate automatically manages the save
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to search users by username
    public List<User> searchOnUsername(String searchKeyword) {
        List<User> searchResult = new ArrayList<>();
        String hql = "FROM User u WHERE u.username LIKE :searchKeyword";

        try {
            Query<User> query = getCurrentSession().createQuery(hql, User.class);
            query.setParameter("searchKeyword", "%" + searchKeyword + "%");
            searchResult = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return searchResult;
    }

    // Method to update an existing user's details (extended user data)
    public boolean update(ExtendedUser user) {
        try {
            getCurrentSession().update(user); // Hibernate update operation
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to check if email exists in the users table
    public boolean isEmailExists(String email) {
        String hql = "SELECT COUNT(u) FROM User u WHERE u.email = :email";
        try {
            Query<Long> query = getCurrentSession().createQuery(hql, Long.class);
            query.setParameter("email", email);
            long count = query.uniqueResult();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
