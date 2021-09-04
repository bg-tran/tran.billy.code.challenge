package tran.billy.code.challenge.services;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import tran.billy.code.challenge.dao.OrganizationDAO;
import tran.billy.code.challenge.dao.TicketDAO;
import tran.billy.code.challenge.dao.UserDAO;
import tran.billy.code.challenge.dao.exception.EntityNotFoundException;
import tran.billy.code.challenge.dto.Organization;
import tran.billy.code.challenge.dto.Ticket;
import tran.billy.code.challenge.dto.User;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

class HelpdeskServiceTest {

    OrganizationDAO orgDAO;
    UserDAO userDAO;
    TicketDAO ticketDAO;
    HelpdeskService service;

    InputStream stdInStream;

    final Organization org = Mockito.spy(new Organization(){
        {
            setId(1001L);
            setName("Org 1");
        }
    });

    final User user = Mockito.spy(new User(){
        {
            setId(10001L);
            setName("User 1");
            setOrganizationId(1001L);
        }
    });


    final Ticket ticket = Mockito.spy(new Ticket(){
        {
            setId("1000001");
            setSubject("Ticket 1");
            setOrganizationId(1001L);
            setSubmitterId(10001L);
            setAssigneeId(20001L);
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

        Mockito.when(orgDAO.findOrganizations(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(new ArrayList<Organization>(){{ add(org); }});

        Mockito.when(userDAO.findUsers(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(new ArrayList<User>() {{ add(user); }});

        Mockito.when(ticketDAO.findTickets(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(new ArrayList<Ticket>() {{ add(ticket); }});

        Mockito.when(orgDAO.findByID(Mockito.anyLong()))
                .thenReturn(org);

        Mockito.when(userDAO.findByID(Mockito.anyLong()))
                .thenReturn(user);

    }

    @AfterEach
    void teardown() {
        System.setIn(stdInStream);
    }

    @Test
    void testSearchOrganizationsWillReturnSuccess(){

        service.searchOrganizations("_id", "101");
        Mockito.verify(orgDAO,Mockito.times(1))
                .findOrganizations("_id","101");
        Mockito.verify(userDAO,Mockito.times(1))
                .findUsers("organization_id","1001");

        Mockito.verify(ticketDAO,Mockito.times(1))
                .findTickets("organization_id","1001");

        Assertions.assertEquals(1, org.getUsers().size());
        Assertions.assertEquals(1, org.getTickets().size());

    }

    @Test
    void testSearchOrganizationsWillReturnEmptyResult(){

        Mockito.when(orgDAO.findOrganizations(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(new ArrayList<>());

        service.searchOrganizations("_id", "101");
        Mockito.verify(orgDAO,Mockito.times(1))
                .findOrganizations("_id","101");
        Mockito.verify(userDAO,Mockito.times(0))
                .findUsers(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(ticketDAO,Mockito.times(0))
                .findTickets(Mockito.anyString(),Mockito.anyString());

    }

    @Test
    void testSearchOrganizationsWillReturnOrgHavingNoUser(){

        Mockito.when(userDAO.findUsers(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(new ArrayList<>());

        service.searchOrganizations("_id", "101");
        Mockito.verify(orgDAO,Mockito.times(1))
                .findOrganizations("_id","101");
        Mockito.verify(userDAO,Mockito.times(1))
                .findUsers(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(ticketDAO,Mockito.times(1))
                .findTickets(Mockito.anyString(),Mockito.anyString());
        Assertions.assertEquals(0, org.getUsers().size());
        Assertions.assertEquals(1, org.getTickets().size());
    }

    @Test
    void testSearchOrganizationsWillReturnOrgHavingNoTicket(){

        Mockito.when(ticketDAO.findTickets(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(new ArrayList<>());

        service.searchOrganizations("_id", "101");
        Mockito.verify(orgDAO,Mockito.times(1))
                .findOrganizations("_id","101");
        Mockito.verify(userDAO,Mockito.times(1))
                .findUsers(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(ticketDAO,Mockito.times(1))
                .findTickets(Mockito.anyString(),Mockito.anyString());
        Assertions.assertEquals(1, org.getUsers().size());
        Assertions.assertEquals(0, org.getTickets().size());
    }

    @Test
    void testSearchUserWithSuccess(){

        service.searchUsers("organization_id", "101");

        Mockito.verify(userDAO,Mockito.times(1))
                .findUsers(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(orgDAO,Mockito.times(1))
                .findByID(Long.parseLong("1001"));

        Mockito.verify(ticketDAO,Mockito.times(1))
                .findTickets("submitter_id","10001");

        Assertions.assertEquals(org.getName(), user.getOrganization().getName());
        Assertions.assertEquals(org.getId(), user.getOrganization().getId());
        Assertions.assertEquals(1, user.getTickets().size());
    }

    @Test
    void testSearchUserHavingNoTicket(){

        Mockito.when(ticketDAO.findTickets(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(new ArrayList<>());

        service.searchUsers("organization_id", "101");

        Mockito.verify(userDAO,Mockito.times(1))
                .findUsers(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(orgDAO,Mockito.times(1))
                .findByID(Long.parseLong("1001"));

        Mockito.verify(ticketDAO,Mockito.times(1))
                .findTickets("submitter_id","10001");

        Assertions.assertEquals(org.getName(), user.getOrganization().getName());
        Assertions.assertEquals(org.getId(), user.getOrganization().getId());
        Assertions.assertEquals(0, user.getTickets().size());

    }

    @Test
    void testSearchUserHavingNoOrganization() throws EntityNotFoundException {

        Mockito.when(orgDAO.findByID(Mockito.anyLong()))
                .thenReturn(null);

        service.searchUsers("_id", "1001");

        Mockito.verify(userDAO,Mockito.times(1))
                .findUsers(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(orgDAO,Mockito.times(1))
                .findByID(Long.parseLong("1001"));
        Mockito.verify(ticketDAO,Mockito.times(1))
                .findTickets("submitter_id","10001");

        Assertions.assertNull(user.getOrganization());
        Assertions.assertEquals(1, user.getTickets().size());
    }

    @Test
    void testSearchTicketWithSuccess(){

        service.searchTickets("via", "web");

        Mockito.verify(ticketDAO,Mockito.times(1))
                .findTickets("via","web");
        Mockito.verify(orgDAO,Mockito.times(1))
                .findByID(Long.parseLong("1001"));


        Mockito.verify(userDAO,Mockito.times(1))
                .findByID(Long.parseLong("10001"));

        Mockito.verify(userDAO,Mockito.times(1))
                .findByID(Long.parseLong("20001"));


        Assertions.assertEquals(org.getName(), ticket.getOrganization().getName());
        Assertions.assertEquals(org.getId(), ticket.getOrganization().getId());
        Assertions.assertEquals(user.getId(), ticket.getSubmitter().getId());
        Assertions.assertEquals(user.getName(), ticket.getAssignee().getName());


    }

    @Test
    void testSearchTicketHavingNoOrg(){

        Mockito.when(orgDAO.findByID(Mockito.anyLong()))
                .thenReturn(null);

        service.searchTickets("via", "web");

        Mockito.verify(ticketDAO,Mockito.times(1))
                .findTickets("via","web");
        Mockito.verify(orgDAO,Mockito.times(1))
                .findByID(Long.parseLong("1001"));

        Mockito.verify(userDAO,Mockito.times(1))
                .findByID(Long.parseLong("10001"));

        Mockito.verify(userDAO,Mockito.times(1))
                .findByID(Long.parseLong("20001"));

        Assertions.assertNull(ticket.getOrganization());

        Assertions.assertEquals(user.getId(), ticket.getSubmitter().getId());
        Assertions.assertEquals(user.getName(), ticket.getAssignee().getName());

    }


    @Test
    void testSearchTicketHavingNoUser(){


        Mockito.when(userDAO.findByID(Mockito.anyLong()))
                .thenReturn(null);

        service.searchTickets("via", "web");

        Mockito.verify(ticketDAO,Mockito.times(1))
                .findTickets("via","web");
        Mockito.verify(orgDAO,Mockito.times(1))
                .findByID(Long.parseLong("1001"));

        Mockito.verify(userDAO,Mockito.times(1))
                .findByID(Long.parseLong("10001"));

        Mockito.verify(userDAO,Mockito.times(1))
                .findByID(Long.parseLong("20001"));

        Assertions.assertEquals(org.getName(), ticket.getOrganization().getName());
        Assertions.assertEquals(org.getId(), ticket.getOrganization().getId());
        Assertions.assertNull(ticket.getSubmitter());
        Assertions.assertNull(ticket.getAssignee());

    }

}