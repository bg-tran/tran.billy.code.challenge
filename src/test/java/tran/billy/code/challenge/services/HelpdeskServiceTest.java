package tran.billy.code.challenge.services;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;

import tran.billy.code.challenge.dao.OrganizationDAO;
import tran.billy.code.challenge.dao.TicketDAO;
import tran.billy.code.challenge.dao.UserDAO;
import tran.billy.code.challenge.dto.Organization;
import tran.billy.code.challenge.dto.Ticket;
import tran.billy.code.challenge.dto.User;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

class HelpdeskServiceTest {

    OrganizationDAO orgDAO;
    UserDAO userDAO;
    TicketDAO ticketDAO;
    HelpdeskService service;

    InputStream stdInStream;

    final Organization org = Mockito.spy(new Organization(){
        {
            setId(1001);
            setName("Org 1");
        }
    });

    final User user = Mockito.spy(new User(){
        {
            setId(10001);
            setName("User 1");
            setOrganizationId(1001);
        }
    });


    final Ticket ticket = Mockito.spy(new Ticket(){
        {
            setId("1000001");
            setSubject("Ticket 1");
            setOrganizationId(1001);
            setSubmitterId(10001);
            setAssigneeId(20001);
        }
    });



    @BeforeEach
    void setUp() {

        stdInStream =  System.in;
        System.setIn(new ByteArrayInputStream("\n".getBytes(StandardCharsets.UTF_8)));

        orgDAO = Mockito.mock(OrganizationDAO.class);
        userDAO = Mockito.mock(UserDAO.class);
        ticketDAO = Mockito.mock(TicketDAO.class);
        service = new HelpdeskService(orgDAO, userDAO, ticketDAO);

        Mockito.when(orgDAO.findOrganizationsByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just(org));

        Mockito.when(userDAO.findUsersByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just(user));

        Mockito.when(ticketDAO.findTicketsByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just(ticket));

    }

    @AfterEach
    void teardown() {
        System.setIn(stdInStream);
    }

    @Test
    void testSearchOrganizationsWillReturnSuccess(){

        service.searchOrganizations("_id", "101");
        Mockito.verify(orgDAO,Mockito.times(1))
                .findOrganizationsByCriteria("_id","101");
        Mockito.verify(userDAO,Mockito.times(1))
                .findUsersByCriteria("organization_id","1001");

        Mockito.verify(ticketDAO,Mockito.times(1))
                .findTicketsByCriteria("organization_id","1001");

        Assertions.assertEquals(1, org.getUsers().size());
        Assertions.assertEquals(1, org.getTickets().size());

    }

