package com.esgc.APIModels.PortoflioAnalysisModels;

import lombok.Data;

import java.util.List;

@Data
public class HistoryTableWrapper {
    public HistoryTables portfolio;
    public List<HistoryTables> benchmark ;
}
