package com.esgc.APIModels.PortfolioSettings;

import lombok.Data;

@Data
public class Investment {
    String company_name;
    double investment_pct;
    String orbis_id;
    int order;
    int rank;

}
