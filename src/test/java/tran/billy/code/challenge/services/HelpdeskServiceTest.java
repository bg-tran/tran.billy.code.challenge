package tran.billy.code.challenge.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;

import tran.billy.code.challenge.dao.OrganizationDAO;
import tran.billy.code.challenge.dao.TicketDAO;
import tran.billy.code.challenge.dao.UserDAO;
import tran.billy.code.challenge.dto.Organization;
import tran.billy.code.challenge.dto.Ticket;
import tran.billy.code.challenge.dto.User;

class HelpdeskServiceTest {

    OrganizationDAO orgDAO;
    UserDAO userDAO;
    TicketDAO ticketDAO;
    HelpdeskService service = new HelpdeskService();
    final Organization org1 = Mockito.spy(new Organization(){
        {
            setId(1001);
            setName("Org 1");
        }
    });

    final Organization org2 = Mockito.spy(new Organization(){
        {
            setId(1002);
            setName("Org 2");
        }
    });

    final User user1 = Mockito.spy(new User(){
        {
            setId(10001);
            setName("User 1");
            setOrganizationId(1001);
        }
    });

    final User user2 = Mockito.spy(new User(){
        {
            setId(10002);
            setName("User 2");
            setOrganizationId(1002);
        }
    });

    final Ticket ticket1 = Mockito.spy(new Ticket(){
        {
            setId("1000001");
            setSubject("Ticket 1");
            setOrganizationId(1001);
            setSubmitterId(10001);
            setAssigneeId(20001);
        }
    });

    final Ticket ticket2 = Mockito.spy(new Ticket(){
        {
            setId("1000002");
            setSubject("Ticket 2");
            setOrganizationId(1002);
            setSubmitterId(10002);
            setAssigneeId(20002);
        }
    });

    final Ticket ticket3 = Mockito.spy(new Ticket(){
        {
            setId("1000003");
            setSubject("Ticket 3");
            setOrganizationId(1003);
            setSubmitterId(10003);
            setAssigneeId(20003);
        }
    });

    @BeforeEach
    void setUp() {
        System.setProperty("datasource_path","src/test");
        orgDAO = Mockito.mock(OrganizationDAO.class);
        userDAO = Mockito.mock(UserDAO.class);
        ticketDAO = Mockito.mock(TicketDAO.class);
        service = new HelpdeskService(orgDAO, userDAO, ticketDAO);

        Mockito.when(orgDAO.findOrganizationsByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just(org1, org2));

        Mockito.when(userDAO.findUsersByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just(user1, user2));

        Mockito.when(ticketDAO.findTicketsByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just(ticket1, ticket2, ticket3));

    }


