package com.exchangerate.currencyexchange.service;

import com.exchangerate.currencyexchange.domain.BillDetails;
import com.exchangerate.currencyexchange.domain.BillResponse;
import com.exchangerate.currencyexchange.domain.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class BillingServiceTest {
    @Mock
    private OpenExchangeRatesService exchangeRatesService;

    @Mock
    private DiscountCalculationService discountCalculationService;

    @InjectMocks
    private BillingService billingService;

    private static final String USD = "USD";
    private static final String INR = "INR";
    private static final String AFFILIATE = "affiliate";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenBillDetails_whenFinalBillAfterDiscountIsCalled_thenReturnFinalPayableAmount() {
        BillDetails billDetails = new BillDetails();
        final Item item1 = new Item("Microwave", "Electronics", 330.00);
        final Item item2 = new Item("Apple", "Groceries", 100.00);
        final Item item3 = new Item("Laptop", "Electronics", 500.00);
        final List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        items.add(item3);
        billDetails.setItems(items);
        billDetails.setOriginalCurrency(USD);
        billDetails.setTargetCurrency(INR);
        billDetails.setTotalAmount(930.00);
        billDetails.setUserType(AFFILIATE);
        billDetails.setCustomerTenure(2);

        Mockito.when(exchangeRatesService.getLatestExchangeRates(USD, INR)).thenReturn(84.00);
        Mockito.when(discountCalculationService.applyDiscounts(any())).thenReturn(802.00);

        BillResponse billResponse = billingService.finalBillAfterDiscount(billDetails);
        assertNotNull(billResponse);
        assertEquals(INR, billResponse.getTargetCurrency());
        assertEquals(67368.00, billResponse.getPayableAmount());
    }
}