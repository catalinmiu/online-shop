package com.example.onlineShop.controller;

import com.example.onlineShop.domain.User;
import com.example.onlineShop.respository.UsersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsersController {

    @Autowired
    UsersDao usersDao;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/sign_up")
    public String getSignUp() { return "sign_up"; }

    @PostMapping(value="/sign_up", consumes= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity createUser(@RequestBody User user) {
        user.setUser_role(1);
        user.setUser_password(bCryptPasswordEncoder.encode(user.getUser_password()));
        usersDao.create(user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
