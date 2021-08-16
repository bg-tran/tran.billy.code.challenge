package tran.billy.code.challenge.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tran.billy.code.challenge.dto.Ticket;

import java.util.List;
import java.util.stream.Collectors;

class TicketDAOTest {

    TicketDAO ticketDao;

    @BeforeEach
    void setUp() {
        ticketDao = new TicketDAO("src/test/tickets.json");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testSearchOrgByCriteria(){
        List<Ticket> actualResult = ticketDao.searchTicketsByCriteria("submitter_id","38")
                .collect(Collectors.<Ticket>toList())
                .block();
        Assertions.assertEquals(1, actualResult.size());
        Assertions.assertNotEquals(0,actualResult.get(0).print().length());
        System.out.print(actualResult.get(0).print());
        actualResult = ticketDao.searchTicketsByCriteria("submitter_id","1022")
                .collect(Collectors.<Ticket>toList())
                .block();
        Assertions.assertEquals(0, actualResult.size());

    }
}