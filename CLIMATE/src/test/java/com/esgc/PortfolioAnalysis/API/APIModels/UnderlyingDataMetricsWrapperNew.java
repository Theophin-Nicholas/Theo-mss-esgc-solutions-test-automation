package com.esgc.PortfolioAnalysis.API.APIModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data @JsonIgnoreProperties(ignoreUnknown = true)
public class UnderlyingDataMetricsWrapperNew {

    /* @JsonProperty("criteria")
     private String criteria;

     private String description;

     private String title;
     @JsonProperty("type")
     private String type;

     private List<List<UnderlyingDataMetricsInnerObject>> data = new ArrayList<>();*/
    @JsonProperty("criteria")
    private String criteria;
    @JsonProperty("title")

    private String title;
    @JsonProperty("type")

    private String type;
    @JsonProperty("data")

    private List<List<UnderlyingDataMetricsInnerObject>> data;
    @JsonProperty("description")

    private String description;

}

