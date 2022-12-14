package com.esgc.APIModels.PortfolioSettings;

import lombok.Data;

@Data
public class Investment {
    String company_name;
    double investment_pct;
    String orbis_id;
    int order;
    String parent_company_name;
    String parent_orbis_id;
    int rank;
    String score_quality;
}
