package com.esgc.APIModels.EMC;

import lombok.Data;

@Data
public class Account {

    private String id;
    private String name;
    private boolean status;
    private String contractStart;
    private String contractEnd;
    private String createdAt;
    private String modifiedAt;
    private String createdBy;
    private String modifiedBy;

}
