package com.esgc.EntityProfile.API.APIModels.SummarySection;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@Data
public class CarbonFootprintSummary {

@JsonProperty("carbon_footprint")

    public CarbonFootprintSummaryDetails carbon;
}
