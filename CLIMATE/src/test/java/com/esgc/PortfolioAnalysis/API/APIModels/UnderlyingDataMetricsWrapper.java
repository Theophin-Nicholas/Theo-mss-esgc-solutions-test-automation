package com.esgc.PortfolioAnalysis.API.APIModels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data @JsonIgnoreProperties(ignoreUnknown = true)
public class UnderlyingDataMetricsWrapper {
//        @SerializedName(value="UnderlyingDataMetrics")
//        private UnderlyingDataMetrics UnderlyingDataMetrics;
    private List<List<Double>> data;
    @JsonProperty("data_export")
    private List<UnderlyingDataMetrics_DataExport> data_export;
    //@JsonIgnore
    @JsonProperty("description")
    private String description;
    private String title;
    @JsonIgnore
    private String criteria;
    @JsonIgnore
    private String type;
    @JsonProperty("min_carbon_footprint_scope_1_2_score")
    private int min_carbon_footprint_scope_1_2_score;
    @JsonProperty("max_carbon_footprint_scope_1_2_score")
    public int max_carbon_footprint_scope_1_2_score;

    }

