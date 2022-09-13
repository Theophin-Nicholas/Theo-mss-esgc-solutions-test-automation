package com.esgc.APIModels;

import lombok.Data;

import java.util.List;

@Data
public class HistoryTableCategory {
    public String name;
    public List<HistoryTableData> data ;
}
