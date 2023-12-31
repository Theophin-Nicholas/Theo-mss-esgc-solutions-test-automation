package com.esgc.PortfolioAnalysis.API.APIModels;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PortfolioScore {

    @JsonProperty(value="name")
    @JsonAlias({"label"})
    private String name;

    @JsonProperty(value="ranking")
    @JsonAlias({"category"})
    private String ranking;

    @JsonProperty(value="score")
    @JsonAlias({"portfolio_score", "weighted_avg"})
    private String score;

    @JsonProperty(value="benchmark_ranking")
    private String benchmark_ranking;

    @JsonProperty(value="benchmark_score")
    private String benchmark_score;
}
