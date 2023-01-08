package com.esgc.Dashboard.API.APIModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyBrownShareInfo {
    public String bvd9_number;
    public String category_score_r1_1;
    public String company_name;
    public String managed_type;
    public double percent_holdings;
    public double score_rl_1;
}
