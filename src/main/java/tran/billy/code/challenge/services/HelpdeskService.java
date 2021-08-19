package tran.billy.code.challenge.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tran.billy.code.challenge.config.AppConfig;
import tran.billy.code.challenge.dao.OrganizationDAO;
import tran.billy.code.challenge.dao.TicketDAO;
import tran.billy.code.challenge.dao.UserDAO;

import java.util.ArrayList;

/**
 * Helpdesk service to search users, tickets or organizations
 */
public class HelpdeskService {

    private final OrganizationDAO orgDAO;
    private final UserDAO userDAO;
    private final TicketDAO ticketDAO;


    public HelpdeskService() {
        this(new OrganizationDAO(AppConfig.get(AppConfig.ORG_DATA_STREAM_RESOURCE_PATH))
                ,new UserDAO(AppConfig.get(AppConfig.USER_DATA_STREAM_RESOURCE_PATH))
                ,new TicketDAO(AppConfig.get(AppConfig.TICKET_DATA_STREAM_RESOURCE_PATH)));
    }

    public HelpdeskService(OrganizationDAO orgDAO, UserDAO userDAO, TicketDAO ticketDAO) {
        this.orgDAO = orgDAO;
        this.userDAO = userDAO;
        this.ticketDAO = ticketDAO;
    }

    /**
     * Search organizations by field and print the result
     * @param searchTerm JSON field
     * @param searchValue filter value
     */
    public void searchOrganizations(String searchTerm, String searchValue){

        orgDAO.findOrganizationsByCriteria(searchTerm, searchValue)
                .flatMap(org -> Flux.zip(
                                        Mono.just(org),
                                        userDAO.findUsersByCriteria("organization_id", String.valueOf(org.getId()))
                                                .collectList()
                                                .switchIfEmpty(Mono.just(new ArrayList<>())),
                                        ticketDAO.findTicketsByCriteria("organization_id", String.valueOf(org.getId()))
                                                .collectList()
                                                .switchIfEmpty(Mono.just(new ArrayList<>()))
                                )
                                .map( t -> {
                                    t.getT1().addUsers(t.getT2());
                                    t.getT1().addTickets(t.getT3());
                                    return t.getT1();
                                })
                )
                .switchIfEmpty(
                        Mono.create(sink -> {
                                System.out.println("No search result");
                                sink.success();
                            }))
                .subscribe( org -> System.out.println(org.print()));

    }

    /**
     * Search users by field and print the result
     * @param searchTerm JSON field
     * @param searchValue filter value
     */
    public void searchUsers(String searchTerm, String searchValue){

        userDAO.findUsersByCriteria(searchTerm,searchValue)
                .flatMap(user -> Flux.zip(
                                        Mono.just(user),
                                        orgDAO.findOrganizationsByCriteria("_id", String.valueOf(user.getOrganizationId()))
                                                .collectList()
                                                .switchIfEmpty(Mono.just(new ArrayList<>())),
                                        ticketDAO.findTicketsByCriteria("submitter_id", String.valueOf(user.getId()))
                                                .collectList()
                                                .switchIfEmpty(Mono.just(new ArrayList<>()))
                                )
                                .map( t -> {
                                    if (t.getT2().size() > 0) {
                                        t.getT1().setOrganization(t.getT2().get(0));
                                    }
                                    t.getT1().addTickets(t.getT3());
                                    return t.getT1();
                                })
                )
                .switchIfEmpty(
                        Mono.create(sink -> {
                            System.out.println("No search result");
                            sink.success();
                        }))
                .subscribe( org -> System.out.println(org.print()));
    }

    /**
     * Search tickets by field and print the result
     * @param searchTerm JSON field
     * @param searchValue filter value
     */
    public void searchTickets(String searchTerm, String searchValue){

        ticketDAO.findTicketsByCriteria(searchTerm, searchValue)
                .flatMap(ticket -> Flux.zip(
                                        Mono.just(ticket),
                                        orgDAO.findOrganizationsByCriteria("_id", String.valueOf(ticket.getOrganizationId()))
                                                .collectList()
                                                .switchIfEmpty(Mono.just(new ArrayList<>())),
                                        userDAO.findUsersByCriteria("_id", String.valueOf(ticket.getSubmitterId()))
                                                .collectList()
                                                .switchIfEmpty(Mono.just(new ArrayList<>())),
                                        userDAO.findUsersByCriteria("_id", String.valueOf(ticket.getAssigneeId()))
                                                .collectList()
                                                .switchIfEmpty(Mono.just(new ArrayList<>()))
                                )
                                .map( t -> {
                                    if (t.getT2().size() > 0) {
                                        t.getT1().setOrganization(t.getT2().get(0));
                                    }
                                    if (t.getT3().size() > 0) {
                                        t.getT1().setSubmitter(t.getT3().get(0));
                                    }
                                    if (t.getT4().size() > 0) {
                                        t.getT1().setAssignee(t.getT4().get(0));
                                    }
                                    return t.getT1();
                                })
                )
                .switchIfEmpty(
                        Mono.create(sink -> {
                            System.out.println("No search result");
                            sink.success();
                        }))
                .subscribe( org -> System.out.println(org.print()));
    }

}
