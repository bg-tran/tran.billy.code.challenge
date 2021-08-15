package tran.billy.code.challenge.dao;

import reactor.core.publisher.Flux;
import tran.billy.code.challenge.dto.Organization;
import tran.billy.code.challenge.helper.DaoHelper;
import tran.billy.code.challenge.helper.JsonHelper;


import java.util.Hashtable;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class OrganizationDAO {

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

    private String dataSource = "input/organizations.json";

    public OrganizationDAO(String dataSource) {
        this.dataSource = dataSource;
    }

    public List<Organization> searchOrganizationByCriteria(String fieldName, String fieldValue){

        Predicate<Organization> predicate = DaoHelper.createCriteria(SEARCH_FIELDS.get(fieldName), fieldValue);
        Flux<Organization> organizationFlux = JsonHelper.readJSONFile(dataSource, Organization.class);

        return organizationFlux.filter(predicate)
                .collect(Collectors.<Organization>toList())
                .block();
    }

}
