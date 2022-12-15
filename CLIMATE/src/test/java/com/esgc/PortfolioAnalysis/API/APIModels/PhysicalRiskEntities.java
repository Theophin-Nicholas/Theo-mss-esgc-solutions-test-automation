package com.esgc.PortfolioAnalysis.API.APIModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhysicalRiskEntities {

    @JsonProperty("name")
    private String risk_name;

    @JsonProperty("0-9")
    private List<Entities> riskEntities09;

    @JsonProperty("10-19")
    private List<Entities> riskEntities1019;

    @JsonProperty("20-29")
    private List<Entities> riskEntities2029;

    @JsonProperty("30-39")
    private List<Entities> riskEntities3039;

    @JsonProperty("40-49")
    private List<Entities> riskEntities4049;

    @JsonProperty("50-59")
    private List<Entities> riskEntities5059;

    @JsonProperty("60-69")
    private List<Entities> riskEntities6069;

    @JsonProperty("70-79")
    private List<Entities> riskEntities7079;

    @JsonProperty("80-89")
    private List<Entities> riskEntities8089;

    @JsonProperty("90-100")
    private List<Entities> riskEntities90100;
}
