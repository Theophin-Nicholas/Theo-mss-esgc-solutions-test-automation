package com.esgc.Dashboard.API.APIModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIEntityListPayload {

    /**
     * {
     * "region": "all",
     * "sector": "all",
     * "month": "06",
     * "year": "2021",
     * "country_code": "us"
     * }
     */


    private String region;
    private String sector;
    private String month;
    private String year;
    private String country_code;


}
