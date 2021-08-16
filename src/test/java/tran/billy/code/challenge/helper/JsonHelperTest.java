package tran.billy.code.challenge.helper;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import tran.billy.code.challenge.dto.Organization;
import tran.billy.code.challenge.dto.Ticket;
import tran.billy.code.challenge.dto.User;

import java.io.IOException;

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
                .expectNextMatches(organization -> organization.getId() == 119
                        && organization.getName().equals("Multron"))
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
                .expectNextMatches(user -> user.getId() == 71
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

    @Test
    void testInvalidFile() {

        StepVerifier
                .create(JsonHelper.readJSONFile("src/test/invalid.json", Object.class))
                .expectErrorMatches(throwable -> throwable instanceof IllegalStateException &&
                        throwable.getMessage().equals("Expected content to be an array"))
                .verify();

        StepVerifier
                .create(JsonHelper.readJSONFile("src/test/abc.json", Object.class))
                .expectErrorMatches(throwable -> throwable instanceof IOException)
                .verify();

        StepVerifier
                .create(JsonHelper.readJSONFile("src/test/invalid2.json", User.class))
                .expectErrorMatches(throwable -> throwable instanceof UnrecognizedPropertyException)
                .verify();
    }

}
