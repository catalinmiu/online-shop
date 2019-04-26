package com.example.onlineShop.respository;


import com.example.onlineShop.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RolesDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Role> findAll(){
        String query = "SELECT id, title FROM Roles";
        RowMapper <Role> rowMapper = new BeanPropertyRowMapper<>(Role.class);
        return jdbcTemplate.query(query, rowMapper);
    }

    public int creste(Role role){
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("Roles").usingColumns("title").usingGeneratedKeyColumns("id");
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("title", role.getTitle());
        return simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).intValue();
    }
}
