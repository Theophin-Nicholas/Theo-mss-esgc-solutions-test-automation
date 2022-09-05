package com.esgc.APIModels;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class carbonFootprintEmissionData {
    private String total;
    private BigDecimal totalAssets;
    private BigDecimal marketCapitalization;
}