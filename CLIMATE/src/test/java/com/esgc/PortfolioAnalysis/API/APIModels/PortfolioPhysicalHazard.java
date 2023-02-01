package com.esgc.PortfolioAnalysis.API.APIModels;

import lombok.Data;

@Data

public class PortfolioPhysicalHazard {

    private String facilities_exposed;
    private String highest_risk_hazard;
    private String hrh_risk_category;
}
