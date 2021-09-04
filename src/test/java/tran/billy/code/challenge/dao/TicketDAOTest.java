package tran.billy.code.challenge.dao;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tran.billy.code.challenge.dto.Ticket;

import java.util.List;


class TicketDAOTest {

    TicketDAO ticketDao;

    @BeforeEach
    void setUp() throws ClassNotFoundException {
        Class.forName("tran.billy.code.challenge.stream.connector.FileStreamConnectorImpl");
        ticketDao = new TicketDAO("src/test/tickets.json");
    }

    @Test
    void testSearchTicketsByCriteria(){
        List<Ticket> actualResult = ticketDao.findTickets("submitter_id","38");
        Assertions.assertTrue(actualResult != null && actualResult.size() == 1);
        Assertions.assertNotEquals(0,actualResult.get(0).print().length());
        actualResult = ticketDao.findTickets("submitter_id","1022");
        Assertions.assertTrue(actualResult != null && actualResult.size() == 0);

        actualResult = ticketDao.findTickets("type","incident");

        Assertions.assertTrue(actualResult != null && actualResult.size() == 2);

        actualResult = ticketDao.findTickets("_id","1a227508-9f39-427c-8f57-1b72f3fab87c");
        Assertions.assertEquals("3e5ca820-cd1f-4a02-a18f-11b18e7bb49a",actualResult.get(0).getExternalId());

        actualResult = ticketDao.findTickets("_id","foo");
        Assertions.assertTrue(actualResult != null && actualResult.size() == 0);

        actualResult = ticketDao.findTickets("_id",null);
        Assertions.assertTrue(actualResult != null && actualResult.size() == 0);

        actualResult = ticketDao.findTickets("foo","bar");
        Assertions.assertTrue(actualResult != null && actualResult.size() == 0);

    }
}