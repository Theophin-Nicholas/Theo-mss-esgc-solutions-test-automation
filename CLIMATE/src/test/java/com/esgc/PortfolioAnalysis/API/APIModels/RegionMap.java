package com.esgc.PortfolioAnalysis.API.APIModels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * This class is used for Region Map API
 */
@Data
public class RegionMap {
    private String country_code;
    private double holdings;
    private int number_of_companies;
    private String region;
    private String region_name;
    private double score;
    private String score_range;
    private String score_category;
    private String country_name;
    @JsonIgnore
    private String score_msg;
}