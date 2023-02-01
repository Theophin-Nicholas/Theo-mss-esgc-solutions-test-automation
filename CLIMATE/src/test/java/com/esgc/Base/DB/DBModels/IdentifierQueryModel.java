package com.esgc.Base.DB.DBModels;

import lombok.Data;

@Data
public class IdentifierQueryModel {
    private String entityIdColumnName;
    private String scoreColumnName;
    private String previousScoreColumnName;
    private String previusProducedDateColumnName;
    private String tableName;
}
