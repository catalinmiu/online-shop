package com.example.onlineShop.respository;


import com.example.onlineShop.domain.User;
import org.h2.result.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UsersDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> findALll(){
        String query = "SELECT id, username, email, user_password, user_role FROM Users";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        return jdbcTemplate.query(query, rowMapper);
    }

    public int create(User user){
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("Users").
                usingColumns("username", "email", "user_password", "user_role").usingGeneratedKeyColumns("id");
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("username", user.getUsername());
        sqlParameterSource.addValue("email", user.getEmail());
        sqlParameterSource.addValue("user_password", user.getUser_password());
        sqlParameterSource.addValue("user_role", user.getUser_role());
        return simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).intValue();
    }
}
