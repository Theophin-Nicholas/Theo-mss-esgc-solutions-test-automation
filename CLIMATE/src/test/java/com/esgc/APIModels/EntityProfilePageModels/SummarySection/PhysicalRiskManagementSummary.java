package com.esgc.APIModels.EntityProfilePageModels.SummarySection;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PhysicalRiskManagementSummary {
    @JsonProperty("physical_risk_mgmt")
    public PhysicalRiskManagementSummary physical_risk_mgmt;
}
