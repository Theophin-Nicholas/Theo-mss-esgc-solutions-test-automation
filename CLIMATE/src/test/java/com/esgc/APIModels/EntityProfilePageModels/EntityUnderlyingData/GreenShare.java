package com.esgc.APIModels.EntityProfilePageModels.EntityUnderlyingData;

import lombok.Data;

import java.util.ArrayList;

@Data
public class GreenShare {
    String bvd9_number;
    ArrayList<GreenShareProduct> green_share_products;
    String score_category;
    String score_msg;
    String updated_date;

}
