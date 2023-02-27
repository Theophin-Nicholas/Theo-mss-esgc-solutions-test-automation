package com.esgc.RegulatoryReporting.API.APIModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data @AllArgsConstructor @NoArgsConstructor
public class PortfolioDetails {

    public String portfolio_id;
    public String portfolio_name;
    public String upload_date_utc;
    public String as_of_date;
    public ArrayList<String> reporting_years;
    public double sfdr_coverage;
    public double taxonomy_coverage;
    public String cash_and_liquidities;
    public String non_sovereign_derivatives;


}
