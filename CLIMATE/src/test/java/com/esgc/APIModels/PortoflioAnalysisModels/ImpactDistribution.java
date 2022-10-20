package com.esgc.APIModels.PortoflioAnalysisModels;

import lombok.Data;

/**
 * This class is used for Region Map API
 */
@Data
public class ImpactDistribution {
    private String category;
    private double selection;
    private double total;
}