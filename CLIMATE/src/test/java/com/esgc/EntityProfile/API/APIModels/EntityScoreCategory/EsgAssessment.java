package com.esgc.EntityProfile.API.APIModels.EntityScoreCategory;

import lombok.Data;

import java.util.ArrayList;

@Data
public class EsgAssessment {
    String last_updated;
    ArrayList<ScoreCategory> score_categories;
    String research_line_id;

}
