package com.esgc.DBModels.EntityPage;

import lombok.Data;
import lombok.ToString;

@Data @ToString(callSuper=true)
public class ESGScoreSummaryDBModel {

    private String Score;
    private String Lasttimestamp;
    //private String ScoreCategory;
    private String Criteria;

}
