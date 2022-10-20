package com.esgc.DBModels;

import lombok.Data;

@Data
public class UpdatesDBModel {
    /*
        {"company_name": "Warehouses De Pauw", "country": "BEL", "investment_pct": 0.52, "last_update_date": "2020-12-18",
    "region_name": "Europe, Middle East & Africa", "score": 0, "score_category": "No Risk", "sector_name": "Infrastructure", "country_name": "Belgium"}
     */
     private String company_name;
     private String country;
     private Double investment_pct;
     private String last_update_date;
     private String region_name;
     private Double score;
     private String score_category;
     private String sector_name;
     private String country_name;


}
