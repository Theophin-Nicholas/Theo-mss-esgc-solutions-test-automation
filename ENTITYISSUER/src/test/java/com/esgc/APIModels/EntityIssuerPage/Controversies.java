package com.esgc.APIModels.EntityIssuerPage;

import lombok.Data;

@Data
public class Controversies {
    private String controversy_description;
    private String controversy_events;
    private String controversy_location;
    private String controversy_responsiveness_comment;
    private String controversy_risk_mitigation;
    private String controversy_title;
    private String orbis_id;
    private String responsiveness;
    private String sector;
    private String severity;
    private String sources;
    private String title;
    private int total_controversies;
    private int total_critical_controversies;
}
