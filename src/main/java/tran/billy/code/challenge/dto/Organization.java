package tran.billy.code.challenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import tran.billy.code.challenge.helper.StringHelper;

import java.util.ArrayList;

public class Organization {

    @JsonProperty("_id")
    private int id;

    @JsonProperty("url")
    private String url;

    @JsonProperty("external_id")
    private String externalId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("domain_names")
    private ArrayList<String> domainNames;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("details")
    private String details;

    @JsonProperty("shared_tickets")
    private boolean sharedTickets;

    @JsonProperty("tags")
    private ArrayList<String> tags;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getDomainNames() {
        return domainNames;
    }

    public void setDomainNames(ArrayList<String> domainNames) {
        this.domainNames = domainNames;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public boolean isSharedTickets() {
        return sharedTickets;
    }

    public void setSharedTickets(boolean sharedTickets) {
        this.sharedTickets = sharedTickets;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }


    @Override
    public String toString() {
        return name;
    }

    public String print() {
        return
                StringHelper.addRightPadding("_id", StringHelper.WIDTH) + getId() + "\n" +
                StringHelper.addRightPadding("url", StringHelper.WIDTH) +  getUrl() + "\n" +
                StringHelper.addRightPadding("external_id", StringHelper.WIDTH) + getExternalId() + "\n" +
                StringHelper.addRightPadding("name", StringHelper.WIDTH) + getName() + "\n" +
                StringHelper.addRightPadding("domain_names", StringHelper.WIDTH)  + getDomainNames() + "\n" +
                StringHelper.addRightPadding("created_at", StringHelper.WIDTH) + getCreatedAt() + "\n" +
                StringHelper.addRightPadding("details", StringHelper.WIDTH) + getDetails() + "\n" +
                StringHelper.addRightPadding("shared_tickets", StringHelper.WIDTH) + isSharedTickets() + "\n" +
                StringHelper.addRightPadding("tags", StringHelper.WIDTH) +  getTags() + "\n" ;
    }
}
