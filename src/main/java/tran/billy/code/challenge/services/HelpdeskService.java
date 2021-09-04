package tran.billy.code.challenge.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tran.billy.code.challenge.config.AppConfig;
import tran.billy.code.challenge.dao.OrganizationDAO;
import tran.billy.code.challenge.dao.TicketDAO;
import tran.billy.code.challenge.dao.UserDAO;
import tran.billy.code.challenge.dto.Organization;
import tran.billy.code.challenge.dto.Ticket;
import tran.billy.code.challenge.dto.User;
import java.io.IOException;
import java.util.List;

/**
 * Helpdesk service to search users, tickets or organizations
 */
public class HelpdeskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelpdeskService.class);

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

        List<Organization> orgs = orgDAO.findOrganizations(searchTerm,searchValue);
        if (orgs == null || orgs.size() == 0){
            System.out.println("No search result");

        } else {
            orgs.forEach(org -> {
                org.addUsers(userDAO.findUsers("organization_id",org.getId().toString()));
                org.addTickets(ticketDAO.findTickets("organization_id",org.getId().toString()));
                System.out.println(org.print());
                System.out.println("Press enter/return to continue");
                try {
                    System.in.read();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                }
            });
        }

    }

    /**
     * Search users by field and print the result
     * @param searchTerm JSON field
     * @param searchValue filter value
     */
    public void searchUsers(String searchTerm, String searchValue){

        List<User> users = userDAO.findUsers(searchTerm,searchValue);
        if (users == null || users.size() == 0){
            System.out.println("No search result");

        } else {
            users.forEach(user -> {

                user.setOrganization(orgDAO.findByID(user.getOrganizationId()));
                user.addTickets(ticketDAO.findTickets("submitter_id", user.getId().toString()));
                System.out.println(user.print());
                System.out.println("Press enter/return to continue");
                try {
                    System.in.read();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                }
            });
        }

    }

    /**
     * Search tickets by field and print the result
     * @param searchTerm JSON field
     * @param searchValue filter value
     */
    public void searchTickets(String searchTerm, String searchValue){


        List<Ticket> tickets = ticketDAO.findTickets(searchTerm,searchValue);
        if (tickets == null || tickets.size() == 0){
            System.out.println("No search result");

        } else {
            tickets.forEach(ticket -> {

                ticket.setOrganization(orgDAO.findByID(ticket.getOrganizationId()));
                ticket.setSubmitter(userDAO.findByID(ticket.getSubmitterId()));
                ticket.setAssignee(userDAO.findByID(ticket.getAssigneeId()));
                System.out.println(ticket.print());
                System.out.println("Press enter/return to continue");
                try {
                    System.in.read();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                }
            });
        }
    }

}
