package com.esgc.APIModels.PortoflioAnalysisModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ESGPortfolioScore {
    @JsonProperty("category")
    private String category;
    @JsonProperty("name")
    private String name;
    @JsonProperty("weighted_avg")
    private Double weightedAvg;
}
