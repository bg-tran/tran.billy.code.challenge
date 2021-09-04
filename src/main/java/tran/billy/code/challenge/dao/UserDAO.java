package tran.billy.code.challenge.dao;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tran.billy.code.challenge.dao.exception.EntityNotFoundException;

import tran.billy.code.challenge.dto.User;

public class UserDAO extends GenericDAO{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAO.class);

    public static final Hashtable<String,String> SEARCH_FIELDS = new Hashtable<String,String>(){{
        put("_id","id");
        put("url","url");
        put("external_id","externalId");
        put("name","name");
        put("alias","alias");
        put("created_at","createdAt");
        put("active","active");
        put("verified","verified");
        put("shared","shared");
        put("locale","locale");
        put("timezone","timezone");
        put("last_login_at","lastLoginAt");
        put("email","email");
        put("phone","phone");
        put("signature","signature");
        put("organization_id","organizationId");
        put("tags","tags");
        put("suspended","suspended");
        put("role","role");
    }};


    public UserDAO(String dataSource) {
        super(dataSource);
        buildCache(User.class);
    }

    /**
     * Find user by field in SEARCH_FIELDS
     * @param fieldName field name
     * @param fieldValue field value
     * @return a list of User
     */
    public List<User> findUsers(String fieldName, String fieldValue){
        List<User> result = new ArrayList<>();
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
     * Find User by ID
     * @param id id
     * @return nullable user from cache
     */
    public User findByID(Long id) {

        User entity = null;
        try {
            entity = super.findByID(id);
        } catch (EntityNotFoundException | NullPointerException e) {
            LOGGER.error("id = " + id);
            LOGGER.error(e.getMessage());
        }

        return entity;
    }
}
