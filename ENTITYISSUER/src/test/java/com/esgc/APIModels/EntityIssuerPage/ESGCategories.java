package com.esgc.APIModels.EntityIssuerPage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ESGCategories {
    private String methodology;
   private List<SubCategories> subcategories;
}
