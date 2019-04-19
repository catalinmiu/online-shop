package com.example.onlineShop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductsController {

    @GetMapping("/")
    public String getHomePage() {
        return "home";
    }

}
