package com.esgc.APIModels;

import com.esgc.APIModels.PortfolioScore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ESGScore {
    @JsonProperty("portfolio_score")
    private List<PortfolioScore> portfolioScore = null;
    @JsonProperty("benchmark_score")
    private List<Object> benchmarkScore = null;
}
