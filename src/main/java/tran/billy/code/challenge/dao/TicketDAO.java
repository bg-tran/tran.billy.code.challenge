package tran.billy.code.challenge.dao;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tran.billy.code.challenge.dao.exception.EntityNotFoundException;
import tran.billy.code.challenge.dto.Ticket;



public class TicketDAO extends GenericDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketDAO.class);

    public static final Hashtable<String,String> SEARCH_FIELDS = new Hashtable<String,String>(){{
        put("_id","id");
        put("url","url");
        put("external_id","externalId");
        put("created_at","createdAt");
        put("type","type");
        put("subject","subject");
        put("description","description");
        put("priority","priority");
        put("submitter_id","submitterId");
        put("assignee_id","assigneeId");
        put("organization_id","organizationId");
        put("tags","tags");
        put("has_incidents","hasIncidents");
        put("due_at","dueAt");
        put("via","via");
    }};

    public TicketDAO(String dataSource) {
        super(dataSource);
        buildCache(Ticket.class);
    }

    /**
     * Find ticket by field in SEARCH_FIELDS
     * @param fieldName field name
     * @param fieldValue field value
     * @return a list of Ticket
     */
    public List<Ticket> findTickets(String fieldName, String fieldValue){

        List<Ticket> result = new ArrayList<>();
        if (ID.equals(SEARCH_FIELDS.get(fieldName))){
            try {
                result.add(findByID(fieldValue));
            } catch (EntityNotFoundException | NullPointerException e) {
                LOGGER.error(e.getMessage());
            }
            return result;
        }
        return SEARCH_FIELDS.get(fieldName) != null ? findByCriteria(SEARCH_FIELDS.get(fieldName), fieldValue) : result;
    }
}