    @Test
    void testSearchOrganizationsWillReturnEmptyResult(){

        Mockito.when(orgDAO.findOrganizationsByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just());

        service.searchOrganizations("_id", "101");
        Mockito.verify(orgDAO,Mockito.times(1))
                .findOrganizationsByCriteria("_id","101");
        Mockito.verify(userDAO,Mockito.times(0))
                .findUsersByCriteria(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(ticketDAO,Mockito.times(0))
                .findTicketsByCriteria(Mockito.anyString(),Mockito.anyString());

    }

    @Test
    void testSearchOrganizationsWillReturnOrgHavingNoUser(){

        Mockito.when(userDAO.findUsersByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just());

        service.searchOrganizations("_id", "101");
        Mockito.verify(orgDAO,Mockito.times(1))
                .findOrganizationsByCriteria("_id","101");
        Mockito.verify(userDAO,Mockito.times(1))
                .findUsersByCriteria(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(ticketDAO,Mockito.times(1))
                .findTicketsByCriteria(Mockito.anyString(),Mockito.anyString());
        Assertions.assertEquals(0, org.getUsers().size());
        Assertions.assertEquals(1, org.getTickets().size());
    }

    @Test
    void testSearchOrganizationsWillReturnOrgHavingNoTicket(){

        Mockito.when(ticketDAO.findTicketsByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just());

        service.searchOrganizations("_id", "101");
        Mockito.verify(orgDAO,Mockito.times(1))
                .findOrganizationsByCriteria("_id","101");
        Mockito.verify(userDAO,Mockito.times(1))
                .findUsersByCriteria(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(ticketDAO,Mockito.times(1))
                .findTicketsByCriteria(Mockito.anyString(),Mockito.anyString());
        Assertions.assertEquals(1, org.getUsers().size());
        Assertions.assertEquals(0, org.getTickets().size());
    }

    @Test
    void testSearchUserWithSuccess(){

        Mockito.when(orgDAO.findOrganizationsByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just(org));

        service.searchUsers("organization_id", "101");

        Mockito.verify(userDAO,Mockito.times(1))
                .findUsersByCriteria(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(orgDAO,Mockito.times(1))
                .findOrganizationsByCriteria("_id","1001");

        Mockito.verify(ticketDAO,Mockito.times(1))
                .findTicketsByCriteria("submitter_id","10001");

        Assertions.assertEquals(org.getName(), user.getOrganization().getName());
        Assertions.assertEquals(org.getId(), user.getOrganization().getId());
        Assertions.assertEquals(1, user.getTickets().size());
    }

    @Test
    void testSearchUserHavingNoTicket(){

        Mockito.when(orgDAO.findOrganizationsByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just(org));
        Mockito.when(ticketDAO.findTicketsByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just());

        service.searchUsers("organization_id", "101");

        Mockito.verify(userDAO,Mockito.times(1))
                .findUsersByCriteria(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(orgDAO,Mockito.times(1))
                .findOrganizationsByCriteria("_id","1001");

        Mockito.verify(ticketDAO,Mockito.times(1))
                .findTicketsByCriteria("submitter_id","10001");

        Assertions.assertEquals(org.getName(), user.getOrganization().getName());
        Assertions.assertEquals(org.getId(), user.getOrganization().getId());
        Assertions.assertEquals(0, user.getTickets().size());

    }

    @Test
    void testSearchUserHavingNoOrganization(){

        Mockito.when(orgDAO.findOrganizationsByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just());
        Mockito.when(userDAO.findUsersByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just(user));
        service.searchUsers("_id", "1001");

        Mockito.verify(userDAO,Mockito.times(1))
                .findUsersByCriteria(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(orgDAO,Mockito.times(1))
                .findOrganizationsByCriteria("_id","1001");
        Mockito.verify(ticketDAO,Mockito.times(1))
                .findTicketsByCriteria("submitter_id","10001");

        Assertions.assertNull(user.getOrganization());
        Assertions.assertEquals(1, user.getTickets().size());
    }

    @Test
    void testSearchTicketWithSuccess(){

        Mockito.when(orgDAO.findOrganizationsByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just(org));
        Mockito.when(userDAO.findUsersByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just(user));

        service.searchTickets("via", "web");

        Mockito.verify(ticketDAO,Mockito.times(1))
                .findTicketsByCriteria("via","web");
        Mockito.verify(orgDAO,Mockito.times(1))
                .findOrganizationsByCriteria("_id","1001");


        Mockito.verify(userDAO,Mockito.times(1))
                .findUsersByCriteria("_id","10001");

        Mockito.verify(userDAO,Mockito.times(1))
                .findUsersByCriteria("_id","20001");


        Assertions.assertEquals(org.getName(), ticket.getOrganization().getName());
        Assertions.assertEquals(org.getId(), ticket.getOrganization().getId());
        Assertions.assertEquals(user.getId(), ticket.getSubmitter().getId());
        Assertions.assertEquals(user.getName(), ticket.getAssignee().getName());


    }

    @Test
    void testSearchTicketHavingNoOrg(){

        Mockito.when(orgDAO.findOrganizationsByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just());
        Mockito.when(userDAO.findUsersByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just(user));
        Mockito.when(ticketDAO.findTicketsByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just(ticket));

        service.searchTickets("via", "web");

        Mockito.verify(ticketDAO,Mockito.times(1))
                .findTicketsByCriteria("via","web");
        Mockito.verify(orgDAO,Mockito.times(1))
                .findOrganizationsByCriteria("_id","1001");

        Mockito.verify(userDAO,Mockito.times(1))
                .findUsersByCriteria("_id","10001");

        Mockito.verify(userDAO,Mockito.times(1))
                .findUsersByCriteria("_id","20001");

        Assertions.assertNull(ticket.getOrganization());

        Assertions.assertEquals(user.getId(), ticket.getSubmitter().getId());
        Assertions.assertEquals(user.getName(), ticket.getAssignee().getName());

    }


    @Test
    void testSearchTicketHavingNoUser(){

        Mockito.when(orgDAO.findOrganizationsByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just(org));
        Mockito.when(userDAO.findUsersByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just());
        Mockito.when(ticketDAO.findTicketsByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just(ticket));

        service.searchTickets("via", "web");

        Mockito.verify(ticketDAO,Mockito.times(1))
                .findTicketsByCriteria("via","web");
        Mockito.verify(orgDAO,Mockito.times(1))
                .findOrganizationsByCriteria("_id","1001");

        Mockito.verify(userDAO,Mockito.times(1))
                .findUsersByCriteria("_id","10001");

        Mockito.verify(userDAO,Mockito.times(1))
                .findUsersByCriteria("_id","20001");

        Assertions.assertEquals(org.getName(), ticket.getOrganization().getName());
        Assertions.assertEquals(org.getId(), ticket.getOrganization().getId());
        Assertions.assertNull(ticket.getSubmitter());
        Assertions.assertNull(ticket.getAssignee());

    }

}