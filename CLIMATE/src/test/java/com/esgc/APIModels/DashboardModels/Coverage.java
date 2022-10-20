package com.esgc.APIModels.DashboardModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Coverage {
    private PortfolioCoverage portfolio_coverage;

}
