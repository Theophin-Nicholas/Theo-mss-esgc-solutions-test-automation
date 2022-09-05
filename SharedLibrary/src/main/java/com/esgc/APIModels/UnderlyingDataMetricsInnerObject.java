package com.esgc.APIModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
// To review and delete
@Data
@NoArgsConstructor
public class UnderlyingDataMetricsInnerObject {

    /*
     {
                    "fac_exposed": [
                        9.9318
                    ],
                    "investment_pct": [
                        9.938343611877
                    ],
                    "name": "Red Flag Risk"
                }
     */

    @JsonProperty("count")
    private Integer count;
    @JsonProperty("data")
    private List<Double> data = new ArrayList<>();
    @JsonProperty("name")
    private String name;
    @JsonProperty("fac_exposed")
    private List<Double> facExposed;
    @JsonProperty("investment_pct")
    private List<Double> investment;

}
