package com.esgc.APIModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class LeadersAndLaggards {
    /*
 {
        "company_name": "Sumitomo Corporation",
        "investment_pct": 1.05,
        "rank": 28,
        "score": 1474886,
        "score_category": "High",
        "updated": "2021-01-19"
    },

 */
    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("investment_pct")
    private Double investmentPct;

    @JsonProperty("methodology_version")
    private Double methodologyversion;

    @JsonProperty("rank")
    private int rank;

    @JsonProperty("score")
    private double score;

    @JsonProperty("score_category")
    private String scoreCategory;

    @JsonProperty("score_range")
    private String scoreRange;

    @JsonProperty("updated")
    private Date updated;

    @JsonProperty("orbis_id")
    private String orbisId;
}
