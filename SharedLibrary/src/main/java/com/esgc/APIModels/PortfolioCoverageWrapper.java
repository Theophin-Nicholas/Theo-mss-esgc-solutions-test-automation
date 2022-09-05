package com.esgc.APIModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PortfolioCoverageWrapper {

    @JsonProperty(value="portfolio_coverage")
    private PortfolioCoverage portfolioCoverage;

    @JsonProperty(value="benchmark_coverage")
    private List<PortfolioCoverage> benchmarkCoverage;
}
