package tran.billy.code.challenge.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tran.billy.code.challenge.dto.Organization;

import java.util.List;
import java.util.stream.Collectors;

class OrganizationDAOTest {

    OrganizationDAO orgDao;

    @BeforeEach
    void setUp() throws ClassNotFoundException {
        Class.forName("tran.billy.code.challenge.stream.connector.FileStreamConnectorImpl");
        orgDao = new OrganizationDAO("src/test/organizations.json");
    }

    @Test
    void testSearchOrganizationsByCriteria(){
        List<Organization> actualResult = orgDao.findOrganizationsByCriteria("_id","102")
                .collect(Collectors.<Organization>toList())
                .block();
        Assertions.assertTrue(actualResult != null && actualResult.size() == 1);
        actualResult = orgDao.findOrganizationsByCriteria("_id","1022")
                .collect(Collectors.<Organization>toList())
                .block();
        Assertions.assertTrue(actualResult != null && actualResult.size() == 0);
        actualResult = orgDao.findOrganizationsByCriteria("domain_names","datagen.com")
                .collect(Collectors.<Organization>toList())
                .block();
        Assertions.assertTrue(actualResult != null && actualResult.size() == 1);
    }
}