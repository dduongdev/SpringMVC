package com.abc.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.abc.entities.Follow;
import com.abc.entities.User;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public class FollowDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    // Lấy danh sách những người theo dõi người dùng
    public List<User> getFollowerUser(int id) {
        String hql = "SELECT u FROM User u JOIN Follow f ON u.id = f.followingUserId WHERE f.followedUserId = :id";
        Query<User> query = getCurrentSession().createQuery(hql, User.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    // Lấy danh sách những người được người dùng theo dõi
    public List<User> getFollowedUsers(int id) {
        String hql = "SELECT u FROM User u JOIN Follow f ON u.id = f.followedUserId WHERE f.followingUserId = :id";
        Query<User> query = getCurrentSession().createQuery(hql, User.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    // Follow một người dùng
    public void followUser(int followingUserId, int followedUserId) {
        Follow follow = new Follow(followingUserId, followedUserId, LocalDateTime.now());
        getCurrentSession().persist(follow);
    }

    // Unfollow một người dùng
    public void unfollowUser(int followingUserId, int followedUserId) {
        String hql = "DELETE FROM Follow f WHERE f.followingUserId = :followingUserId AND f.followedUserId = :followedUserId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("followingUserId", followingUserId);
        query.setParameter("followedUserId", followedUserId);
        query.executeUpdate();
    }

    // Lấy danh sách gợi ý người dùng để follow (người dùng chưa follow và không phải chính người dùng)
    public List<User> getSuggestedFollows(int userId) {
        String hql = "SELECT u FROM User u LEFT JOIN Follow f ON u.id = f.followedUserId AND f.followingUserId = :userId "
                   + "WHERE f.followedUserId IS NULL AND u.id <> :userId";
        Query<User> query = getCurrentSession().createQuery(hql, User.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
