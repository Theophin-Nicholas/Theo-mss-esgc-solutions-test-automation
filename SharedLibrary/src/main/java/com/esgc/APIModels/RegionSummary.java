package com.esgc.APIModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class RegionSummary {

    private String category;

    private Integer change_companies;

    @JsonProperty(value="code")
    private String regionCode;

    @JsonProperty(value="companies")
    private Integer countOfCompanies;

    private Double investment_pct;

    @JsonProperty(value="name")
    private String regionName;

    @JsonProperty(value="portfolio_distribution")
    private List<PortfolioDistribution> portfolioDistributionList;

    private String score_range;

    private Integer weighted_average_score;
}
