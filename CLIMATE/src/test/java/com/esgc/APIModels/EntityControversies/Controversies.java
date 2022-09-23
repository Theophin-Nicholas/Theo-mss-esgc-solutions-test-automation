package com.esgc.APIModels.EntityControversies;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

public class Controversies {
    ArrayList<ControversiesList> controversies_list;
    ArrayList<SubCategory> sub_categories;

    @JsonProperty("controversies_list")
    public ArrayList<ControversiesList> getControversies_list() {
        return this.controversies_list;
    }
    public void setControversies_list(ArrayList<ControversiesList> controversies_list) {
        this.controversies_list = controversies_list;
    }

    @JsonProperty("sub_categories")
    public ArrayList<SubCategory> getSub_categories() {
        return this.sub_categories;
    }
    public void setSub_categories(ArrayList<SubCategory> sub_categories) {
        this.sub_categories = sub_categories;
    }
}
