package tran.billy.code.challenge.dao;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tran.billy.code.challenge.dao.exception.EntityNotFoundException;
import tran.billy.code.challenge.dto.Organization;

public class OrganizationDAO extends GenericDAO{

    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationDAO.class);

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
    public List<Organization> findOrganizations(String fieldName, String fieldValue) {
        List<Organization> result = new ArrayList<>();
        if (ID.equals(SEARCH_FIELDS.get(fieldName))){
            try {
                result.add(super.findByID(Long.parseLong(fieldValue)));
            } catch (EntityNotFoundException | NumberFormatException e) {
                LOGGER.error(e.getMessage());
            }
            return result;
        }
        return SEARCH_FIELDS.get(fieldName) != null ? findByCriteria(SEARCH_FIELDS.get(fieldName), fieldValue) : result;
    }

    /**
     * Find Organization by ID
     * @param id id
     * @return nullable organization from cache
     */
    public Organization findByID(Long id) {

        Organization entity = null;
        try {
            entity = super.findByID(id);
        } catch (EntityNotFoundException | NullPointerException e) {
            LOGGER.error("id = " + id);
            LOGGER.error(e.getMessage());
        }

        return entity;
    }
}
