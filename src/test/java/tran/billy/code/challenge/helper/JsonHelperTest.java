package tran.billy.code.challenge.helper;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import tran.billy.code.challenge.dto.Organization;
import tran.billy.code.challenge.dto.Ticket;
import tran.billy.code.challenge.dto.User;

public class JsonHelperTest {

    @Test
    void testReadOrganizationFile(){

        Flux<Organization> organizationFlux = JsonHelper.readJSONFile("src/test/organizations.json", Organization.class);
        StepVerifier
                .create(organizationFlux)
                .expectNextMatches(organization -> organization.getId() == 101
                        && organization.getName().equals("Enthaze"))
                .expectNextMatches(organization -> organization.getId() == 102
                        && organization.getName().equals("Nutralab"))
                .expectComplete()
                .verify();
    }

    @Test
    void testReadUserFile(){

        Flux<User> userFlux = JsonHelper.readJSONFile("src/test/users.json", User.class);
        StepVerifier
                .create(userFlux)
                .expectNextMatches(user -> user.getId() == 1
                        && user.getAlias().equals("Miss Coffey")
                        && user.getRole().equals("admin"))
                .expectNextMatches(user -> user.getId() == 3
                        && user.getAlias().equals("Miss Buck")
                        && user.getRole().equals("end-user"))
                .expectNextMatches(user -> user.getId() == 75
                        && user.getAlias().equals("Miss Rosanna")
                        && user.getRole().equals("agent"))
                .expectComplete()
                .verify();
    }

    @Test
    void testReadTicketFile(){

        Flux<Ticket> ticketFlux = JsonHelper.readJSONFile("src/test/tickets.json", Ticket.class);
        StepVerifier
                .create(ticketFlux)
                .expectNextMatches(ticket -> ticket.getId().equals("436bf9b0-1147-4c0a-8439-6f79833bff5b")
                        && ticket.getSubject().equals("A Catastrophe in Korea (North)"))
                .expectNextMatches(ticket -> ticket.getId().equals("1a227508-9f39-427c-8f57-1b72f3fab87c")
                        && ticket.getSubject().equals("A Catastrophe in Micronesia"))
                .expectComplete()
                .verify();
    }
}