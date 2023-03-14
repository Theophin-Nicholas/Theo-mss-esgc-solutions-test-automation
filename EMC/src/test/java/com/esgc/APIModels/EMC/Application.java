package com.esgc.APIModels.EMC;

import lombok.Data;

@Data
public class Application {
    private String id;
    private String name;
    private String url;
    private String createdAt;
    private String modifiedAt;
    private String createdBy;
    private String modifiedBy;
    private String masteredBy;
    private String provider;
    private String key;
    private String type;
}
