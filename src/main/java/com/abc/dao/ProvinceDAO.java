package com.abc.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.abc.entities.Province;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public class ProvinceDAO {

    @Autowired
    private SessionFactory sessionFactory;

    // Helper method to get the current session
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    // Method to get all provinces
    public List<Province> getAll() {
        List<Province> provinces = new ArrayList<>();
        String hql = "FROM Province"; // HQL query to get all provinces

        try {
            Query<Province> query = getCurrentSession().createQuery(hql, Province.class);
            provinces = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return provinces;
    }
}
