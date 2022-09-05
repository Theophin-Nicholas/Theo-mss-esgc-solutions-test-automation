package com.esgc.APIModels.EMC;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class User {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("userName")
    private String userName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("createdAt")
    private String createdAt;
    @JsonProperty("modifiedAt")
    private String modifiedAt;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("modifiedBy")
    private String modifiedBy;
    @JsonProperty("provisionedOktaResource")
    private Boolean provisionedOktaResource;
    @JsonProperty("oktaId")
    private String oktaId;
    @JsonProperty("provider")
    private String provider;
    @JsonProperty("masteredBy")
    private String masteredBy;
    @JsonProperty("status")
    private String status;
    @JsonProperty("lastLogin")
    private Object lastLogin;
}
