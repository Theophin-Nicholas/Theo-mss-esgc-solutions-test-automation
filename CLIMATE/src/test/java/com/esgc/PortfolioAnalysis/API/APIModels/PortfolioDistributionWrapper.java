package com.esgc.PortfolioAnalysis.API.APIModels;

import lombok.Data;

import java.util.List;

@Data
public class PortfolioDistributionWrapper {
    public List<PortfolioDistribution> portfolio_distribution;
    public List<PortfolioDistribution> benchmark_distribution;
}
