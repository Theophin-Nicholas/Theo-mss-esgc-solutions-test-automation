package com.esgc.APIModels.EntityProfilePageModels.EntityControversies;


import lombok.Data;

import java.util.ArrayList;

@Data
public class Controversies {
    ArrayList<ControversiesList> controversies_list;
    ArrayList<SubCategory> sub_categories;
}
