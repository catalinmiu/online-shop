package com.example.onlineShop;

import com.example.onlineShop.domain.Role;
import com.example.onlineShop.domain.User;
import com.example.onlineShop.respository.RolesDao;
import com.example.onlineShop.respository.UsersDao;
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
public class UsersDaoTest {

    private static final String ROLE_TITLE1 = "Pantaloni";
    private static final String ROLE_TITLE2 = "Bluze";

    public static final String USERNAME1 = "User1";
    public static final String USERNAME2 = "User2";

    public static final String EMAIL1 = "Email1";
    public static final String EMAIL2 = "Email2";

    public static final String USER_PASSWORD1 = "pass1";
    public static final String USER_PASSWORD2 = "pass2";


    @Autowired
    private RolesDao rolesDao;

    @Autowired
    private UsersDao usersDao;

    private Role role1;
    private Role role2;

    private User user1;
    private User user2;

    @Before
    public void setUp() {
        role1 = new Role();
        role2 = new Role();

        user1 = new User();
        user2 = new User();

        role1.setTitle(ROLE_TITLE1);
        role2.setTitle(ROLE_TITLE2);

        user1.setUsername(USERNAME1);
        user1.setEmail(EMAIL1);
        user1.setUser_password(USER_PASSWORD1);

        user2.setUsername(USERNAME2);
        user2.setEmail(EMAIL2);
        user2.setUser_password(USER_PASSWORD2);
    }

    @Test
    public void findAllRoles() {
        int rolesId1 = rolesDao.creste(role1);
        int rolesId2 = rolesDao.creste(role2);
        role1.setId(rolesId1);
        role2.setId(rolesId2);

        user1.setId(rolesId1);
        user2.setId(rolesId2);
        int userId1 = usersDao.create(user1);
        int userId2 = usersDao.create(user2);

        user1.setId(userId1);
        user2.setId(userId2);

        List<User> actualList = usersDao.findALll();
        List<User> expectedList = new ArrayList<>();

        expectedList.add(user1);
        expectedList.add(user2);

        Assert.assertNotNull(actualList);
        Assert.assertEquals(actualList.size(), expectedList.size());
        Assert.assertTrue(actualList.contains(expectedList.get(0)));
        Assert.assertTrue(actualList.contains(expectedList.get(1)));

    }
}
