package com.esgc.APIModels.Dashboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PortfolioCoverage {
        private String companies;
        private double investment;
}
