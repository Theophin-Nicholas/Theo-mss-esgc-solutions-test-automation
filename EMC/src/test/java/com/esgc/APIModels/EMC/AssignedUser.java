package com.esgc.APIModels.EMC;

import lombok.Data;

@Data
public class AssignedUser {
    private String name;
    private String id;
    private String userName;
    private String accountId;
    private String accountName;
    private String userProvider;
}
