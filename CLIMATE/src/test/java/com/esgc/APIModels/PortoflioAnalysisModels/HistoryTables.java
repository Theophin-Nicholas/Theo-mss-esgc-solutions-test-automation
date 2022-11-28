package com.esgc.APIModels.PortoflioAnalysisModels;

import lombok.Data;

import java.util.List;

@Data
public class HistoryTables {

    public List<HistoryTableCategory> categorydata ;
    public List<String> yearlydata ;
}
