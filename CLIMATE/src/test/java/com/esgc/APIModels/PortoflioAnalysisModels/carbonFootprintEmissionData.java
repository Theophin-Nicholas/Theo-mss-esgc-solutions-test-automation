package com.esgc.APIModels.PortoflioAnalysisModels;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class carbonFootprintEmissionData {
    private String total;
    private BigDecimal totalAssets;
    private BigDecimal marketCapitalization;
}