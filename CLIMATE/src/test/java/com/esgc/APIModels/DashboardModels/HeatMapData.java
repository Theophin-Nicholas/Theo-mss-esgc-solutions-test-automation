package com.esgc.APIModels.DashboardModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class HeatMapData {
    private Integer companies;
    private Double perc_investment;
    private String research_line_1_score_category;
    private String research_line_1_score_range;
    @JsonProperty(value = "research_line_2_score_category")
    private String research_line_2_score_category;
    @JsonProperty(value = "research_line_2_score_range")
    private String research_line_2_score_range;
}
