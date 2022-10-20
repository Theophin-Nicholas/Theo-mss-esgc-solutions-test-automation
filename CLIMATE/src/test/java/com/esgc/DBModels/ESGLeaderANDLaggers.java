package com.esgc.DBModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ESGLeaderANDLaggers {


    private int RANK;
    private int SCORE;
    private String BVD9_NUMBER;
    private String COMPANY_NAME;
    private Double investmentPercentage;
    private int SCORING_RLID;
    private int Scale ;
    private String METHODOLOGY_VERSION ;


}
