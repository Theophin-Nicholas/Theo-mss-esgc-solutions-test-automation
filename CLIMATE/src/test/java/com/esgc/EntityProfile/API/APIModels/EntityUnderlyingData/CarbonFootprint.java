package com.esgc.EntityProfile.API.APIModels.EntityUnderlyingData;

import lombok.Data;

@Data
public class CarbonFootprint{
    String bvd9_number;
    String carbon_footprint_reporting_year;
    int carbon_footprint_value_total;
    double carbon_intensity;
    int emissions_scope_1;
    int emissions_scope_2;
    int emissions_scope_3;
    String estimated;
    String last_updated_date;
    int sales;
    String score_category;
    String source;
    String third_party_verification;
}

