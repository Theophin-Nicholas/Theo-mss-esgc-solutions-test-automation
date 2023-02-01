package com.esgc.Dashboard.API.APIModels;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HeatMapAxisData {
    @JsonProperty(value = "research_line_1_score_category")
    @JsonAlias({"research_line_2_score_category"})

    private String research_line_score_category;
    @JsonProperty(value = "research_line_1_score_range")
    @JsonAlias({"research_line_2_score_range"})

    private String research_line_score_range;

    @JsonProperty(value = "total_investment")
    private Double total_investment;
}
