package com.abc.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abc.entities.Post;

@Repository
@Transactional
public class PostDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public List<Post> getAllPost(int id) {
        String hql = "from Post";
        Query<Post> query = getCurrentSession().createQuery(hql, Post.class);
        return query.getResultList();
    }

    public List<Post> getPostById(int id) {
        // Sửa câu lệnh HQL để tham chiếu đúng trường 'userId' và 'created_At'
        String hql = "FROM Post p WHERE p.userId = :id ORDER BY p.created_At DESC";

        // Tạo truy vấn với HQL và ánh xạ vào lớp Post
        Query<Post> query = getCurrentSession().createQuery(hql, Post.class);
        
        // Thiết lập tham số id trong truy vấn
        query.setParameter("id", id);
        
        // Trả về danh sách kết quả
        return query.getResultList();
    }


    public boolean createdPost(Post post) {
        try {
            getCurrentSession().persist(post);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}