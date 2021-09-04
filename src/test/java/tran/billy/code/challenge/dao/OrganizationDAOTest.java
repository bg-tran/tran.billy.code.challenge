package tran.billy.code.challenge.dao;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tran.billy.code.challenge.dto.Organization;

import java.util.List;

class OrganizationDAOTest {

    OrganizationDAO orgDao;

    @BeforeEach
    void setUp() throws ClassNotFoundException {
        Class.forName("tran.billy.code.challenge.stream.connector.FileStreamConnectorImpl");
        orgDao = new OrganizationDAO("src/test/organizations.json");
    }

    @Test
    void testSearchOrganizationsByCriteria(){
        List<Organization> actualResult = orgDao.findOrganizations("domain_names","datagen.com");
        Assertions.assertTrue(actualResult != null && actualResult.size() == 1);

        actualResult = orgDao.findOrganizations("foo","1022");
        Assertions.assertTrue(actualResult != null && actualResult.size() == 0);

        actualResult = orgDao.findOrganizations("_id","foo");
        Assertions.assertTrue(actualResult != null && actualResult.size() == 0);

        actualResult = orgDao.findOrganizations("_id","102");
        Assertions.assertTrue(actualResult != null && actualResult.size() == 1);
        Assertions.assertEquals("7cd6b8d4-2999-4ff2-8cfd-44d05b449226", actualResult.get(0).getExternalId());

    }

    @Test
    void testSearchOrganizationsByID(){

        Organization actualOrg = orgDao.findByID(102L);
        Assertions.assertTrue(actualOrg != null && actualOrg.getExternalId().equals("7cd6b8d4-2999-4ff2-8cfd-44d05b449226"));

        actualOrg = orgDao.findByID(null);
        Assertions.assertNull(actualOrg);

        actualOrg = orgDao.findByID(1022L);
        Assertions.assertNull(actualOrg);


    }
}