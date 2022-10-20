package com.esgc.APIModels.EntityProfilePageModels.EntityScoreCategory;

import lombok.Data;

import java.util.ArrayList;

@Data
public class EsgAssessment {
    String last_updated;
    ArrayList<ScoreCategory> score_categories;
    String research_line_id;

}
