package com.esgc.APIModels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.ArrayList;

@Data
public class PortfolioDistributionWrapper {

    public ArrayList<PortfolioDistribution> portfolio_distribution;
}
