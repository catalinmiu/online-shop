package com.example.onlineShop.respository;


import com.example.onlineShop.domain.CartProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartProductsDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<CartProduct> findAll(){
        String query = "SELECT id, product_id, units FROM Cart_Products";
        RowMapper<CartProduct> rowMapper = new BeanPropertyRowMapper<>(CartProduct.class);
        return jdbcTemplate.query(query, rowMapper);
    }

    public int create(CartProduct cart_product){
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("Cart_Products").usingColumns("product_id", "units").usingGeneratedKeyColumns("id");
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("product_id", cart_product.getProduct_id());
        sqlParameterSource.addValue("units", cart_product.getUnits());
        return simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).intValue();
    }
}
