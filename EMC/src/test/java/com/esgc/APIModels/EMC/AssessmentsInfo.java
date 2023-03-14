package com.esgc.APIModels.EMC;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AssessmentsInfo {
    @JsonProperty("purchasedAssessments")
    private Integer purchasedAssessments;
    @JsonProperty("usedAssessments")
    private Integer usedAssessments;
}
