package com.esgc.APIModels.PortoflioAnalysisModels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class PortfolioDistribution {

    private String category;
    private Integer companies;
    private Double investment_pct;
    @JsonIgnore
    private Integer prior_quarter_companies;
    @JsonIgnore
    private Integer prior_quarter_investment_pct;
    @JsonIgnore
    private Integer prior_month_companies;
    @JsonIgnore
    private Double prior_month_investment_pct;
    @JsonIgnore
    private Integer companies_mom;
    @JsonIgnore
    private Integer companies_qoq;
    @JsonIgnore
    private Double investment_pct_mom;
    @JsonIgnore
    private Double investment_pct_qoq;
    private String score_range;
}
