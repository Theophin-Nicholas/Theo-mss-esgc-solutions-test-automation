package com.esgc.APIModels.EntityProfilePageModels.SummarySection;

import lombok.Data;

@Data
public class EntityPageSummaryCarbonFootprint {

    private int carbon_footprint_value_total;
    private int emissions_scope_1;
    private int emissions_scope_2;
    private int emissions_scope_3;
    private String estimated;
    private String last_updated_date;
    private String score_category;

}

