package tran.billy.code.challenge.dao;

import java.util.Hashtable;
import java.util.List;
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
        buildCache(Organization.class);
    }

    /**
     * Find user by field in SEARCH_FIELDS
     * @param fieldName field name
     * @param fieldValue field value
     * @return a list of Organization
     */
    public List<Organization> findOrganizations(String fieldName, String fieldValue){

        return SEARCH_FIELDS.contains(fieldName) ? findByCriteria(SEARCH_FIELDS.get(fieldName), fieldValue) : null;
    }
}
