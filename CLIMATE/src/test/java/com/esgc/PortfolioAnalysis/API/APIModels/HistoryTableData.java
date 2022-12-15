package com.esgc.PortfolioAnalysis.API.APIModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryTableData {
    public Double inv_pct;
    public Integer num_companies;
}
