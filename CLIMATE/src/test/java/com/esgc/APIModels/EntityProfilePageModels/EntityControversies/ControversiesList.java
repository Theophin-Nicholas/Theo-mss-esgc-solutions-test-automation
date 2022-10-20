package com.esgc.APIModels.EntityProfilePageModels.EntityControversies;

import lombok.Data;

import java.util.ArrayList;

@Data
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
}
