package com.esgc.APIModels.DashboardModels;

import lombok.Data;

@Data
public class GeoMapCountryEntity {
    /**
     *  {
     *         "bvd9_number": "006739603",
     *         "company_name": "Ramsay Health Care Ltd.",
     *         "country_code": "AU",
     *         "country_name": "Australia",
     *         "holdings": 1.59,
     *         "score": 14,
     *         "score_category": "No Risk",
     *         "score_range": "0-19"
     *     }
     */


    private String bvd9_number;
    private String company_name;
    private String country_code;
    private String country_name;
    private Double holdings;
    private Double score;
    private String score_category;
    private String score_range;
    private String score_msg;

} 
