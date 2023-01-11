package com.esgc.PortfolioAnalysis.API.APIModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * {
 * "company_name": "Abc-Mart,Inc.",
 * "investment_pct": 0.76,
 * "rank": 121,
 * "score": 0,
 * "score_category": "Weak",
 * "updated": "2021-03-25"
 * }
 */

@Data @AllArgsConstructor @NoArgsConstructor
public class Company {

    private String company_name;
    private Double investment_pct;
    private Integer rank;
    private Double score;
    private String score_category;
    private Date updated;
    private String orbis_id;
    private String score_msg;
    private String score_range;

}
