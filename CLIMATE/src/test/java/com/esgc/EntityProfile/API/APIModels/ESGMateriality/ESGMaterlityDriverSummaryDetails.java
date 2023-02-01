package com.esgc.EntityProfile.API.APIModels.ESGMateriality;

import lombok.Data;

import java.util.List;


@Data
public class ESGMaterlityDriverSummaryDetails {

    private String criteria_id;
    private String criteria_name;
    private double criteria_score;
    private String critical_controversy_exists_flag ;
    private int disclosure_ratio ;
    private String domain;
    private List<ESGMaterlityDriverWeights> driver_weights;
    private String indicator;
    private String score_category;
    private String sub_category_detailed_description;
}




