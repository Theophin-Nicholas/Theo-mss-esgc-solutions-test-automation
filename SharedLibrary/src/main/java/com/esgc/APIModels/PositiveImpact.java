package com.esgc.APIModels;

import lombok.Data;

import java.util.List;


@Data
public class PositiveImpact {
    private List<ImpactDistribution> distribution;
    private String impact_side;
    private List<InvestmentAndScore> investment_and_score;
    private double total_investment_perc;
    private int x_axis_investment;
}