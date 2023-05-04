package com.esgc.Dashboard.API.APIModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PortfolioSummaryCompanies {
    @JsonProperty
    private List<RegionSector> regionSector;
    @JsonProperty("Basic Materials")
    public BasicMaterials basicMaterials;
    @JsonProperty("Communication")
    public Communication communication;
    @JsonProperty("Consumer Discretionary")
    public ConsumerDiscretionary consumerDiscretionary;
    @JsonProperty("Consumer Staples")
    public ConsumerStaples consumerStaples;
    @JsonProperty("Energy")
    public Energy energy;
    @JsonProperty("Financials")
    public Financials financials;
    @JsonProperty("Health Care")
    public HealthCare healthCare;
    @JsonProperty("Industry")
    public Industry industry;
    @JsonProperty("Sovereign")
    public Sovereign sovereign;
    @JsonProperty("Technology")
    public Technology technology;
    @JsonProperty("Utilities")
    public Utilities utilities;

    @JsonProperty("Americas")
    public Americas americas;
    @JsonProperty("Asia Pacific")
    public AsiaPacific  asiaPacific;
    @JsonProperty ("Europe, Middle East & Africa")
    public EMEA emea;

}

