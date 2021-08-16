package tran.billy.code.challenge.dao;

import reactor.core.publisher.Flux;
import java.util.Hashtable;
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
    }

    /**
     * Find ticket by field in SEARCH_FIELDS
     * @param fieldName
     * @param fieldValue
     * @return a stream of Ticket
     */
    public Flux<Ticket> findTicketsByCriteria(String fieldName, String fieldValue){

       return findByCriteria(SEARCH_FIELDS.get(fieldName), fieldValue, Ticket.class);
    }
}
