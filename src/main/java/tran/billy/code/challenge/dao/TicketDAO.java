package tran.billy.code.challenge.dao;

import java.util.Hashtable;
import java.util.List;

import tran.billy.code.challenge.dto.Ticket;


public class TicketDAO extends GenericDAO {

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

        return SEARCH_FIELDS.contains(fieldName) ? findByCriteria(SEARCH_FIELDS.get(fieldName), fieldValue) : null;
    }
}
