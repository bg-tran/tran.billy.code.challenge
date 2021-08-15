package tran.billy.code.challenge.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import tran.billy.code.challenge.helper.StringHelper;

import java.util.ArrayList;
import java.util.Arrays;

public class User {

    static public final String TERM_PREFIX = "user_";

    @JsonProperty("_id")
    private int id;

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
    private boolean active;

    @JsonProperty("verified")
    private boolean verified;

    @JsonProperty("shared")
    private boolean shared;

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
    private int organizationId;

    @JsonProperty("tags")
    private String[] tags;

    @JsonProperty("suspended")
    private boolean suspended;

    @JsonProperty("role")
    private String role;

    private Organization organization;

    private ArrayList<Object> tickets;

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
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

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
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

    public ArrayList<Object> getTickets() {
        return tickets;
    }

    public void setTickets(ArrayList<Object> tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return name;
    }

    public String print() {
        return
                StringHelper.addRightPadding("_id", StringHelper.WIDTH) + id + "\n" +
                StringHelper.addRightPadding("url", StringHelper.WIDTH) + '\"' + url + "\"\n" +
                StringHelper.addRightPadding("name", StringHelper.WIDTH) + '\"' + name + "\"\n" +
                StringHelper.addRightPadding("alias", StringHelper.WIDTH) + '\"' +  alias + "\"\n" +
                StringHelper.addRightPadding("created_at", StringHelper.WIDTH) + '\"' +  createdAt + "\"\n" +
                StringHelper.addRightPadding("active", StringHelper.WIDTH) +  active + "\n" +
                StringHelper.addRightPadding("verified", StringHelper.WIDTH) + verified + "\n" +
                StringHelper.addRightPadding("shared", StringHelper.WIDTH) + shared + "\n" +
                StringHelper.addRightPadding("locale", StringHelper.WIDTH) + '\"' + locale + "\"\n" +
                StringHelper.addRightPadding("timezone", StringHelper.WIDTH) + '\"' + timezone + "\"\n" +
                StringHelper.addRightPadding("lastLoginAt", StringHelper.WIDTH) + '\"' + lastLoginAt + "\"\n" +
                StringHelper.addRightPadding("email", StringHelper.WIDTH) + '\"' + email + "\"\n" +
                StringHelper.addRightPadding("phone", StringHelper.WIDTH) + '\"' + phone + "\"\n" +
                StringHelper.addRightPadding("signature", StringHelper.WIDTH) + '\"' + signature + "\"\n" +
                StringHelper.addRightPadding("organization_id", StringHelper.WIDTH) + organizationId + "\n" +
                StringHelper.addRightPadding("tags", StringHelper.WIDTH) + Arrays.toString(tags) + "\"\n" +
                StringHelper.addRightPadding("suspended", StringHelper.WIDTH) + suspended + "\n" +
                StringHelper.addRightPadding("role", StringHelper.WIDTH) + '\"' + role + "\"\n" +
                StringHelper.addRightPadding("organization_name", StringHelper.WIDTH) + '\"' + organization + "\"\n" +
                StringHelper.printListAsString(tickets,"ticket") + "\"\n";

    }
}
