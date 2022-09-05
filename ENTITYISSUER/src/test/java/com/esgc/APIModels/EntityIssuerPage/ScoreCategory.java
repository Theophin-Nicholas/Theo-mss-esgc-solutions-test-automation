package com.esgc.APIModels.EntityIssuerPage;

import lombok.Data;


@Data
public class ScoreCategory {

    private String criteria;
    private double score;
    private String score_category;
    private String score_msg;
    private String qualifier;

}




