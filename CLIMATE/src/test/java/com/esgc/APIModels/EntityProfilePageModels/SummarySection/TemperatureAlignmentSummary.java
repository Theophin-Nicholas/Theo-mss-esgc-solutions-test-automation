package com.esgc.APIModels.EntityProfilePageModels.SummarySection;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TemperatureAlignmentSummary {
    @JsonProperty("temperature_alignment")
    public TemperatureAlignmentSummaryDetails climate;

    public TemperatureAlignmentSummaryDetails getClimate() {
        return climate;
    }
}
