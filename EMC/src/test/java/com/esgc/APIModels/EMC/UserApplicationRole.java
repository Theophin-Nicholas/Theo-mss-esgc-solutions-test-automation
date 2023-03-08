package com.esgc.APIModels.EMC;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserApplicationRole {
    @JsonProperty("application")
    private Application application;
    @JsonProperty("role")
    private Role role;
}
