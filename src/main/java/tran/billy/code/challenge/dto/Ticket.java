package tran.billy.code.challenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import tran.billy.code.challenge.helper.StringHelper;

import java.util.ArrayList;

public class Ticket {

    static public final String TERM_PREFIX = "ticket_";

    @JsonProperty("_id")
    private String id;

    @JsonProperty("url")
    private String url;

    @JsonProperty("external_id")
    private String externalId;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("type")
    private String type;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("description")
    private String description;

    @JsonProperty("priority")
    private String priority;

    @JsonProperty("status")
    private String status;

    @JsonProperty("submitter_id")
    private Long submitterId;

    @JsonProperty("assignee_id")
    private Long assigneeId;

    @JsonProperty("organization_id")
    private Long organizationId;

    @JsonProperty("tags")
    private ArrayList<String> tags;

    @JsonProperty("has_incidents")
    private Boolean hasIncidents;

    @JsonProperty("due_at")
    private String dueAt;

    @JsonProperty("via")
    private String via;

    private User submitter;

    private User assignee;

    private Organization organization;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getSubmitterId() {
        return submitterId;
    }

    public void setSubmitterId(Long submitterId) {
        this.submitterId = submitterId;
    }

    public Long getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(Long assigneeId) {
        this.assigneeId = assigneeId;
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

    public Boolean isHasIncidents() {
        return hasIncidents;
    }

    public void setHasIncidents(Boolean hasIncidents) {
        this.hasIncidents = hasIncidents;
    }

    public String getDueAt() {
        return dueAt;
    }

    public void setDueAt(String dueAt) {
        this.dueAt = dueAt;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public User getSubmitter() {
        return submitter;
    }

    public void setSubmitter(User submitter) {
        this.submitter = submitter;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return subject;
    }

    public String print() {
        return
                StringHelper.addRightPadding("_id", StringHelper.RIGHT_PADDING_WIDTH) + getId() + "\n" +
                StringHelper.addRightPadding("url", StringHelper.RIGHT_PADDING_WIDTH) + getUrl() + "\n" +
                StringHelper.addRightPadding("external_id", StringHelper.RIGHT_PADDING_WIDTH) + getExternalId() + "\n" +
                StringHelper.addRightPadding("created_at", StringHelper.RIGHT_PADDING_WIDTH) + getCreatedAt() + "\n" +
                StringHelper.addRightPadding("type", StringHelper.RIGHT_PADDING_WIDTH) + getType() + "\n" +
                StringHelper.addRightPadding("subject", StringHelper.RIGHT_PADDING_WIDTH) + getSubject() + "\n" +
                StringHelper.addRightPadding("description", StringHelper.RIGHT_PADDING_WIDTH) +  getDescription() + "\n" +
                StringHelper.addRightPadding("priority", StringHelper.RIGHT_PADDING_WIDTH) + getPriority() + "\n" +
                StringHelper.addRightPadding("status", StringHelper.RIGHT_PADDING_WIDTH) + getStatus() + "\n" +
                StringHelper.addRightPadding("submitter_id", StringHelper.RIGHT_PADDING_WIDTH) + getSubmitterId() + "\n" +
                StringHelper.addRightPadding("assignee_id", StringHelper.RIGHT_PADDING_WIDTH) + getAssigneeId() + "\n" +
                StringHelper.addRightPadding("organization_id", StringHelper.RIGHT_PADDING_WIDTH) + getOrganizationId() + "\n" +
                StringHelper.addRightPadding("tags", StringHelper.RIGHT_PADDING_WIDTH) + getTags() + "\n" +
                StringHelper.addRightPadding("has_incidents", StringHelper.RIGHT_PADDING_WIDTH) + isHasIncidents() + "\n" +
                StringHelper.addRightPadding("due_at", StringHelper.RIGHT_PADDING_WIDTH) + getDueAt() + "\n" +
                StringHelper.addRightPadding("via", StringHelper.RIGHT_PADDING_WIDTH) +  via + "\n" +
                StringHelper.addRightPadding("organization_name", StringHelper.RIGHT_PADDING_WIDTH) + organization + "\n" +
                StringHelper.addRightPadding("submitter_name", StringHelper.RIGHT_PADDING_WIDTH) + submitter + "\n" +
                StringHelper.addRightPadding("assignee_name", StringHelper.RIGHT_PADDING_WIDTH) + assignee + "\n";
    }
}
