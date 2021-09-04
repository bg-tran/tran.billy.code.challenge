package tran.billy.code.challenge.dao;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tran.billy.code.challenge.dto.Organization;
import tran.billy.code.challenge.dto.User;

import java.util.List;


class UserDAOTest {

    UserDAO userDao;

    @BeforeEach
    void setUp() throws ClassNotFoundException {
        Class.forName("tran.billy.code.challenge.stream.connector.FileStreamConnectorImpl");
        userDao = new UserDAO("src/test/users.json");
    }

    @Test
    void testSearchUsersByCriteria(){
        List<User> actualResult = userDao.findUsers("email","rosannasimpson@flotonic.com");
        Assertions.assertTrue(actualResult != null && actualResult.size() == 1);
        Assertions.assertEquals("119",actualResult.get(0).getOrganizationId().toString());

        actualResult = userDao.findUsers("email","1022");
        Assertions.assertTrue(actualResult != null && actualResult.size() == 0);

        actualResult = userDao.findUsers("tags","Navarre");
        Assertions.assertTrue(actualResult != null && actualResult.size() == 1);

        actualResult = userDao.findUsers("organization_id","104");
        Assertions.assertTrue(actualResult != null && actualResult.size() == 1);
        Assertions.assertEquals("3",actualResult.get(0).getId().toString());

        actualResult = userDao.findUsers("_id","1");
        Assertions.assertTrue(actualResult != null && actualResult.size() == 1);
        Assertions.assertEquals("74341f74-9c79-49d5-9611-87ef9b6eb75f",actualResult.get(0).getExternalId());

        actualResult = userDao.findUsers("foo","bar");
        Assertions.assertTrue(actualResult != null && actualResult.size() == 0);

        actualResult = userDao.findUsers("_id","foo");
        Assertions.assertTrue(actualResult != null && actualResult.size() == 0);

    }

    @Test
    void testSearchOrganizationsByID(){

        User actualUser = userDao.findByID(1L);
        Assertions.assertTrue(actualUser != null && actualUser.getExternalId().equals("74341f74-9c79-49d5-9611-87ef9b6eb75f"));

        actualUser = userDao.findByID(null);
        Assertions.assertNull(actualUser);

        actualUser = userDao.findByID(1022L);
        Assertions.assertNull(actualUser);

    }
}