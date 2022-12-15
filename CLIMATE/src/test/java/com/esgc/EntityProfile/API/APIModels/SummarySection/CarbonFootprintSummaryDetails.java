package com.esgc.EntityProfile.API.APIModels.SummarySection;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;


@Data
public class CarbonFootprintSummaryDetails {

    public int carbon_footprint_value_total;
    public int emissions_scope_1;
    public int emissions_scope_2;
    public int emissions_scope_3;
    public String estimated;
    public String score_category;
    public String last_updated_date;
    @JsonIgnore
    public String emissions_scope_1_msg;
    @JsonIgnore
    public String emissions_scope_2_msg;
    @JsonIgnore
    public String emissions_scope_3_msg;

}
