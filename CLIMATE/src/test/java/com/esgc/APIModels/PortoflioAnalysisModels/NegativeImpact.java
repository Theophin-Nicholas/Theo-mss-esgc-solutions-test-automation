package com.esgc.APIModels.PortoflioAnalysisModels;

import lombok.Data;

import java.util.List;

/**
 * This class is used for Region Map API
 */
@Data
public class NegativeImpact {
    private List<ImpactDistribution> distribution;
    private String impact_side;
    private List<InvestmentAndScore> investment_and_score;
    private double total_investment_perc;
    private int x_axis_investment;
}