package com.esgc.APIModels.PortfolioSettings;

import lombok.Data;

import java.util.ArrayList;

@Data
public class PortfolioDetails {
    int entities;
    ArrayList<Investment> investments;
    String portfolio_id;
    int portfolio_percent;
    int total_matched_companies;
    int total_unique_companies;
    int total_unmatched_companies;
    int unmatched_portfolio_percent;

}
