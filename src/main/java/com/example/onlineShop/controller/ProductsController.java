package com.example.onlineShop.controller;

import com.example.onlineShop.domain.*;
import com.example.onlineShop.respository.*;
import com.fasterxml.jackson.databind.JsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.security.Principal;
import java.time.LocalDateTime;

@Controller
public class ProductsController {

    @Autowired
    private ProductsDao productsDao;

    @Autowired
    private CategoriesDao categoriesDao;

    @Autowired
    private CartsDao cartsDao;

    @Autowired
    private CartProductsDao cartProductsDao;

    @Autowired
    private UsersDao usersDao;

    @GetMapping("/")
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/products")
    public String getProducts(Model model){
        model.addAttribute("products", productsDao.findAll());
        return "products";
    }

    @PostMapping(value = "/create_product", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity createProduct(@RequestBody Product product) {
        product.setCreatedDate(LocalDateTime.now());
        productsDao.create(product);
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @GetMapping("/create_product")
    public String getCreateProduct(Model model) {
        model.addAttribute("categories", categoriesDao.findAll());
        return "create_product";
    }

    @GetMapping("/create_category")
    public String getCreateCategory() {
        return "create_category";
    }

    @PostMapping(value = "/create_category", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity createCategory(@RequestBody Category category) {
        categoriesDao.create(category);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(value="/product/{productId}")
    public String getProduct(@PathVariable int productId, Model model) {
        try {
            model.addAttribute("product", productsDao.findById(productId));
        } catch (Exception e) {
            return "404";
        }
        return "product";
    }

    @PostMapping(value = "/product/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity product(@PathVariable int productId, final Principal principal) {
        String username = principal.getName();
        User user = null;
        Cart cart = null;
        CartProduct cartProduct = null;
        try {
            user = usersDao.findByName(username);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try {
            cart = cartsDao.findByUserId(user.getId());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try {
            cartProduct = cartProductsDao.findByProductId(productId);
            cartProduct.setUnits(cartProduct.getUnits() + 1);
            cartProductsDao.updateCartProduct(cartProduct);
        } catch(Exception e) {
            cartProduct = new CartProduct();
            cartProduct.setProduct_id(productId);
            cartProduct.setUnits(1);
            cartProduct.setCart_id(cart.getId());
            cartProductsDao.create(cartProduct);
        }




        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
