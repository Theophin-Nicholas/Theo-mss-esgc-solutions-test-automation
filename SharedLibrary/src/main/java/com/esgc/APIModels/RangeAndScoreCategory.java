package com.esgc.APIModels;

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
}
