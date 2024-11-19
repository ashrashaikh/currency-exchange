package com.exchangerate.currencyexchange.controller;

import com.exchangerate.currencyexchange.domain.BillDetails;
import com.exchangerate.currencyexchange.domain.BillResponse;
import com.exchangerate.currencyexchange.domain.Item;
import com.exchangerate.currencyexchange.service.BillingService;
import com.exchangerate.currencyexchange.service.DiscountCalculationService;
import com.exchangerate.currencyexchange.service.OpenExchangeRatesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(CurrencyExchangeRateController.class)
class CurrencyExchangeRateControllerTest {

    @MockBean
    private BillingService billingService;

    @MockBean
    private DiscountCalculationService discountCalculationService;

    @MockBean
    private OpenExchangeRatesService exchangeRatesService;

    @Autowired
    private MockMvc mockMvc;

    private BillDetails billDetails;
    private BillResponse billResponse;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String USD = "USD";
    private static final String AED = "AED";
    private static final String EMPLOYEE = "employee";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        billDetails = new BillDetails();
        final Item item1 = new Item("Fan", "Electronics", 90.00);
        final Item item2 = new Item("Bread", "Groceries", 10.00);
        final List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        billDetails.setItems(items);
        billDetails.setOriginalCurrency(USD);
        billDetails.setTargetCurrency(AED);
        billDetails.setTotalAmount(100.00);
        billDetails.setUserType(EMPLOYEE);
        billDetails.setCustomerTenure(2);

        billResponse = new BillResponse(AED, 249.764);
    }

    @Test
    void givenBillDetailsWithHttpHeader_when_calculatePayableAmount_isCalled_thenReturn403ForbiddenResponse() throws Exception {
        when(discountCalculationService.applyDiscounts(billDetails)).thenReturn(68.00);
        when(exchangeRatesService.getLatestExchangeRates(USD, AED)).thenReturn(3.673);
        when(billingService.finalBillAfterDiscount(billDetails)).thenReturn(billResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(billDetails)))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Disabled
    void givenBillDetailsWithValidRequest_when_calculatePayableAmount_isCalled_thenReturnFinalPayableAmount() throws Exception {
        when(discountCalculationService.applyDiscounts(billDetails)).thenReturn(68.00);
        when(exchangeRatesService.getLatestExchangeRates(USD, AED)).thenReturn(3.673);
        when(billingService.finalBillAfterDiscount(billDetails)).thenReturn(billResponse);

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("ashra", "password");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/calculate")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(billDetails)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.targetCurrency").value(AED))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payableAmount").value(249.764));
    }
}