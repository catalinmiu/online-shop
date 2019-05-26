package com.example.onlineShop.controller;

import com.example.onlineShop.domain.*;
import com.example.onlineShop.domain.dto.CartDto;
import com.example.onlineShop.respository.*;
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
import java.util.List;

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
            cart = cartsDao.findCurrentCartByUserId(user.getId());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try {
            cartProduct = cartProductsDao.findByProductIdAndCartId(productId, cart.getId());
            cartProduct.setUnits(cartProduct.getUnits() + 1);
            cartProductsDao.setUnitsByCartProductId(cartProduct);
        } catch(Exception e) {
            cartProduct = new CartProduct();
            cartProduct.setProduct_id(productId);
            cartProduct.setUnits(1);
            cartProduct.setCart_id(cart.getId());
            cartProductsDao.create(cartProduct);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(value="/myCart")
    public String getCart(Model model, final Principal principal) {
        String username = principal.getName();
        User user = null;
        Cart cart = null;
        List<CartDto> cartProducts = null;

        try {
            user = usersDao.findByName(username);
        } catch(Exception e) {
            return "404";
        }

        try {
            cart = cartsDao.findCurrentCartByUserId(user.getId());
        } catch (Exception e) {
            return "404";
        }

        try {
            cartProducts = cartProductsDao.findAllProductsByCartId(cart.getId());
        } catch (Exception e) {
            return "404";
        }

        model.addAttribute("products", cartProducts);

        return "cart";
    }

    @PostMapping(value = "/myCart-checkout", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity cart_checkout(@RequestBody List<CartProduct> cartProducts, Principal principal) {
        String username = principal.getName();
        User user = null;
        Cart cart = null;
        List<CartProduct> cartProductFromDb = null;

        try {
            user = usersDao.findByName(username);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            cart = cartsDao.findCurrentCartByUserId(user.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            cartProductFromDb = cartProductsDao.findAllCartProductByCartId(cart.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if(cartProductFromDb.size() != cartProducts.size()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else {
            for(int i=0;i<cartProducts.size();i++) {
                for(int j=0;j<cartProductFromDb.size();j++) {
                    if(cartProductFromDb.get(j).getProduct_id() == cartProducts.get(i).getProduct_id()) {
                        if(cartProducts.get(i).getUnits() != cartProductFromDb.get(i).getUnits()) {
                            cartProductFromDb.get(j).setUnits(cartProducts.get(i).getUnits());
                            cartProductsDao.setUnitsByCartProductId(cartProductFromDb.get(j));
                        }
                    }
                }
            }
            cartProductsDao.deleteById(cartProductFromDb.get(0).getCart_id(), 0);
        }

        cart.setPaidDate(LocalDateTime.now());
        cartsDao.updateCart(cart);

        Cart newCart = new Cart();
        newCart.setUser_id(user.getId());
        cartsDao.create(newCart);

    return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(value = "/myCart-save", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity cart_save(@RequestBody List<CartProduct> cartProducts, Principal principal) {
        String username = principal.getName();
        User user = null;
        Cart cart = null;
        List<CartProduct> cartProductFromDb = null;

        try {
            user = usersDao.findByName(username);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            cart = cartsDao.findCurrentCartByUserId(user.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            cartProductFromDb = cartProductsDao.findAllCartProductByCartId(cart.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if(cartProductFromDb.size() != cartProducts.size()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else {
            for (int i = 0; i < cartProducts.size(); i++) {
                for (int j = 0; j < cartProductFromDb.size(); j++) {
                    if (cartProductFromDb.get(j).getProduct_id() == cartProducts.get(i).getProduct_id()) {
                        if (cartProducts.get(i).getUnits() != cartProductFromDb.get(i).getUnits()) {
                            cartProductFromDb.get(j).setUnits(cartProducts.get(i).getUnits());
                            cartProductsDao.setUnitsByCartProductId(cartProductFromDb.get(j));
                        }
                    }
                }
            }
            cartProductsDao.deleteById(cartProductFromDb.get(0).getCart_id(), 0);
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/admin_products")
    public String getAdminProducts(Model model) {
        model.addAttribute("products", productsDao.findAll());
        return "admin_products";
    }

    @PostMapping(value = "/admin_products_delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity adminProductsDelete(@RequestBody Integer productId) {
        productsDao.deleteById(productId);
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @GetMapping("/admin_edit_product/{productId}")
    public String getAdminEditProduct(@PathVariable int productId, Model model) {
        try {
            model.addAttribute("product", productsDao.findById(productId));
            model.addAttribute("categories", categoriesDao.findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "admin_edit_product";
    }

    @PostMapping(value = "/admin_edit_product", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity adminEditProduct(@RequestBody Product product) {
        productsDao.updateProductById(product);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/admin_overview")
    public String getAdminOverview(Model model) {

        float total = 0;
        int id;
        Product p1;
        List<CartProduct> cartProducts = cartProductsDao.findAllPaidProduct();
        for (CartProduct i : cartProducts) {
            id = i.getProduct_id();
            try {
                p1 = productsDao.findById(id);
                total += p1.getPrice() * i.getUnits();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }

        List<Product> products = productsDao.findAll();
        int count = 0;
        for (Product product : products) {
            count += product.getStock();
        }

        try {
            model.addAttribute("profit", total);
            model.addAttribute("stock", count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "admin_overview";
    }

}
