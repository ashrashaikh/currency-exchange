package com.exchangerate.currencyexchange.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * BillDetails
 * Contains request body
 */
@Getter
@Setter
public class BillDetails {
    private List<Item> items;
    private Double totalAmount;
    private String userType;
    private Integer customerTenure;
    private String originalCurrency;
    private String targetCurrency;
}
