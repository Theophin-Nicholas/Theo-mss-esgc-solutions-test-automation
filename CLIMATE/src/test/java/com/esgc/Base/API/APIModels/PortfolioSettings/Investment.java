package com.esgc.Base.API.APIModels.PortfolioSettings;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Investment {
    String climate;
    String company_name;
    double investment_pct;
    String orbis_id;
    int order;
    int rank;
    String score_quality;
    @JsonIgnore
    String parent_company_name;
    @JsonIgnore
    String parent_orbis_id;

}
