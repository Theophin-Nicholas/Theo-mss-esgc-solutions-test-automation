package com.esgc.DBModels;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class carbonFootPrintEmissionDBModel {
    private String category;
    private BigDecimal totalAssets;
    private BigDecimal marketCapitalization;

}
