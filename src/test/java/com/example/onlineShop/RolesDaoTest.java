package com.example.onlineShop;

import com.example.onlineShop.domain.Role;
import com.example.onlineShop.respository.RolesDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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
public class RolesDaoTest {

    private static final String ROLE_TITLE1 = "Pantaloni";
    private static final String ROLE_TITLE2 = "Bluze";

    @Autowired
    private RolesDao rolesDao;

    private Role role1;
    private Role role2;

    @Before
    public void setUp() {
        role1 = new Role();
        role2 = new Role();

        role1.setTitle(ROLE_TITLE1);
        role2.setTitle(ROLE_TITLE2);
    }

    @Test
    public void findAllRoles() {
        int rolesId1 = rolesDao.creste(role1);
        int rolesId2 = rolesDao.creste(role2);
        role1.setId(rolesId1);
        role2.setId(rolesId2);

        List<Role> actualList = rolesDao.findAll();

        List<Role> expectedList = new ArrayList<>();

        expectedList.add(role1);
        expectedList.add(role2);

        Assert.assertNotNull(actualList);
        Assert.assertEquals(actualList.size(), expectedList.size());
        Assert.assertTrue(actualList.contains(expectedList.get(0)));
        Assert.assertTrue(actualList.contains(expectedList.get(1)));

    }
}
