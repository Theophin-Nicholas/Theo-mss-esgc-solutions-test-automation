package com.esgc.APIModels.Dashboard;

import com.esgc.APIModels.APIFilterPayload;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class APIPerformanceChartPayload {


    /**
     * {
     * "region": "all",
     * "sector": "all",
     * "month": "01",
     * "year": "2022",
     * "table_type": "largest_holdings",
     * "limit": 10
     * }
     */
    private String region;
    private String sector;
    private String month;
    private String year;
    private String table_type;
    private int limit;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String research_line;

    public APIPerformanceChartPayload(APIFilterPayload apiFilterPayload, String researchLine, String tableType, int limit) {
        this.region = apiFilterPayload.getRegion();
        this.sector = apiFilterPayload.getSector();
        this.month = apiFilterPayload.getMonth();
        this.year = apiFilterPayload.getYear();
        this.table_type = tableType;
        this.limit = limit;
        this.research_line = researchLine;
    }

}
