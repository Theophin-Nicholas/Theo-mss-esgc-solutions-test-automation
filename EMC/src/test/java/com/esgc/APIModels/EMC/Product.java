package com.esgc.APIModels.EMC;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Product {
    private String id;
    private String name;
    private String code;
    private String key;
    @JsonProperty(value = "info", required = false)
    private SMEInfo SMEInfo;
}
