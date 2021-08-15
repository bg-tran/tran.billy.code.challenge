package tran.billy.code.challenge.dto;

import tran.billy.code.challenge.helper.StringHelper;

import java.util.Arrays;

public class Ticket {

    static public final String TERM_PREFIX = "ticket_";
    private String id;
    private String url;
    private String externalId;
    private String createdAt;
    private String type;
    private String subject;
    private String description;
    private String priority;
    private String status;
    private int submitterId;
    private int assigneeId;
    private int organizationId;
    private String[] tags;
    private boolean hasIncidents;
    private String dueAt;
    private String via;

    private Organization organization;
    private User submitter;
    private User assignee;

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

    public int getSubmitterId() {
        return submitterId;
    }

    public void setSubmitterId(int submitterId) {
        this.submitterId = submitterId;
    }

    public int getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(int assigneeId) {
        this.assigneeId = assigneeId;
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

    public boolean isHasIncidents() {
        return hasIncidents;
    }

    public void setHasIncidents(boolean hasIncidents) {
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

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
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

    @Override
    public String toString() {
        return subject;
    }

    public String print() {
        return
                StringHelper.addRightPadding("_id", StringHelper.WIDTH) + '\"' + id + "\"\n" +
                StringHelper.addRightPadding("url", StringHelper.WIDTH) + '\"' + url + "\"\n" +
                StringHelper.addRightPadding("external_id", StringHelper.WIDTH) + '\"' + externalId + "\"\n" +
                StringHelper.addRightPadding("created_at", StringHelper.WIDTH) + '\"' +  createdAt + "\"\n" +
                StringHelper.addRightPadding("type", StringHelper.WIDTH) + '\"' +  type + "\"\n" +
                StringHelper.addRightPadding("subject", StringHelper.WIDTH) + '\"' +  subject + "\"\n" +
                StringHelper.addRightPadding("description", StringHelper.WIDTH) + '\"' +  description + "\"\n" +
                StringHelper.addRightPadding("priority", StringHelper.WIDTH) + '\"' +  priority + "\"\n" +
                StringHelper.addRightPadding("status", StringHelper.WIDTH) + '\"' +  status + "\"\n" +
                StringHelper.addRightPadding("submitter_id", StringHelper.WIDTH) + submitterId +
                StringHelper.addRightPadding("assignee_id", StringHelper.WIDTH) + assigneeId +
                StringHelper.addRightPadding("organization_id", StringHelper.WIDTH) + organizationId +
                StringHelper.addRightPadding("tags", StringHelper.WIDTH) + Arrays.toString(tags) +
                StringHelper.addRightPadding("has_incidents", StringHelper.WIDTH) + hasIncidents +
                StringHelper.addRightPadding("due_at", StringHelper.WIDTH) + '\"' +  dueAt + "\"\n" +
                StringHelper.addRightPadding("via", StringHelper.WIDTH) + '\"' +  via + "\"\n" +
                StringHelper.addRightPadding("organization_name", StringHelper.WIDTH) + '\"' + organization + "\"\n" +
                StringHelper.addRightPadding("submitter_name", StringHelper.WIDTH) + '\"' + submitter + "\"\n" +
                StringHelper.addRightPadding("assignee_name", StringHelper.WIDTH) + '\"' + assignee + "\"\n";
    }
}
