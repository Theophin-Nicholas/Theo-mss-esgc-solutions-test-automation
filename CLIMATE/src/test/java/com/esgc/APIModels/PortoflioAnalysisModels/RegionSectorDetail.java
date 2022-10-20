package com.esgc.APIModels.PortoflioAnalysisModels;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * This class is used for Region and Sector drilldown details
 */
@Data
public class RegionSectorDetail {
    @JsonProperty(value = "No Risk")
    @JsonAlias({"Weak", "Moderate", "0%", "Major", "Well Below 2°C"})
    List<Company> category1;

    @JsonProperty(value = "Low Risk")
    @JsonAlias({"Limited", "Significant", "0-20%", "Below 2°C"})
    List<Company> category2;

    @JsonProperty(value = "Medium Risk")
    @JsonAlias({"Robust", "High", "20-100%", "Minor", "2°C"})
    List<Company> category3;

    @JsonProperty(value = "High Risk")
    @JsonAlias({"Advanced", "Intense", "None", "Above 2°C"})
    List<Company> category4;

    @JsonProperty(value = "Red Flag Risk")
    @JsonAlias({"No Info"})
    List<Company> category5;

    String name;
}