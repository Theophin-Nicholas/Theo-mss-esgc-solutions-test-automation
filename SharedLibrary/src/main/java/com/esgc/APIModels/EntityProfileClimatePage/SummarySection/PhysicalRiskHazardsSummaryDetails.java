package com.esgc.APIModels.EntityProfileClimatePage.SummarySection;

import lombok.Data;

@Data
public class PhysicalRiskHazardsSummaryDetails {
    //@JsonProperty("physical_risk_hazard")
    public String highest_risk_hazard_category;
    public double highest_risk_hazard_fac_exp;
    public String hrh_risk_category;
    public String last_updated_date;
}
