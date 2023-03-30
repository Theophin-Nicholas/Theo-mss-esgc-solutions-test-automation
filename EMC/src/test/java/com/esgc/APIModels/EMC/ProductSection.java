package com.esgc.APIModels.EMC;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductSection {
    @JsonProperty(value = "name", required = false)
    private String name;
    @JsonProperty(value = "displayName", required = false)
    private String displayName;
    @JsonProperty(value = "fieldDefinitions", required = false)
    private List<FieldDefinition> fieldDefinitions;
}
