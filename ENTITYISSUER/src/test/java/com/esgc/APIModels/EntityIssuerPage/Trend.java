package com.esgc.APIModels.EntityIssuerPage;

import lombok.Data;

import java.util.List;

@Data
public class Trend {
    private String angle;
    private String pillar;
    private List<TrendDetail> trend_details;
    private String trend_name;
}
