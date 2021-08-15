package tran.billy.code.challenge.dto;

import tran.billy.code.challenge.helper.StringHelper;

import java.util.ArrayList;
import java.util.Arrays;

public class Organization {

    private int id;
    private String url;
    private String externalId;
    private String name;
    private String[] domainNames;
    private String createdAt;
    private String details;
    private boolean sharedTickets;
    private String[] tags;

    private ArrayList<Ticket> tickets;
    private ArrayList<User> users;

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

    public String[] getDomainNames() {
        return domainNames;
    }

    public void setDomainNames(String[] domainNames) {
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

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return name;
    }

    public String print() {
        return
                StringHelper.addRightPadding("_id", StringHelper.WIDTH) + id + "\n" +
                StringHelper.addRightPadding("url", StringHelper.WIDTH) + '\"' + url + "\"\n" +
                StringHelper.addRightPadding("external_id", StringHelper.WIDTH) + '\"' + externalId + "\"\n" +
                StringHelper.addRightPadding("name", StringHelper.WIDTH) + '\"' + name + "\"\n" +
                StringHelper.addRightPadding("domain_names", StringHelper.WIDTH) + '\"' + Arrays.toString(domainNames) + "\"\n" +
                StringHelper.addRightPadding("created_at", StringHelper.WIDTH) + createdAt + "\"\n" +
                StringHelper.addRightPadding("details", StringHelper.WIDTH) + details + "\"\n" +
                StringHelper.addRightPadding("shared_tickets", StringHelper.WIDTH) + sharedTickets + "\"\n" +
                StringHelper.addRightPadding("tags", StringHelper.WIDTH) + Arrays.toString(tags) + "\"\n" +
                StringHelper.printListAsString(tickets, Ticket.TERM_PREFIX) + "\"\n" +
                StringHelper.printListAsString(users, User.TERM_PREFIX)  + "\"\n";
    }
}
