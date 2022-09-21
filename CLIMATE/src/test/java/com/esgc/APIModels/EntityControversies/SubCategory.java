package com.esgc.APIModels.EntityControversies;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubCategory {
    String sub_category;
    int sub_category_cnt;

    @JsonProperty("sub_category")
    public String getSub_category() {
        return this.sub_category;
    }
    public void setSub_category(String sub_category) {
        this.sub_category = sub_category;
    }

    @JsonProperty("sub_category_cnt")
    public int getSub_category_cnt() {
        return this.sub_category_cnt;
    }
    public void setSub_category_cnt(int sub_category_cnt) {
        this.sub_category_cnt = sub_category_cnt;
    }


}
