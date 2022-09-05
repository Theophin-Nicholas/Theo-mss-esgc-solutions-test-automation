package com.esgc.APIModels.EntityProfileClimatePage.UnderlyingDataMetrics;

import lombok.Data;

import java.util.ArrayList;

/**
 * This class is used for Entity Climate Profile | UnderLying _ Physical Hazards widget
 */
@Data
public class PhysicalRiskHazardsWrapper {
    private String bvd9_number;
    private String research_line;
    private String score_category;
    private ArrayList<PhysicalRiskHazardsDetails> underlying_metrics;
}
