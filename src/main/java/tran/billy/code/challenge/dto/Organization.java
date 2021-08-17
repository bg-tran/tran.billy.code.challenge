package tran.billy.code.challenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import tran.billy.code.challenge.helper.StringHelper;

import java.util.ArrayList;
import java.util.List;

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

    private ArrayList<User> users;

    private ArrayList<Ticket> tickets;

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

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUsers(List<User> users){

        this.users = new ArrayList<>(users);
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void addTickets(List<Ticket> tickets){

        this.tickets = new ArrayList<>(tickets);
    }

    @Override
    public String toString() {
        return name;
    }

    public String print() {
        return
                StringHelper.addRightPadding("_id", StringHelper.RIGHT_PADDING_WIDTH) + getId() + "\n" +
                StringHelper.addRightPadding("url", StringHelper.RIGHT_PADDING_WIDTH) +  getUrl() + "\n" +
                StringHelper.addRightPadding("external_id", StringHelper.RIGHT_PADDING_WIDTH) + getExternalId() + "\n" +
                StringHelper.addRightPadding("name", StringHelper.RIGHT_PADDING_WIDTH) + getName() + "\n" +
                StringHelper.addRightPadding("domain_names", StringHelper.RIGHT_PADDING_WIDTH)  + getDomainNames() + "\n" +
                StringHelper.addRightPadding("created_at", StringHelper.RIGHT_PADDING_WIDTH) + getCreatedAt() + "\n" +
                StringHelper.addRightPadding("details", StringHelper.RIGHT_PADDING_WIDTH) + getDetails() + "\n" +
                StringHelper.addRightPadding("shared_tickets", StringHelper.RIGHT_PADDING_WIDTH) + isSharedTickets() + "\n" +
                StringHelper.addRightPadding("tags", StringHelper.RIGHT_PADDING_WIDTH) +  getTags() + "\n" +
                StringHelper.printListAsString(Ticket.TERM_PREFIX, getTickets()) +
                StringHelper.printListAsString(User.TERM_PREFIX, getUsers())  + "\n";
    }
}
