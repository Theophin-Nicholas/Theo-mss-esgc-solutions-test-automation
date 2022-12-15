package com.esgc.PortfolioAnalysis.API.APIModels;

import lombok.Data;

/**
 * This class is used for Region Map API
 */
@Data
public class InvestmentAndScore {
    private String company_name;
    private double investment_pct;
    private int score;
    private String score_category;
    private String score_range ;
    private String orbis_id;
}