    @Test
    void testSearchOrganizationsWillReturnSuccess(){

        service.searchOrganizations("_id", "101");
        Mockito.verify(orgDAO,Mockito.times(1))
                .findOrganizationsByCriteria("_id","101");
        Mockito.verify(userDAO,Mockito.times(1))
                .findUsersByCriteria("organization_id","1001");
        Mockito.verify(userDAO,Mockito.times(1))
                .findUsersByCriteria("organization_id","1002");
        Mockito.verify(ticketDAO,Mockito.times(1))
                .findTicketsByCriteria("organization_id","1001");
        Mockito.verify(ticketDAO,Mockito.times(1))
                .findTicketsByCriteria("organization_id","1002");
        Assertions.assertEquals(2, org1.getUsers().size());
        Assertions.assertEquals(3, org1.getTickets().size());

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
        Mockito.verify(userDAO,Mockito.times(2))
                .findUsersByCriteria(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(ticketDAO,Mockito.times(2))
                .findTicketsByCriteria(Mockito.anyString(),Mockito.anyString());
        Assertions.assertEquals(0, org1.getUsers().size());
        Assertions.assertEquals(3, org1.getTickets().size());
    }

    @Test
    void testSearchOrganizationsWillReturnOrgHavingNoTicket(){

        Mockito.when(ticketDAO.findTicketsByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just());

        service.searchOrganizations("_id", "101");
        Mockito.verify(orgDAO,Mockito.times(1))
                .findOrganizationsByCriteria("_id","101");
        Mockito.verify(userDAO,Mockito.times(2))
                .findUsersByCriteria(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(ticketDAO,Mockito.times(2))
                .findTicketsByCriteria(Mockito.anyString(),Mockito.anyString());
        Assertions.assertEquals(2, org1.getUsers().size());
        Assertions.assertEquals(0, org1.getTickets().size());
    }

    @Test
    void testSearchUserWithSuccess(){

        Mockito.when(orgDAO.findOrganizationsByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just(org1));

        service.searchUsers("organization_id", "101");

        Mockito.verify(userDAO,Mockito.times(1))
                .findUsersByCriteria(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(orgDAO,Mockito.times(1))
                .findOrganizationsByCriteria("_id","1001");
        Mockito.verify(orgDAO,Mockito.times(1))
                .findOrganizationsByCriteria("_id","1002");
        Mockito.verify(ticketDAO,Mockito.times(1))
                .findTicketsByCriteria("submitter_id","10001");
        Mockito.verify(ticketDAO,Mockito.times(1))
                .findTicketsByCriteria("submitter_id","10002");
        Assertions.assertEquals(org1.getName(), user1.getOrganization().getName());
        Assertions.assertEquals(org1.getId(), user1.getOrganization().getId());
        Assertions.assertEquals(org1.getName(), user2.getOrganization().getName());
        Assertions.assertEquals(org1.getId(), user2.getOrganization().getId());
        Assertions.assertEquals(3, user1.getTickets().size());
        Assertions.assertEquals(3, user2.getTickets().size());
    }

    @Test
    void testSearchUserHavingNoTicket(){

        Mockito.when(orgDAO.findOrganizationsByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just(org1));
        Mockito.when(ticketDAO.findTicketsByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just());

        service.searchUsers("organization_id", "101");

        Mockito.verify(userDAO,Mockito.times(1))
                .findUsersByCriteria(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(orgDAO,Mockito.times(1))
                .findOrganizationsByCriteria("_id","1001");
        Mockito.verify(orgDAO,Mockito.times(1))
                .findOrganizationsByCriteria("_id","1002");
        Mockito.verify(ticketDAO,Mockito.times(1))
                .findTicketsByCriteria("submitter_id","10001");
        Mockito.verify(ticketDAO,Mockito.times(1))
                .findTicketsByCriteria("submitter_id","10002");
        Assertions.assertEquals(org1.getName(), user1.getOrganization().getName());
        Assertions.assertEquals(org1.getId(), user1.getOrganization().getId());
        Assertions.assertEquals(org1.getName(), user2.getOrganization().getName());
        Assertions.assertEquals(org1.getId(), user2.getOrganization().getId());
        Assertions.assertEquals(0, user1.getTickets().size());
        Assertions.assertEquals(0, user2.getTickets().size());
    }

    @Test
    void testSearchUserHavingNoOrganization(){

        Mockito.when(orgDAO.findOrganizationsByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just());
        Mockito.when(userDAO.findUsersByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just(user1));
        service.searchUsers("_id", "1001");

        Mockito.verify(userDAO,Mockito.times(1))
                .findUsersByCriteria(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(orgDAO,Mockito.times(1))
                .findOrganizationsByCriteria("_id","1001");
        Mockito.verify(ticketDAO,Mockito.times(1))
                .findTicketsByCriteria("submitter_id","10001");

        Assertions.assertNull(user1.getOrganization());
        Assertions.assertEquals(3, user1.getTickets().size());
    }

    @Test
    void testSearchTicketWithSuccess(){

        Mockito.when(orgDAO.findOrganizationsByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just(org1));
        Mockito.when(userDAO.findUsersByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just(user1));

        service.searchTickets("via", "web");

        Mockito.verify(ticketDAO,Mockito.times(1))
                .findTicketsByCriteria("via","web");
        Mockito.verify(orgDAO,Mockito.times(1))
                .findOrganizationsByCriteria("_id","1001");
        Mockito.verify(orgDAO,Mockito.times(1))
                .findOrganizationsByCriteria("_id","1002");
        Mockito.verify(orgDAO,Mockito.times(1))
                .findOrganizationsByCriteria("_id","1003");
        Mockito.verify(userDAO,Mockito.times(1))
                .findUsersByCriteria("_id","10001");
        Mockito.verify(userDAO,Mockito.times(1))
                .findUsersByCriteria("_id","10002");
        Mockito.verify(userDAO,Mockito.times(1))
                .findUsersByCriteria("_id","10003");
        Mockito.verify(userDAO,Mockito.times(1))
                .findUsersByCriteria("_id","20001");
        Mockito.verify(userDAO,Mockito.times(1))
                .findUsersByCriteria("_id","20002");
        Mockito.verify(userDAO,Mockito.times(1))
                .findUsersByCriteria("_id","20003");

        Assertions.assertEquals(org1.getName(), ticket1.getOrganization().getName());
        Assertions.assertEquals(org1.getId(), ticket1.getOrganization().getId());
        Assertions.assertEquals(org1.getName(), ticket2.getOrganization().getName());
        Assertions.assertEquals(org1.getId(), ticket2.getOrganization().getId());
        Assertions.assertEquals(org1.getName(), ticket3.getOrganization().getName());
        Assertions.assertEquals(org1.getId(), ticket3.getOrganization().getId());

        Assertions.assertEquals(user1.getId(), ticket1.getSubmitter().getId());
        Assertions.assertEquals(user1.getName(), ticket1.getAssignee().getName());
        Assertions.assertEquals(user1.getId(), ticket2.getSubmitter().getId());
        Assertions.assertEquals(user1.getName(), ticket2.getAssignee().getName());
        Assertions.assertEquals(user1.getId(), ticket3.getSubmitter().getId());
        Assertions.assertEquals(user1.getName(), ticket3.getAssignee().getName());

    }

    @Test
    void testSearchTicketHavingNoOrg(){

        Mockito.when(orgDAO.findOrganizationsByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just());
        Mockito.when(userDAO.findUsersByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just(user1));
        Mockito.when(ticketDAO.findTicketsByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just(ticket1));

        service.searchTickets("via", "web");

        Mockito.verify(ticketDAO,Mockito.times(1))
                .findTicketsByCriteria("via","web");
        Mockito.verify(orgDAO,Mockito.times(1))
                .findOrganizationsByCriteria("_id","1001");

        Mockito.verify(userDAO,Mockito.times(1))
                .findUsersByCriteria("_id","10001");

        Mockito.verify(userDAO,Mockito.times(1))
                .findUsersByCriteria("_id","20001");

        Assertions.assertNull(ticket1.getOrganization());

        Assertions.assertEquals(user1.getId(), ticket1.getSubmitter().getId());
        Assertions.assertEquals(user1.getName(), ticket1.getAssignee().getName());

    }


    @Test
    void testSearchTicketHavingNoUser(){

        Mockito.when(orgDAO.findOrganizationsByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just(org1));
        Mockito.when(userDAO.findUsersByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just());
        Mockito.when(ticketDAO.findTicketsByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just(ticket1));

        service.searchTickets("via", "web");

        Mockito.verify(ticketDAO,Mockito.times(1))
                .findTicketsByCriteria("via","web");
        Mockito.verify(orgDAO,Mockito.times(1))
                .findOrganizationsByCriteria("_id","1001");

        Mockito.verify(userDAO,Mockito.times(1))
                .findUsersByCriteria("_id","10001");

        Mockito.verify(userDAO,Mockito.times(1))
                .findUsersByCriteria("_id","20001");

        Assertions.assertEquals(org1.getName(), ticket1.getOrganization().getName());
        Assertions.assertEquals(org1.getId(), ticket1.getOrganization().getId());
        Assertions.assertNull(ticket1.getSubmitter());
        Assertions.assertNull(ticket1.getAssignee());

    }

}