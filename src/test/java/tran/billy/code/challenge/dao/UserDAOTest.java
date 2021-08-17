package tran.billy.code.challenge.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tran.billy.code.challenge.dto.User;

import java.util.List;
import java.util.stream.Collectors;

class UserDAOTest {

    UserDAO userDao;

    @BeforeEach
    void setUp() {
        userDao = new UserDAO("src/test/users.json");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testSearchOrgByCriteria(){
        List<User> actualResult = userDao.findUsersByCriteria("email","rosannasimpson@flotonic.com")
                .collect(Collectors.<User>toList())
                .block();
        Assertions.assertTrue(actualResult != null && actualResult.size() == 1);
        actualResult = userDao.findUsersByCriteria("email","1022")
                .collect(Collectors.<User>toList())
                .block();
        Assertions.assertTrue(actualResult != null && actualResult.size() == 0);

        actualResult = userDao.findUsersByCriteria("tags","Navarre")
                .collect(Collectors.<User>toList())
                .block();
        Assertions.assertTrue(actualResult != null && actualResult.size() == 1);

    }
}