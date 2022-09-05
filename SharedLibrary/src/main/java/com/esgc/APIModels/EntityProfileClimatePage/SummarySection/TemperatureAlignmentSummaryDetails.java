package com.esgc.APIModels.EntityProfileClimatePage.SummarySection;

import lombok.Data;

@Data
public class TemperatureAlignmentSummaryDetails {

    public String emissions_reduction_target_year;
    public double implied_temperature_rise;
    public String last_updated_date;
    public String score_category;
    public String target_description;
    public String implied_temperature_rise_msg;
}
