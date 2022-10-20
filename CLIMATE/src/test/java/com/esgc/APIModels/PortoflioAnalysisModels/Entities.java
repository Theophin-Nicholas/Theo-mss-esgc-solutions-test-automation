package com.esgc.APIModels.PortoflioAnalysisModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Entities {

    @JsonProperty("company_name")
    private String company_name;

    @JsonProperty("investment_pct")
    private double investment_pct;

    @JsonProperty("orbis_id")
    private String orbis_id;

    @JsonProperty("score")
    private int score;

}
