package com.esgc.APIModels;

import lombok.Data;

/**
 * This class is used for Region Map API
 */
@Data
public class ImpactDistributionWrappers {
    private NegativeImpact negative_impact;
    private PositiveImpact positive_impact;
}