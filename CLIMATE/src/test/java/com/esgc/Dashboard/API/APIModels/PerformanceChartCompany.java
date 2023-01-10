package com.esgc.Dashboard.API.APIModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data @JsonIgnoreProperties(ignoreUnknown = true)
public class PerformanceChartCompany {
    /**
     * {
     *     "BVD9_NUMBER": "000014662",
     *     "COMPANY_NAME": "Air Products & Chemicals, Inc.",
     *     "CURR_SCORE_BROWN_SHARE": 0,
     *     "CURR_SCORE_CARBON_FOOTPRINT": 27200000,
     *     "CURR_SCORE_ENERGY_TRANSITION": 50,
     *     "CURR_SCORE_GREEN_SHARE": 5,
     *     "CURR_SCORE_MARKET_RISK": 76,
     *     "CURR_SCORE_OPERATIONS_RISK": 86,
     *     "CURR_SCORE_PHYSICAL_RISK_MANAGEMENT": 22,
     *     "CURR_SCORE_SUPPLY_CHAIN_RISK": 87,
     *     "CURR_SCORE_TCFD_STRATEGY": 46,
     *     "PERCENT_HOLDINGS": 0.61,
     *     "PREV_SCORE_BROWN_SHARE": 0,
     *     "PREV_SCORE_GREEN_SHARE": 5,
     *     "PREV_SCORE_MARKET_RISK": 0,
     *     "PREV_SCORE_OPERATIONS_RISK": 0,
     *     "PREV_SCORE_SUPPLY_CHAIN_RISK": 0,
     *     "SCORE_CATEGORY_BROWN_SHARE": "0%",
     *     "SCORE_CATEGORY_CARBON_FOOTPRINT": "Intense",
     *     "SCORE_CATEGORY_ENERGY_TRANSITION": "Robust",
     *     "SCORE_CATEGORY_GREEN_SHARE": "Minor",
     *     "SCORE_CATEGORY_MARKET_RISK": "High Risk",
     *     "SCORE_CATEGORY_OPERATIONS_RISK": "Red Flag Risk",
     *     "SCORE_CATEGORY_PHYSICAL_RISK_MANAGEMENT": "Weak",
     *     "SCORE_CATEGORY_SUPPLY_CHAIN_RISK": "Red Flag Risk",
     *     "SCORE_CATEGORY_TCFD_STRATEGY": "Limited",
     *     "SECTOR": "Basic Materials"
     * }
     */

    /**
     *
     * {
     *         "BVD9_NUMBER": "001691226",
     *         "COMPANY_NAME": "Jefferies Financial Group, Inc.",
     *         "CURR_SCORE_BROWN_SHARE": 5,
     *         "CURR_SCORE_CARBON_FOOTPRINT": 442582,
     *         "CURR_SCORE_GREEN_SHARE": 0,
     *         "PERCENT_HOLDINGS": 0,
     *         "PHYSICAL_RISK_HAZARD_CATEGORY_NAME": "Water Stress",
     *         "PHYSICAL_RISK_HAZARD_PERCENT_FACILITIES_EXPOSED": 29,
     *         "PREV_SCORE_BROWN_SHARE": 5,
     *         "PREV_SCORE_CARBON_FOOTPRINT": 171532,
     *         "PREV_SCORE_GREEN_SHARE": 0,
     *         "SCORE_CATEGORY_BROWN_SHARE": "0-20%",
     *         "SCORE_CATEGORY_CARBON_FOOTPRINT": "Significant",
     *         "SCORE_CATEGORY_GREEN_SHARE": "None",
     *         "SECTOR": "Financials"
     *     }
     */
    @JsonProperty(value="BVD9_NUMBER")
    private String BVD9_NUMBER;
    @JsonProperty(value="COMPANY_NAME")
    private String COMPANY_NAME;

    @JsonProperty(value="CURR_CRITICAL_CONTROVERSIES")
    private String CURR_CRITICAL_CONTROVERSIES;
    @JsonProperty(value="CURR_SCORE_BROWN_SHARE")
    private Integer CURRENT_BROWN_SHARE_SCORE;
    @JsonProperty(value="CURR_SCORE_CARBON_FOOTPRINT")
    private Integer CURRENT_CARBON_SCORE;
    @JsonProperty(value="CURR_SCORE_GREEN_SHARE")
    private Integer CURRENT_GREEN_SHARE_SCORE;


    @JsonProperty(value="CURR_SCORE_PHYSICAL_RISK_MANAGEMENT")
    private Integer CURRENT_PHYSICAL_RISK_MANAGEMENT_SCORE;
    @JsonProperty(value="CURR_SCORE_TEMPERATURE_ALIGNMENT")
    private Double CURRENT_TEMPERATURE_ALIGNMENT_SCORE;

    @JsonProperty(value="PHYSICAL_RISK_HAZARD_CATEGORY_NAME")
    private String PHYSICAL_RISK_HAZARD_CATEGORY_NAME;
    @JsonProperty(value="PHYSICAL_RISK_HAZARD_PERCENT_FACILITIES_EXPOSED")
    private Integer PHYSICAL_RISK_HAZARD_PERCENT_FACILITIES_EXPOSED;

    @JsonProperty(value="PREV_SCORE_BROWN_SHARE")
    private Integer PREVIOUS_BROWN_SHARE_SCORE;
    @JsonProperty(value="PREV_SCORE_CARBON_FOOTPRINT")
    private Integer PREVIOUS_CARBON_SCORE;
    @JsonProperty(value="PREV_SCORE_GREEN_SHARE")
    private Integer PREVIOUS_GREEN_SHARE_SCORE;

    @JsonProperty(value="PREV_SCORE_PHYSICAL_RISK_MANAGEMENT")
    private Integer PREVIOUS_PHYSICAL_RISK_MANAGEMENT_SCORE;

    @JsonProperty(value="PERCENT_HOLDINGS")
    private Double PERCENT_HOLDINGS;

    @JsonProperty(value="SCORE_CATEGORY_BROWN_SHARE")
    private String SCORE_CATEGORY_BROWN_SHARE;
    @JsonProperty(value="SCORE_CATEGORY_CARBON_FOOTPRINT")
    private String SCORE_CATEGORY_CARBON_FOOTPRINT;

    @JsonProperty(value="SCORE_CATEGORY_ESG_ASSESSMENT")
    private String SCORE_CATEGORY_ESG_ASSESSMENT;
    @JsonProperty(value="SCORE_CATEGORY_GREEN_SHARE")
    private String SCORE_CATEGORY_GREEN_SHARE;

    @JsonProperty(value="SCORE_CATEGORY_PHYSICAL_RISK_MANAGEMENT")
    private String SCORE_CATEGORY_PHYSICAL_RISK_MANAGEMENT_SCORE;

    @JsonProperty(value="SCORE_CATEGORY_TEMPERATURE_ALIGNMENT")
    private String SCORE_CATEGORY_TEMPERATURE_ALIGNMENT;


    @JsonProperty(value="SECTOR")
    private String SECTOR;
}
