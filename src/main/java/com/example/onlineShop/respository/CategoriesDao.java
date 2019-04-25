package com.example.onlineShop.respository;


import com.example.onlineShop.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoriesDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Category> findAll(){
        String query = "SELECT id, title FROM Category";
        RowMapper<Category> rowMapper = new BeanPropertyRowMapper<>(Category.class);
        return jdbcTemplate.query(query, rowMapper);
    }

    public int create(Category category){
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("Category").usingColumns("title").usingGeneratedKeyColumns("id");
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("title", category.getTitle());
        return simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).intValue();
    }
}
