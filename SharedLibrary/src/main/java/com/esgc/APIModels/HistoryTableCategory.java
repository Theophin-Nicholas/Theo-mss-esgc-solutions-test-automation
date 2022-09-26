package com.esgc.APIModels;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.List;

@Data
@JsonDeserialize(using = HistoryTableDataResolverForHistoryTableCategoryDeserializer.class)
public class HistoryTableCategory {
    public String name;
    public List<HistoryTableData> data ;
}
