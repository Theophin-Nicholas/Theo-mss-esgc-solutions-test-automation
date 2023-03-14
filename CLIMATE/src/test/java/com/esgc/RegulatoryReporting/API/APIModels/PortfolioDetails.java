package com.esgc.RegulatoryReporting.API.APIModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor
public class PortfolioDetails {

    private String portfolio_id;
    private String portfolio_name;
    private String upload_date_utc;
    private String as_of_date;
    private ArrayList<String> reporting_years;
    private double sfdr_coverage;
    private double taxonomy_coverage;
    private Object non_sovereign_derivatives;
    private Object cash_and_liquidities;

}
