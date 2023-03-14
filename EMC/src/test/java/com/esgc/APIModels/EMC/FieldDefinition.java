package com.esgc.APIModels.EMC;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FieldDefinition {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("displayName")
    private String displayName;
    @JsonProperty("type")
    private String type;
    @JsonProperty("required")
    private Boolean required;
    @JsonProperty("editable")
    private Boolean editable;
}
