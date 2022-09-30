package com.esgc.APIModels.EntityControversies;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

public class ControversiesList {

    String controversy_description;
    String controversy_events;
    String controversy_location;
    String controversy_responsiveness_comment;
    String controversy_risk_mitigation;
    String controversy_title;
    String orbis_id;
    ArrayList<String> related_domain;
    String responsiveness;
    String sector;
    String severity;
    String sources;
    String title;
    int total_controversies;
    int total_critical_controversies;

    @JsonProperty("controversy_description")
    public String getControversy_description() {
        return this.controversy_description;
    }
    public void setControversy_description(String controversy_description) {
        this.controversy_description = controversy_description;
    }

    @JsonProperty("controversy_events")
    public String getControversy_events() {
        return this.controversy_events;
    }
    public void setControversy_events(String controversy_events) {
        this.controversy_events = controversy_events;
    }

    @JsonProperty("controversy_location")
    public String getControversy_location() {
        return this.controversy_location;
    }
    public void setControversy_location(String controversy_location) {
        this.controversy_location = controversy_location;
    }

    @JsonProperty("controversy_responsiveness_comment")
    public String getControversy_responsiveness_comment() {
        return this.controversy_responsiveness_comment;
    }
    public void setControversy_responsiveness_comment(String controversy_responsiveness_comment) {
        this.controversy_responsiveness_comment = controversy_responsiveness_comment;
    }

    @JsonProperty("controversy_risk_mitigation")
    public String getControversy_risk_mitigation() {
        return this.controversy_risk_mitigation;
    }
    public void setControversy_risk_mitigation(String controversy_risk_mitigation) {
        this.controversy_risk_mitigation = controversy_risk_mitigation;
    }

    @JsonProperty("controversy_title")
    public String getControversy_title() {
        return this.controversy_title;
    }
    public void setControversy_title(String controversy_title) {
        this.controversy_title = controversy_title;
    }

    @JsonProperty("orbis_id")
    public String getOrbis_id() {
        return this.orbis_id;
    }
    public void setOrbis_id(String orbis_id) {
        this.orbis_id = orbis_id;
    }

    @JsonProperty("related_domain")
    public ArrayList<String> getRelated_domain() {
        return this.related_domain;
    }
    public void setRelated_domain(ArrayList<String> related_domain) {
        this.related_domain = related_domain;
    }

    @JsonProperty("responsiveness")
    public String getResponsiveness() {
        return this.responsiveness;
    }
    public void setResponsiveness(String responsiveness) {
        this.responsiveness = responsiveness;
    }

    @JsonProperty("sector")
    public String getSector() {
        return this.sector;
    }
    public void setSector(String sector) {
        this.sector = sector;
    }

    @JsonProperty("severity")
    public String getSeverity() {
        return this.severity;
    }
    public void setSeverity(String severity) {
        this.severity = severity;
    }

    @JsonProperty("sources")
    public String getSources() {
        return this.sources;
    }
    public void setSources(String sources) {
        this.sources = sources;
    }

    @JsonProperty("title")
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("total_controversies")
    public int getTotal_controversies() {
        return this.total_controversies;
    }
    public void setTotal_controversies(int total_controversies) {
        this.total_controversies = total_controversies;
    }

    @JsonProperty("total_critical_controversies")
    public int getTotal_critical_controversies() {
        return this.total_critical_controversies;
    }
    public void setTotal_critical_controversies(int total_critical_controversies) {
        this.total_critical_controversies = total_critical_controversies;
    }
}
