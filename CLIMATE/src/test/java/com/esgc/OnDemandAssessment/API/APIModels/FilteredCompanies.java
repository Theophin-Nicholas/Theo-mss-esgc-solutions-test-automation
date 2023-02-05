package com.esgc.OnDemandAssessment.API.APIModels;


import com.esgc.EntityProfile.API.APIModels.EntityHeaderSubsidiaryDetails;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
    public int number_of_companies;
    public String region_code;
    public String region_name;
    public String score;
    public String sector;
    public int actual_size;
    public String sector_code;
    public String size;
    public String state;
    public String type;
}

class Info{
    public double predicted_pct;
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
    public Object companySizeName;
    public String countryCode;
    public Object dataAlliance;
    public Object evaluationYear;
    public String type;
    public String state;
}
