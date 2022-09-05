package com.esgc.APIModels;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class PortfolioScoreWrapper {
    @JsonProperty(value="portfolio_score")
    public ArrayList<PortfolioScore> portfolioScore;

    @JsonProperty(value="benchmark_score")
    public ArrayList<PortfolioScore> benchmarkScore;
}
