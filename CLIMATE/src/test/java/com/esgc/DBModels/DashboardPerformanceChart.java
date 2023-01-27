package com.esgc.DBModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardPerformanceChart {


    private String BVD9_NUMBER;
    private String COMPANY_NAME;
    private Double investmentPercentage;
    private int SCORE;
    private int controCounts ;
    private int Scale ;
    private String ESGCATEGORIES ;


}
