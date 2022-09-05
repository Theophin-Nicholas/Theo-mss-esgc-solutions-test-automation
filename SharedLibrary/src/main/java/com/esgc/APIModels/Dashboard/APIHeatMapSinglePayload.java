package com.esgc.APIModels.Dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIHeatMapSinglePayload {
    /**
     * {
     * "region": "all",
     * "sector": "all",
     * "month": "02",
     * "year": "2022",
     * "research_line_1": "greenshareasmt",
     * }
     */
    private String region;
    private String sector;
    private String month;
    private String year;
    private String research_line_1;
}
