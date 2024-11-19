package com.exchangerate.currencyexchange.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * ExchangeRateResponse
 * Contains a map with the currency and its current exchange rate
 */
@Getter
@Setter
public class ExchangeRateResponse {
    private Map<String, Double> rates;
}
