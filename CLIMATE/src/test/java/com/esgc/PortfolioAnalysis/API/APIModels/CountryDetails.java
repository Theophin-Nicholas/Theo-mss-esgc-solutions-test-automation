package com.esgc.PortfolioAnalysis.API.APIModels;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 *     country_code: "bm"
 *     country_name: "Bermuda"
 *     holdings: 0.81
 *     number_of_companies: 1
 *     region: "AMER"
 *     region_name: "Americas"
 *     score: 32
 *     score_category: "Low Risk"
 */

@Data
public class CountryDetails {
    @SerializedName(value="country_code")
    String countryCode;
    @SerializedName(value="country_name")
    String countryName;
    @SerializedName(value="holdings")
    Double percentage;
    @SerializedName(value="number_of_companies")
    Integer numberOfCompanies;
    @SerializedName(value="region")
    String regionCode;
    @SerializedName(value="region_namee")
    String regionName;
    Integer score;
    @SerializedName(value="score_category")
    String scoreCategory;

}
