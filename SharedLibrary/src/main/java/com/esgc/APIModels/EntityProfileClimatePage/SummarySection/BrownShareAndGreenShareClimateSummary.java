package com.esgc.APIModels.EntityProfileClimatePage.SummarySection;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BrownShareAndGreenShareClimateSummary {
    @JsonProperty("green_share")
    @JsonAlias({"brown_share"})
    public BrownShareAndGreenShareClimateSummaryDetails climate;
}
