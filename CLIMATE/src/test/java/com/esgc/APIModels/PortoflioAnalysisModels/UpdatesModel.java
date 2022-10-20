package com.esgc.APIModels.PortoflioAnalysisModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdatesModel {
/*
{
		"company_name": "AEON Financial Service Co., Ltd.",
		"country": "jp",
		"country_name": "Japan",
		"investment_pct": 0.24,
		"last_update_date": "2022-06-23",
		"orbis_id": "053463238",
		"previous_score": "43184",
		"previous_score_category": "Moderate",
		"previous_update_date": "2021-01-22",
		"region_name": "Asia Pacific",
		"score": 22155,
		"score_category": "Moderate",
		"sector_name": "Financials"
	}
 */
    @JsonProperty(value="company_name")
    public String companyName;

    @JsonProperty("country")
    public String country;

    @JsonProperty(value="country_name")
    public String countryName;

    @JsonProperty(value="investment_pct")
    public double investmentPct;

    @JsonProperty(value="last_update_date")
    public String lastUpdateDate;

    @JsonProperty(value="orbis_id")
    public String orbis_id;

    @JsonProperty("previous_score")
    public String previousScore;

    @JsonProperty(value="previous_score_category")
    public String previousScoreCategory;

    @JsonProperty(value="previous_update_date")
    public String previousUpdateDate;

    @JsonProperty(value="region_name")
    public String regionName;

    @JsonProperty("score")
    public int score;

    @JsonProperty(value="score_category")
    public String scoreCategory;

    @JsonProperty(value="sector_name")
    public String sectorName;




}
