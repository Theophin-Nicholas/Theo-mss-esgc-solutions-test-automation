package com.esgc.APIModels.EMC;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AssignedProduct {
    @JsonProperty("application")
    private Application application;
    @JsonProperty("product")
    private Product product;
}
