package com.exchangerate.currencyexchange.service;

import com.exchangerate.currencyexchange.domain.BillDetails;
import com.exchangerate.currencyexchange.domain.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

class DiscountCalculationServiceTest {

    @InjectMocks
    DiscountCalculationService discountCalculationService;

    private static final String USD = "USD";
    private static final String INR = "INR";
    private static final String EMPLOYEE = "employee";
    private static final String AFFILIATE = "affiliate";
    private static final String CUSTOMER = "customer";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenBillDetails_whenApplyDiscountsIsCalled_thenReturnTotalAmountToBePaidAfterDiscountForEmployee() {
        final BillDetails billDetails = new BillDetails();
        final Item item = new Item("Milk", "GROCERIES", 2.00);
        final List<Item> items = new ArrayList<>();
        items.add(item);
        billDetails.setItems(items);
        billDetails.setOriginalCurrency(USD);
        billDetails.setTargetCurrency(INR);
        billDetails.setTotalAmount(2.00);
        billDetails.setUserType(EMPLOYEE);
        billDetails.setCustomerTenure(2);

        final Double discountedAmount = discountCalculationService.applyDiscounts(billDetails);
        Assertions.assertEquals(2.00, discountedAmount);
    }

    @Test
    void givenBillDetails_whenApplyDiscountsIsCalled_thenReturnTotalAmountToBePaidAfterDiscountForAffiliate() {
        final BillDetails billDetails = new BillDetails();
        final Item item = new Item("Milk", "GROCERIES", 2.00);
        final List<Item> items = new ArrayList<>();
        items.add(item);
        billDetails.setItems(items);
        billDetails.setOriginalCurrency(USD);
        billDetails.setTargetCurrency(INR);
        billDetails.setTotalAmount(2.00);
        billDetails.setUserType(AFFILIATE);
        billDetails.setCustomerTenure(2);

        final Double discountedAmount = discountCalculationService.applyDiscounts(billDetails);
        Assertions.assertEquals(2.00, discountedAmount);
    }

    @Test
    void givenBillDetails_whenApplyDiscountsIsCalled_thenReturnTotalAmountToBePaidAfterDiscountForCustomer() {
        final BillDetails billDetails = new BillDetails();
        final Item item = new Item("Milk", "GROCERIES", 2.00);
        final List<Item> items = new ArrayList<>();
        items.add(item);
        billDetails.setItems(items);
        billDetails.setOriginalCurrency(USD);
        billDetails.setTargetCurrency(INR);
        billDetails.setTotalAmount(2.00);
        billDetails.setUserType(CUSTOMER);
        billDetails.setCustomerTenure(2);

        final Double discountedAmount = discountCalculationService.applyDiscounts(billDetails);
        Assertions.assertEquals(2.00, discountedAmount);
    }

    @Test
    void givenNonGroceryBillDetails_whenApplyDiscountsIsCalled_thenReturnTotalAmountToBePaidAfterDiscountForEmployee() {
        final BillDetails billDetails = new BillDetails();
        final Item item = new Item("Mobile", "electronics", 630.00);
        final List<Item> items = new ArrayList<>();
        items.add(item);
        billDetails.setItems(items);
        billDetails.setOriginalCurrency(USD);
        billDetails.setTargetCurrency(INR);
        billDetails.setTotalAmount(630.00);
        billDetails.setUserType(EMPLOYEE);
        billDetails.setCustomerTenure(2);

        final Double discountedAmount = discountCalculationService.applyDiscounts(billDetails);
        Assertions.assertEquals(411.00, discountedAmount);
    }
}