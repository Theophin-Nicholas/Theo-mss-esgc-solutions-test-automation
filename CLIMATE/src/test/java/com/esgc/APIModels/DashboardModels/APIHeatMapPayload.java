package com.esgc.APIModels.DashboardModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIHeatMapPayload {
    /**
     * {
     * "region": "all",
     * "sector": "all",
     * "month": "02",
     * "year": "2022",
     * "research_line_1": "greenshareasmt",
     * "research_line_2": "brownshareasmt"
     * }
     */


    private String region;
    private String sector;
    private String month;
    private String year;
    private String research_line_1;
    private String research_line_2;


}
