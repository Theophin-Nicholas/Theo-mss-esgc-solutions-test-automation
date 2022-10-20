package com.esgc.APIModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIFilterPayloadWithoutBenchmark {

    /**
     * {
     * "region": "all",
     * "sector": "all",
     * "month": "03",
     * "year": "2021"
     * }
     */

    private String region;
    private String sector;
    private String month;
    private String year;


}
