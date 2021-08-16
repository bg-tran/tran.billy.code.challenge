package tran.billy.code.challenge.services;

import org.junit.jupiter.api.AfterEach;
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

import java.util.ArrayList;

class HelpdeskServiceTest {

    OrganizationDAO orgDAO;
    UserDAO userDAO;
    TicketDAO ticketDAO;
    HelpdeskService service;

    @BeforeEach
    void setUp() {
        System.setProperty("datasource_path","src/test");
        orgDAO = Mockito.mock(OrganizationDAO.class);
        userDAO = Mockito.mock(UserDAO.class);
        ticketDAO = Mockito.mock(TicketDAO.class);
        service = new HelpdeskService(orgDAO, userDAO, ticketDAO);

        Mockito.when(orgDAO.findOrganizationsByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just(new Organization(){{
                    setId(101);
                    setUrl("http://initech.zendesk.com/api/v2/organizations/101.json");
                    setExternalId("9270ed79-35eb-4a38-a46f-35725197ea8d");
                    setName("Enthaze 2");
                    setDomainNames(new ArrayList<String>(){{
                        add("kage.com");
                        add("ecratic.com");
                        add("endipin.com");
                        add("zentix.com");
                    }});
                    setCreatedAt("2016-05-21T11:10:28 -10:00");
                    setDetails("MegaCorp");
                    setSharedTickets(false);
                    setTags(new ArrayList<String>(){{
                        add("Fulton");
                        add("West");
                        add("Rodriguez");
                        add("Farley");
                    }});
                }}));
        Mockito.when(userDAO.findUsersByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just(new User(){{
                    setId(1);
                    setUrl("http://initech.zendesk.com/api/v2/users/1.json");
                    setExternalId("74341f74-9c79-49d5-9611-87ef9b6eb75f");
                    setName("Francisca Rasmussen LLL");
                    setAlias("Miss Coffey");
                    setCreatedAt("2016-04-15T05:19:46 -10:00");
                    setActive(true);
                    setVerified(true);
                    setShared(false);
                    setLocale("en-AU");
                    setTimezone("Sri Lanka");
                    setLastLoginAt("2013-08-04T01:03:27 -10:00");
                    setEmail("coffeyrasmussen@flotonic.com");
                    setPhone("8335-422-718");
                    setSignature("Don't Worry Be Happy!");
                    setOrganizationId(101);
                    setTags(new ArrayList<String>(){{
                        add("Springville");
                        add("Sutton");
                        add("Hartsville/Hartley");
                        add("Diaperville");
                    }});
                    setSuspended(true);
                    setRole("admin");
                }}));
        Mockito.when(ticketDAO.findTicketsByCriteria(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Flux.just(new Ticket(){{
                    setId("436bf9b0-1147-4c0a-8439-6f79833bff5b");
                    setUrl("http://initech.zendesk.com/api/v2/tickets/436bf9b0-1147-4c0a-8439-6f79833bff5b.json");
                    setExternalId("9210cdc9-4bee-485f-a078-35396cd74063");
                    setCreatedAt("2016-04-28T11:19:34 -10:00");
                    setType("incident");
                    setSubject("A Catastrophe in Korea (North NNNN)");
                    setDescription("Nostrud ad sit velit cupidatat laboris ipsum nisi amet laboris ex exercitation " +
                            "amet et proident. Ipsum fugiat aute dolore tempor nostrud velit ipsum.");
                    setPriority("high");
                    setStatus("pending");
                    setSubmitterId(38);
                    setAssigneeId(24);
                    setOrganizationId(101);
                    setTags(new ArrayList<String>(){{
                        add("Ohio");
                        add("Pennsylvania");
                        add("American Samoa");
                        add("Northern Mariana Islands");
                    }});
                    setHasIncidents(false);
                    setDueAt("2016-07-31T02:37:50 -10:00");
                    setVia("web");
                }}));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void searchOrganization(){

        service.searchOrganizations("_id", "101");
        Mockito.verify(orgDAO,Mockito.times(1))
                .findOrganizationsByCriteria(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(userDAO,Mockito.times(1))
                .findUsersByCriteria(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(ticketDAO,Mockito.times(1))
                .findTicketsByCriteria(Mockito.anyString(),Mockito.anyString());
    }

    @Test
    void searchUser(){

        service.searchUsers("email", "rosannasimpson@flotonic.com");
        Mockito.verify(orgDAO,Mockito.times(1))
                .findOrganizationsByCriteria(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(userDAO,Mockito.times(1))
                .findUsersByCriteria(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(ticketDAO,Mockito.times(1))
                .findTicketsByCriteria(Mockito.anyString(),Mockito.anyString());
    }

    @Test
    void searchTicket(){

        service.searchTickets("_id", "1a227508-9f39-427c-8f57-1b72f3fab87c");
        Mockito.verify(orgDAO,Mockito.times(1))
                .findOrganizationsByCriteria(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(userDAO,Mockito.times(1))
                .findUsersByCriteria(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(ticketDAO,Mockito.times(1))
                .findTicketsByCriteria(Mockito.anyString(),Mockito.anyString());
    }
}