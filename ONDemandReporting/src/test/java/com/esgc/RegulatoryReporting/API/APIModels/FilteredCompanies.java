package com.esgc.RegulatoryReporting.API.APIModels;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data

public class FilteredCompanies {
    public Info info;
    public ArrayList<Entity> entities;
    public ArrayList<SmeStatus> sme_statuses;
}

class Entity{
    public String bvd9_number;
    public String company_name;
    public String country_code;
    public String country_name;
    public int current_investment;
    public double investment_pct;
    public String region_code;
    public String region_name;
    public int score;
    public String sector;
    public String sector_code;
    public String state;
    public String type;
    public int actual_size;
    public String size;
}

class Info{
    public int number_of_predicted;
    public int remaining_assessments;
}

class SmeStatus{
    public String id;
    public String entityId;
    @JsonProperty("ISIN")
    public String iSIN;
    public String companyName;
    public String sectorCode;
    public String companySizeName;
    public String countryCode;
    public boolean dataAlliance;
    public int evaluationYear;
    public String type;
    public String state;
    public String entityState;
}
