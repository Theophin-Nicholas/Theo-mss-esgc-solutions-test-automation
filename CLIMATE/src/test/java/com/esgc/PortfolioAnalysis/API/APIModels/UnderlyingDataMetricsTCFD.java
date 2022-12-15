package com.esgc.PortfolioAnalysis.API.APIModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class UnderlyingDataMetricsTCFD {
    @JsonProperty("title")
    private String title;
    @JsonProperty("data")
    private List<PortfolioCoverageWrapper> data;
    @JsonProperty("description")
    private String description;

}
