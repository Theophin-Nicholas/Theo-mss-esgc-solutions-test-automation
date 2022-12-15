package com.esgc.PortfolioAnalysis.API.APIModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SectorSummary {
    private Integer change_companies;
    @JsonProperty(value="category")
    private String category;
    @JsonProperty(value="companies")
    private Integer countOfCompanies;
    private Double investment_pct;
    @JsonProperty(value="name")
    private String sectorName;
    @JsonProperty(value="portfolio_distribution")
    private List<PortfolioDistribution> portfolioDistributionList;
    private String score_range;
    private Double weighted_average_score;
}