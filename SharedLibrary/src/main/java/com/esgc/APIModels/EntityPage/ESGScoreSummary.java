package com.esgc.APIModels.EntityPage;

import lombok.Data;

import java.util.List;

@Data

public class ESGScoreSummary {

    private String last_updated;
    private String orbis_id;
    private List<ScoreCategory> score_categories;

}




