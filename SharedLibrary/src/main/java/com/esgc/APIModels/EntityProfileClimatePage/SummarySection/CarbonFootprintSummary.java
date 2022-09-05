package com.esgc.APIModels.EntityProfileClimatePage.SummarySection;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@Data
public class CarbonFootprintSummary {

@JsonProperty("carbon_footprint")

    public CarbonFootprintSummaryDetails carbon;
}
