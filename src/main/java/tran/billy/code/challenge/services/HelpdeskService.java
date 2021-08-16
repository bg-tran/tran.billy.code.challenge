package tran.billy.code.challenge.services;

import reactor.core.publisher.Mono;
import tran.billy.code.challenge.dao.OrganizationDAO;
import tran.billy.code.challenge.dao.TicketDAO;
import tran.billy.code.challenge.dao.UserDAO;
import tran.billy.code.challenge.dto.Ticket;
import tran.billy.code.challenge.dto.User;
import tran.billy.code.challenge.helper.StringHelper;


public class HelpdeskService {

    public static final String ORG_DATASOURCE_NAME = "organizations.json";
    public static final String USER_DATASOURCE_NAME = "users.json";
    public static final String TICKET_DATASOURCE_NAME = "tickets.json";

    private OrganizationDAO orgDAO;
    private UserDAO userDAO;
    private TicketDAO ticketDAO;


    public HelpdeskService() {

        String datasourcePath = System.getProperty("datasource_path");
        this.orgDAO = new OrganizationDAO(datasourcePath + "/" + ORG_DATASOURCE_NAME);
        this.userDAO = new UserDAO(datasourcePath + "/" + USER_DATASOURCE_NAME);
        this.ticketDAO = new TicketDAO(datasourcePath + "/" + TICKET_DATASOURCE_NAME);
    }

    public HelpdeskService(OrganizationDAO orgDAO, UserDAO userDAO, TicketDAO ticketDAO) {
        this.orgDAO = orgDAO;
        this.userDAO = userDAO;
        this.ticketDAO = ticketDAO;
    }

    public void searchOrganizations(String searchTerm, String searchValue){

        orgDAO.findOrganizationsByCriteria(searchTerm, searchValue)
                .subscribe( org -> {
                    System.out.print(org.print());
                    userDAO.findUsersByCriteria("organization_id", String.valueOf(org.getId()))
                            .index()
                            .flatMap(tuple2 -> {
                                System.out.print(StringHelper.printKeyValue(User.TERM_PREFIX + tuple2.getT1()
                                        ,tuple2.getT2().toString()));
                                return Mono.just(tuple2);
                            })
                            .subscribe();
                    ticketDAO.findTicketsByCriteria("organization_id", String.valueOf(org.getId()))
                            .index()
                            .flatMap(tuple2 -> {
                                System.out.print(StringHelper.printKeyValue(Ticket.TERM_PREFIX + tuple2.getT1()
                                        ,tuple2.getT2().toString()));
                                return Mono.just(tuple2);
                            })
                            .subscribe();
                });
    }

    public void searchUsers(String searchTerm, String searchValue){

        userDAO.findUsersByCriteria(searchTerm,searchValue)
                .subscribe(user -> {
                    System.out.print(user.print());
                    orgDAO.findOrganizationsByCriteria("_id", String.valueOf(user.getOrganizationId()))
                            .flatMap(org -> {
                                System.out.print(StringHelper.printKeyValue("organization_name", org.getName()));
                                return Mono.just(org);
                            }).subscribe();
                    ticketDAO.findTicketsByCriteria("submitter_id", String.valueOf(user.getId()))
                            .index()
                            .flatMap(tuple2 -> {
                                System.out.print(StringHelper.printKeyValue(Ticket.TERM_PREFIX + tuple2.getT1()
                                        ,tuple2.getT2().toString()));
                                return Mono.just(tuple2);
                            })
                            .subscribe();
                });
    }

    public void searchTickets(String searchTerm, String searchValue){

        ticketDAO.findTicketsByCriteria(searchTerm, searchValue)
                .subscribe(ticket -> {
                    System.out.print(ticket.print());
                    orgDAO.findOrganizationsByCriteria("_id", String.valueOf(ticket.getOrganizationId()))
                            .flatMap(org -> {
                                System.out.print(StringHelper.printKeyValue("organization_name", org.getName()));
                                return Mono.just(org);
                            }).subscribe();
                    userDAO.findUsersByCriteria("_id", String.valueOf(ticket.getSubmitterId()))
                            .flatMap(user -> {
                                System.out.print(StringHelper.printKeyValue("submitter_name", user.getName()));
                                return Mono.just(user);
                            }).subscribe();
                });
    }

}
