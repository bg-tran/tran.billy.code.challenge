package tran.billy.code.challenge.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import tran.billy.code.challenge.helper.StringHelper;

import java.util.ArrayList;
import java.util.List;

public class User {

    static public final String TERM_PREFIX = "user_";

    @JsonProperty("_id")
    private Long id;

    @JsonProperty("url")
    private String url;

    @JsonProperty("external_id")
    private String externalId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("alias")
    private String alias;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("active")
    private Boolean active;

    @JsonProperty("verified")
    private Boolean verified;

    @JsonProperty("shared")
    private Boolean shared;

    @JsonProperty("locale")
    private String locale;

    @JsonProperty("timezone")
    private String timezone;

    @JsonProperty("last_login_at")
    private String lastLoginAt;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("signature")
    private String signature;

    @JsonProperty("organization_id")
    private Long organizationId;

    @JsonProperty("tags")
    private ArrayList<String> tags;

    @JsonProperty("suspended")
    private Boolean suspended;

    @JsonProperty("role")
    private String role;

    private Organization organization;

    private ArrayList<Ticket> tickets;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean isVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Boolean isShared() {
        return shared;
    }

    public void setShared(Boolean shared) {
        this.shared = shared;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(String lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public Boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(Boolean suspended) {
        this.suspended = suspended;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
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
                StringHelper.addRightPadding("url", StringHelper.RIGHT_PADDING_WIDTH) + getUrl() + "\n" +
                StringHelper.addRightPadding("name", StringHelper.RIGHT_PADDING_WIDTH) + getName() + "\n" +
                StringHelper.addRightPadding("alias", StringHelper.RIGHT_PADDING_WIDTH) + getAlias() + "\n" +
                StringHelper.addRightPadding("created_at", StringHelper.RIGHT_PADDING_WIDTH) + getCreatedAt() + "\n" +
                StringHelper.addRightPadding("active", StringHelper.RIGHT_PADDING_WIDTH) +  isActive() + "\n" +
                StringHelper.addRightPadding("verified", StringHelper.RIGHT_PADDING_WIDTH) + isVerified() + "\n" +
                StringHelper.addRightPadding("shared", StringHelper.RIGHT_PADDING_WIDTH) + isShared() + "\n" +
                StringHelper.addRightPadding("locale", StringHelper.RIGHT_PADDING_WIDTH) + getLocale() + "\n" +
                StringHelper.addRightPadding("timezone", StringHelper.RIGHT_PADDING_WIDTH) + getTimezone() + "\n" +
                StringHelper.addRightPadding("lastLoginAt", StringHelper.RIGHT_PADDING_WIDTH) + getLastLoginAt() + "\n" +
                StringHelper.addRightPadding("email", StringHelper.RIGHT_PADDING_WIDTH) + getEmail() + "\n" +
                StringHelper.addRightPadding("phone", StringHelper.RIGHT_PADDING_WIDTH) + getPhone() + "\n" +
                StringHelper.addRightPadding("signature", StringHelper.RIGHT_PADDING_WIDTH) + getSignature() + "\n" +
                StringHelper.addRightPadding("organization_id", StringHelper.RIGHT_PADDING_WIDTH) + getOrganizationId() + "\n" +
                StringHelper.addRightPadding("tags", StringHelper.RIGHT_PADDING_WIDTH) + getTags() + "\n" +
                StringHelper.addRightPadding("suspended", StringHelper.RIGHT_PADDING_WIDTH) + isSuspended() + "\n" +
                StringHelper.addRightPadding("role", StringHelper.RIGHT_PADDING_WIDTH) + getRole() + "\n" +
                StringHelper.addRightPadding("organization_name", StringHelper.RIGHT_PADDING_WIDTH) + organization + "\n" +
                StringHelper.printListAsString(Ticket.TERM_PREFIX, getTickets()) + "\n";
    }
}
