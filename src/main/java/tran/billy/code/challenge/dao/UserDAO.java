package tran.billy.code.challenge.dao;

import java.util.Hashtable;
import java.util.List;

import tran.billy.code.challenge.dto.User;

public class UserDAO extends GenericDAO{

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

        return SEARCH_FIELDS.contains(fieldName) ? findByCriteria(SEARCH_FIELDS.get(fieldName), fieldValue) : null;
    }
}
