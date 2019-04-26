package com.example.onlineShop;


import com.example.onlineShop.domain.Category;
import com.example.onlineShop.respository.CategoriesDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@JdbcTest
@ComponentScan
public class CategoriesDaoTest {

    private static final String TITLE1 = "Pantaloni";
    private static final String TITLE2 = "Bluze";

    @Autowired
    private CategoriesDao categoriesDao;

    private Category category1;
    private Category category2;

    @Before
    public void setUp() {
        category1 = new Category();
        category2 = new Category();
        category1.setTitle(TITLE1);
        category2.setTitle(TITLE2);

    }

    @Test
    public void finAllCategories() {
        int categoryId1 = categoriesDao.create(category1);
        category1.setId(categoryId1);
        int categoryId2 = categoriesDao.create(category2);
        category2.setId(categoryId2);

        List<Category> actualCategories = categoriesDao.findAll();

        List<Category> expectedCategories = new ArrayList<>();
        expectedCategories.add(category1);
        expectedCategories.add(category2);

        Assert.assertNotNull(actualCategories);
        Assert.assertEquals(actualCategories.size(), expectedCategories.size());
        Assert.assertTrue(actualCategories.contains(expectedCategories.get(0)));
        Assert.assertTrue(actualCategories.contains(expectedCategories.get(1)));

    }
}
