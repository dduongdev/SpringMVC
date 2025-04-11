package com.abc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.abc.config.DatabaseConfig;
import com.abc.entities.Province;

@Repository
public class ProvinceDAO {
	public List<Province> getAll() {
        List<Province> provinces = new ArrayList<>();
        String sql = "SELECT id, name FROM provinces";

        try (Connection conn = DatabaseConfig.getConnection();
        		PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");

                Province province = new Province(id, name);
                provinces.add(province);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return provinces;
    }
}
