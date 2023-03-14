package com.esgc.APIModels.EMC;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SMEInfo {
    @JsonProperty("assessmentsInfo")
    private AssessmentsInfo assessmentsInfo;
}
