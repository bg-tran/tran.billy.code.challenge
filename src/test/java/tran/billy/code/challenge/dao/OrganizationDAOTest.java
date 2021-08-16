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
    void setUp() {
        orgDao = new OrganizationDAO("src/test/organizations.json");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testSearchOrgByCriteria(){
        List<Organization> actualResult = orgDao.searchOrganizationByCriteria("_id","102")
                .collect(Collectors.<Organization>toList())
                .block();
        Assertions.assertEquals(1, actualResult.size());
        actualResult = orgDao.searchOrganizationByCriteria("_id","1022")
                .collect(Collectors.<Organization>toList())
                .block();
        Assertions.assertEquals(0, actualResult.size());
        actualResult = orgDao.searchOrganizationByCriteria("domain_names","datagen.com")
                .collect(Collectors.<Organization>toList())
                .block();
        Assertions.assertEquals(1, actualResult.size());
//        System.out.print(actualResult.get(0).print());
    }
}