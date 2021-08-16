package tran.billy.code.challenge.dao;

import reactor.core.publisher.Flux;
import java.util.Hashtable;
import tran.billy.code.challenge.dto.Organization;

public class OrganizationDAO extends GenericDAO{

    public static final Hashtable<String,String> SEARCH_FIELDS = new Hashtable<String,String>(){{
        put("_id","id");
        put("url","url");
        put("external_id","externalId");
        put("name","name");
        put("domain_names","domainNames");
        put("created_at","createdAt");
        put("details","details");
        put("shared_tickets","sharedTickets");
        put("tags","tags");
    }};

    public OrganizationDAO(String dataSource) {
        super(dataSource);
    }

    public Flux<Organization> searchOrganizationByCriteria(String fieldName, String fieldValue){

        return searchByCriteria(SEARCH_FIELDS.get(fieldName), fieldValue, Organization.class);
    }

}
