package com.esgc.APIModels;

import lombok.Data;

import java.util.List;

@Data
public class HistoryTables {

    public List<HistoryTableCategory> categorydata ;
    public List<String> yearlydata ;
}
