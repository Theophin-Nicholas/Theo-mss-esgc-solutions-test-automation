package com.esgc.EntityProfile.API.APIModels.SummarySection;

import lombok.Data;

@Data
public class PhysicalRiskManagementSummaryDetails {
    public String name;
    public int score;
    public String score_category;
}
