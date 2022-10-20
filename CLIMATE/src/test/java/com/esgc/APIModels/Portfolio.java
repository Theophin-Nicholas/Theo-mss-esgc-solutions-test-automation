package com.esgc.APIModels;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * {
 *             "portfolio_id": "737b7114-3157-423d-a700-35f99840f767",
 *             "portfolio_name": "My AMAZING Portfolio",
 *             "currency": "USD",
 *             "as_of_date": "2021-01-22"
 *         }
 */

@Data
@AllArgsConstructor
public class Portfolio {

    private String portfolio_id;
    private String portfolio_name;
    private String currency;
    private String as_of_date;

}




