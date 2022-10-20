package com.esgc.DBModels.EntityIssuerPageDBModels;

import lombok.Data;
import lombok.ToString;

@Data @ToString(callSuper=true)
public class DriverScoreDBModel {

    private String ORBIS_ID ;
    private String ENTITY_NAME_BVD;
    private String MESG_SECTOR;
    private String CRITERIA ;
    private String NAME ;
    private String INDICATOR ;
    private String DOMAIN ;
    private String DATA_TYPE ;
    private int    VALUE ;
    private double SCORE ;
    private String SCORECATEGORY ;

}
