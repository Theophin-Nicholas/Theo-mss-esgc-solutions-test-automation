package com.esgc.PortfolioAnalysis.API.APIModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RangeAndScoreCategory {
    private String category;
    private Double min;
    private Double max;
    private String impact;
    private Double min2;
    private Double max2;
    private Double minScale;
    private Double maxScale;


    public RangeAndScoreCategory(String category, Double min, Double max, String impact) {
        this.category = category;
        this.min = min;
        this.max = max;
        this.impact = impact;
    }
}
