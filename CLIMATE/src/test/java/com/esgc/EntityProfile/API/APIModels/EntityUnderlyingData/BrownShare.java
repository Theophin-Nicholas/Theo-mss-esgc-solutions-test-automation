package com.esgc.EntityProfile.API.APIModels.EntityUnderlyingData;

import lombok.Data;

import java.util.ArrayList;

@Data
public class BrownShare{
    ArrayList<BrownShareProduct> brown_share_products;
    String bvd9_number;
    int score;
    String score_category;
    String updated_date;
    String score_range;

}
