package com.exchangerate.currencyexchange.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * BillResponse containing targetCurrency and the total payableAmount
 */
@Data
@AllArgsConstructor
public class BillResponse {
    private String targetCurrency;
    private Double payableAmount;
}
