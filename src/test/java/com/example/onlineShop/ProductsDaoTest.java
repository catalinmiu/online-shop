package com.example.onlineShop;

import com.example.onlineShop.domain.Category;
import com.example.onlineShop.domain.Product;
import com.example.onlineShop.respository.CategoriesDao;
import com.example.onlineShop.respository.ProductsDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@JdbcTest
@ComponentScan
public class ProductsDaoTest {

    private static final String CATEGORY_TITLE1 = "Pantaloni";
    private static final String CATEGORY_TITLE2 = "Bluze";

    private static final String PRODUCT_TITLE1 = "Blugi";
    private static final String PRODUCT_TITLE2 = "Bluza Rosie";

    public static final float PRICE1 = 10f;
    public static final float PRICE2 = 3.14f;

    public static final String DESC1 = "Blugi Albastri";
    public static final String DESC2 = "Bluza Rosie cu maneca lunga";

    public static final int STOCK_PRODUCT1 = 5;
    public static final int STOCK_PRODUCT2 = 18;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final LocalDateTime CREATED_DATE1 = LocalDateTime.parse("2019-04-10 10:31", formatter);
    private static final LocalDateTime CREATED_DATE2 = LocalDateTime.parse("2019-06-10 10:31", formatter);

    @Autowired
    private CategoriesDao categoriesDao;

    @Autowired
    private ProductsDao productsDao;

    private Category category1;
    private Category category2;

    private Product product1;
    private Product product2;

    @Before
    public void setUp(){
        category1 = new Category();
        category2 = new Category();

        product1 = new Product();
        product2 = new Product();

        category1.setTitle(CATEGORY_TITLE1);
        category2.setTitle(CATEGORY_TITLE2);

        product1.setTitle(PRODUCT_TITLE1);
        product1.setDescription(DESC1);
        product1.setPrice(PRICE1);
        product1.setStock(STOCK_PRODUCT1);
        product1.setCreatedDate(CREATED_DATE1);

        product2.setTitle(PRODUCT_TITLE2);
        product2.setDescription(DESC2);
        product2.setPrice(PRICE2);
        product2.setStock(STOCK_PRODUCT2);
        product2.setCreatedDate(CREATED_DATE2);
    }

    @Test
    public void findAllProducts(){
        int categoryId1 = categoriesDao.create(category1);
        int categoryId2 = categoriesDao.create(category2);

        product1.setCategoryId(categoryId1);
        product2.setCategoryId(categoryId2);

        int productId1 = productsDao.create(product1);
        int productId2 = productsDao.create(product2);

        product1.setId(productId1);
        product2.setId(productId2);

        List<Product> actualProducts = productsDao.findAll();

        List<Product> expectedProducts = new ArrayList<>();

        expectedProducts.add(product1);
        expectedProducts.add(product2);

        Assert.assertNotNull(actualProducts);
        Assert.assertEquals(actualProducts.size(), expectedProducts.size());
        Assert.assertTrue(actualProducts.contains(expectedProducts.get(0)));
        Assert.assertTrue(actualProducts.contains(expectedProducts.get(1)));
    }
}
