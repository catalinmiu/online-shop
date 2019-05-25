package com.example.onlineShop.respository;


import com.example.onlineShop.domain.Cart;
import com.example.onlineShop.domain.CartProduct;
import com.example.onlineShop.domain.Product;
import com.example.onlineShop.domain.dto.CartDto;
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
        String query = "SELECT id, product_id, cart_id, units FROM Cart_Products";
        RowMapper<CartProduct> rowMapper = new BeanPropertyRowMapper<>(CartProduct.class);
        return jdbcTemplate.query(query, rowMapper);
    }

    public int create(CartProduct cart_product){
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("Cart_Products").usingColumns("product_id", "units", "cart_id").usingGeneratedKeyColumns("id");
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("product_id", cart_product.getProduct_id());
        sqlParameterSource.addValue("units", cart_product.getUnits());
        sqlParameterSource.addValue("cart_id", cart_product.getCart_id());
        return simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).intValue();
    }

    public CartProduct findByProductIdAndCartId(int id, int cart_id) throws Exception {
        String query = "SELECT * FROM Cart_Products WHERE product_id = " + id + " AND cart_id = " + cart_id;
        RowMapper<CartProduct> rowMapper = new BeanPropertyRowMapper<>(CartProduct.class);
        CartProduct cartProduct;
        cartProduct = jdbcTemplate.queryForObject(query, rowMapper);
        return cartProduct;
    }

    public void setUnitsByCartProductId(CartProduct cartProduct) {
        String query = "UPDATE Cart_Products SET units=? WHERE id=?";
        jdbcTemplate.update(query, cartProduct.getUnits(), cartProduct.getId());
    }

    public List<CartDto> findAllProductsByCartId(int id) throws Exception {
        String query = "SELECT * FROM Cart_Products c JOIN Products p ON c.product_id = p.id WHERE cart_id = " + id;
        RowMapper<CartDto> rowMapper = new BeanPropertyRowMapper<>(CartDto.class);
        return jdbcTemplate.query(query, rowMapper);
    }

    public List<CartProduct> findAllCartProductByCartId(int id) throws Exception {
        String query = "SELECT * FROM Cart_Products c WHERE cart_id = " + id;
        RowMapper<CartProduct> rowMapper = new BeanPropertyRowMapper<>(CartProduct.class);
        return jdbcTemplate.query(query, rowMapper);
    }
    public void deleteById(int id, int units) {
        String query = "DELETE FROM Cart_Products WHERE cart_id = " + id + " AND units = " + units;
        jdbcTemplate.update(query);
    }

}
