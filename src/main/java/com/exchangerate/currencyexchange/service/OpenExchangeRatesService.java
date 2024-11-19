package com.exchangerate.currencyexchange.service;

import com.exchangerate.currencyexchange.domain.ExchangeRateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

import static com.exchangerate.currencyexchange.util.CurrencyExchangeConstants.OER_URL_FORMAT;

/**
 * OpenExchangeRatesService
 * This service class connects to Open Exchange Rates API to get the latest currency exchange rates.
 */
@Service
public class OpenExchangeRatesService {
    @Value("${open.exchange.rate.api.url}")
    private String OPEN_EXCHANGE_RATE_API_URL;

    @Value("${open.exchange.rate.api.key}")
    private String OPEN_EXCHANGE_RATE_API_KEY;

    @Autowired
    private final RestTemplate restTemplate;

    public OpenExchangeRatesService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Get Latest Currency Exchange Rate from the exchange rate api
     * @param baseCurrency      currency to be exchanged
     * @param targetCurrency    target currency
     * @return exchange rate of the target currency from base currency
     */
    public Double getLatestExchangeRates(final String baseCurrency, final String targetCurrency) {
        final String apiUrl = String.format(OER_URL_FORMAT,
                OPEN_EXCHANGE_RATE_API_URL,OPEN_EXCHANGE_RATE_API_KEY, baseCurrency);
        final ResponseEntity<ExchangeRateResponse> responseEntity =
                restTemplate.exchange(apiUrl,
                        HttpMethod.GET,
                        null, ExchangeRateResponse.class);
        return Optional.ofNullable(responseEntity.getBody())
                .map(ExchangeRateResponse::getRates)
                .flatMap(rateMap -> rateMap.entrySet()
                        .stream()
                        .filter(rate ->
                                rate.getKey().equals(targetCurrency))
                        .findFirst()
                        .map(Map.Entry::getValue)).orElse(null);
    }
}
