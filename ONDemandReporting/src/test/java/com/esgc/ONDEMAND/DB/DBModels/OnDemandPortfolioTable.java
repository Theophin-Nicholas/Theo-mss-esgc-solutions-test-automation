package com.esgc.ONDEMAND.DB.DBModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnDemandPortfolioTable {
    private String PORTFOLIO_ID;
    private String bvd9_number;
    private String COMPANY_NAME;
    private String REGION;
    private String SECTOR;
    private String SCORE_QUALITY;
    private String ENTITY_STATUS;
    private int VALUE;
    private int TOTAL_COMPANIES;
    private int TOTAL_VALUE;
}

