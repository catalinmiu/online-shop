package com.example.onlineShop.respository;

import com.example.onlineShop.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductsDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Product> findAll() {
        String query = "SELECT id, title, price, description, stock, created_date, category_id FROM Products";
        RowMapper<Product> rowMapper = new BeanPropertyRowMapper<>(Product.class);
        return jdbcTemplate.query(query, rowMapper);
    }

    public int create(Product product) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("Products").usingColumns("title", "price", "description", "stock",
                "created_date", "category_id").usingGeneratedKeyColumns("id");
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("title", product.getTitle());
        sqlParameterSource.addValue("price", product.getPrice());
        sqlParameterSource.addValue("description", product.getDescription());
        sqlParameterSource.addValue("stock", product.getStock());
        sqlParameterSource.addValue("created_date", product.getCreatedDate());
        sqlParameterSource.addValue("category_id", product.getCategoryId());
        return simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).intValue();
    }

    public Product findById(int id) throws Exception {
        String query = "SELECT * FROM Products WHERE id = " + id;
        RowMapper<Product> rowMapper = new BeanPropertyRowMapper<>(Product.class);
        Product product;
        product = jdbcTemplate.queryForObject(query, rowMapper);
        return product;
    }

    public void deleteById(int id) {
        String query = "DELETE FROM Products WHERE id = " + id;
        jdbcTemplate.update(query);
    }

    public void updateProductById(Product product) {
        String query = "UPDATE Products SET title = '" + product.getTitle() + "', price = " + product.getPrice() +
                ", description = '" + product.getDescription() + "', stock = " + product.getStock() +
                ", category_id = " + product.getCategoryId()+ " WHERE id = " + product.getId();
        jdbcTemplate.update(query);
    }

}
