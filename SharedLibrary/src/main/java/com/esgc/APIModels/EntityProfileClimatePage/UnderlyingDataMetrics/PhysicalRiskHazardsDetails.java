package com.esgc.APIModels.EntityProfileClimatePage.UnderlyingDataMetrics;

import lombok.Data;

/**
 * This class is used for Entity Climate Profile | UnderLying _ Physical Hazards widget
 */
@Data
public class PhysicalRiskHazardsDetails {
    private int score;
    private String score_category;
    private String score_msg;
    private String title;

}
