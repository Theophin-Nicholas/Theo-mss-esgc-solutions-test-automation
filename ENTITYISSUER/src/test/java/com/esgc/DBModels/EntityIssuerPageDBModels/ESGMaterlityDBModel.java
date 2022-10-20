package com.esgc.DBModels.EntityIssuerPageDBModels;

import lombok.Data;


@Data
public class ESGMaterlityDBModel {

    private String ORBIS_ID;
    private String criteria_code;
    private String criteria_name;
    private String indicator;
    private String domain;
    private String sub_catg_desc ;
    private String critical_controversy_exists_flag;
    private String score_category;
    private String  data_type;
    private int  value ;
    private String  weight;
    private double criteria_score ;
    private int  disclosure_ratio;
}




