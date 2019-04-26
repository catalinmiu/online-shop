package com.example.onlineShop.controller;

import com.example.onlineShop.domain.Product;
import com.example.onlineShop.respository.ProductsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class ProductsController {

    @Autowired
    ProductsDao productsDao;

    @GetMapping("/")
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/products")
    public String getProducts(Model model){
        model.addAttribute("products", productsDao.findAll());
        return "products";
    }

    @PostMapping(value = "/createProduct", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity createProduct(@RequestBody Product product) {
        product.setCreatedDate(LocalDateTime.now());
        productsDao.create(product);
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @GetMapping("/createProduct")
    public String getCreateProduct() {
        return "createProduct";
    }

}
