package com.esgc.EntityProfile.API.APIModels.SummarySection;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BrownShareAndGreenShareClimateSummary {
    @JsonProperty("green_share")
    @JsonAlias({"brown_share"})
    public BrownShareAndGreenShareClimateSummaryDetails climate;
}
