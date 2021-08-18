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
    void setUp() throws ClassNotFoundException {
        Class.forName("tran.billy.code.challenge.stream.connector.FileStreamConnectorImpl");
        ticketDao = new TicketDAO("src/test/tickets.json");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testSearchTicketsByCriteria(){
        List<Ticket> actualResult = ticketDao.findTicketsByCriteria("submitter_id","38")
                .collect(Collectors.<Ticket>toList())
                .block();
        Assertions.assertTrue(actualResult != null && actualResult.size() == 1);
        Assertions.assertNotEquals(0,actualResult.get(0).print().length());
        actualResult = ticketDao.findTicketsByCriteria("submitter_id","1022")
                .collect(Collectors.<Ticket>toList())
                .block();
        Assertions.assertTrue(actualResult != null && actualResult.size() == 0);

        actualResult = ticketDao.findTicketsByCriteria("type","incident")
                .collect(Collectors.<Ticket>toList())
                .block();
        Assertions.assertTrue(actualResult != null && actualResult.size() == 2);

    }
}