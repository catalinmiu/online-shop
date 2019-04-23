package com.example.onlineShop.respository;


import com.example.onlineShop.domain.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartsDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Cart> findAll(){
        String query = "SELECT id, user_id FROM carts";
        RowMapper<Cart> rowMapper = new BeanPropertyRowMapper<>(Cart.class);
        return jdbcTemplate.query(query, rowMapper);
    }

    public int create(Cart cart){
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("Carts").usingColumns("user_id").usingGeneratedKeyColumns("id");

        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("user_id", cart.getUser_id());
        return simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).intValue();
    }
}
