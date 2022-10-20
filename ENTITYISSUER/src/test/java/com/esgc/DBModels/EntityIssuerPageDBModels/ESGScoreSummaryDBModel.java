package com.esgc.DBModels.EntityIssuerPageDBModels;

import lombok.Data;
import lombok.ToString;

@Data @ToString(callSuper=true)
public class ESGScoreSummaryDBModel {

    private String Score;
    private String Lasttimestamp;
    //private String ScoreCategory;
    private String Criteria;

}
