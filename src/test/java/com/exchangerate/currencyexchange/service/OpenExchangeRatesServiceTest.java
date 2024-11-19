package com.exchangerate.currencyexchange.service;

import com.exchangerate.currencyexchange.domain.ExchangeRateResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.eq;

class OpenExchangeRatesServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OpenExchangeRatesService openExchangeRatesService;

    private static final String USD = "USD";
    private static final String AED = "AED";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void fetchLatestExchangeRates_when_getLatestExchangeRatesIsCalled() {

        Map<String, Double> rates = new HashMap<>();
        rates.put(AED, 3.673);
        ExchangeRateResponse mockExchangeRateResponse = new ExchangeRateResponse();
        mockExchangeRateResponse.setRates(rates);

        ResponseEntity<ExchangeRateResponse> responseEntity =
                new ResponseEntity<>(mockExchangeRateResponse, HttpStatus.OK);

        Mockito.when(restTemplate.exchange(
                Mockito.anyString(),
                eq(HttpMethod.GET),
                eq(null),
                eq(ExchangeRateResponse.class)
        )).thenReturn(responseEntity);

        Double exchangeRate = openExchangeRatesService.getLatestExchangeRates(USD, AED);
        Assertions.assertNotNull(exchangeRate);
        Assertions.assertEquals(3.673, exchangeRate);
    }
}