package com.esgc.APIModels.DashboardModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIHeatMapResponse {
    /**
     * [
     *     {
     *         "y_axis_total_invct_pct": [
     *             {
     *                 "research_line_1_score_category": "Low Risk",
     *                 "research_line_1_score_range": "20-39",
     *                 "total_investment": 20.53
     *             }
     *         ]
     *     }
     * ]
     */
    public ArrayList<YAxisTotalInvctPct> y_axis_total_invct_pct;
}

