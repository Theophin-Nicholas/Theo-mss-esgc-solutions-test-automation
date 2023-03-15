package com.esgc.APIModels.EMC;

import lombok.Data;

@Data
public class Role {
    private String key;
    private String name;
    private String description;
    private String createdAt;
    private String modifiedAt;
    private String createdBy;
    private String modifiedBy;
    private String id;
}
