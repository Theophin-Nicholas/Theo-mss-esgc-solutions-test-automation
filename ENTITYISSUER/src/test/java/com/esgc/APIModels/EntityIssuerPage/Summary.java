package com.esgc.APIModels.EntityIssuerPage;

import lombok.Data;

import java.util.ArrayList;


@Data
public class Summary {

    public String last_updated;
    public ArrayList<ScoreCategory> score_categories;
    public String orbis_id;

}




