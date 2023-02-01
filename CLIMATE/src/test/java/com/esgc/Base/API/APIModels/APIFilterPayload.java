package com.esgc.Base.API.APIModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class APIFilterPayload {

    /**
     *
     * {
     *   "region": "all",
     *   "sector": "all",
     *   "month": "03",
     *   "year": "2021"
     * }
     */

    private String region = "all";
    private String sector = "all";
    private String month;
    private String year;
    private String benchmark = "";


}
