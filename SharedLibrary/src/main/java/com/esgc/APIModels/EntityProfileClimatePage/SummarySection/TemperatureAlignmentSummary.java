package com.esgc.APIModels.EntityProfileClimatePage.SummarySection;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TemperatureAlignmentSummary {
    @JsonProperty("temperature_alignment")
    public TemperatureAlignmentSummaryDetails climate;
}
