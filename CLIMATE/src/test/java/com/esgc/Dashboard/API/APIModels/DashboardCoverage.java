package com.esgc.Dashboard.API.APIModels;

import lombok.Data;

@Data
public class DashboardCoverage {
    private int companies;
    private String coverage;
    private double perc_investment;
    private boolean assessment_requested;
}
