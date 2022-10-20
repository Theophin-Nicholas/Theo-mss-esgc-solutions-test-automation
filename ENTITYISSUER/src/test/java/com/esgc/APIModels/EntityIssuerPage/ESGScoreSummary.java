package com.esgc.APIModels.EntityIssuerPage;

import lombok.Data;

import java.util.List;

@Data

public class ESGScoreSummary {

    private String last_updated;
    private String orbis_id;
    private List<ScoreCategory> score_categories;

}




