package com.example.onlineShop.respository;


import com.example.onlineShop.domain.Cart;
import com.example.onlineShop.domain.CartProduct;
import com.example.onlineShop.domain.User;
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
        String query = "SELECT id, user_id, paid_date FROM carts";
        RowMapper<Cart> rowMapper = new BeanPropertyRowMapper<>(Cart.class);
        return jdbcTemplate.query(query, rowMapper);
    }

    public int create(Cart cart){
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("Carts").usingColumns("user_id", "paid_date").usingGeneratedKeyColumns("id");

        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("user_id", cart.getUser_id());
        sqlParameterSource.addValue("paid_date", cart.getPaidDate());
        return simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).intValue();
    }

    public Cart findByUserId(int id) throws Exception {
        String query = "SELECT * FROM Carts WHERE user_id = " + id;
        RowMapper<Cart> rowMapper = new BeanPropertyRowMapper<>(Cart.class);
        Cart cart;
        cart = jdbcTemplate.queryForObject(query, rowMapper);
        return cart;
    }

    public Cart findCurrentCartByUserId(int id) throws Exception {
        String query = "SELECT * FROM Carts WHERE user_id = " + id + " AND paid_date is NULL";
        RowMapper<Cart> rowMapper = new BeanPropertyRowMapper<>(Cart.class);
        Cart cart;
        cart = jdbcTemplate.queryForObject(query, rowMapper);
        return cart;
    }

    public void updateCart(Cart cart) {
        String query = "UPDATE Carts SET paid_date=? WHERE id=?";
        jdbcTemplate.update(query, cart.getPaidDate(), cart.getId());
    }
}
