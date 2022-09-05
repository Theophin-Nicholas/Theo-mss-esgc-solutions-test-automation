package com.esgc.APIModels.EntityProfileClimatePage.SummarySection;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * This class is used for Entity Climate Profile | Physical Climate Hazards
 */
@Data
public class PhysicalRiskHazardsSummary {
@JsonProperty("physical_risk_hazard")
    public PhysicalRiskHazardsSummaryDetails physical_risk_hazard;


}
