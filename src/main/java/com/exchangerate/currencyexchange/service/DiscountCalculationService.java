package com.exchangerate.currencyexchange.service;

import com.exchangerate.currencyexchange.domain.BillDetails;
import com.exchangerate.currencyexchange.domain.Item;
import org.springframework.stereotype.Service;

import static com.exchangerate.currencyexchange.util.CurrencyExchangeConstants.*;
import static com.exchangerate.currencyexchange.util.CurrencyExchangeConstants.CUSTOMER;

/**
 * This service calculates discounts
 */
@Service
public class DiscountCalculationService {
    /**
     * Applies discount on total amount as per the discount scheme
     * @param billDetails bill details submitted in request
     * @return the total discounted amount
     */
    public double applyDiscounts(final BillDetails billDetails) {

        // Segregate the total amount of grocery and non-grocery
        // items from the given list of items in the supplied bill
        double groceryTotal = billDetails.getItems()
                .stream()
                .filter(item -> GROCERIES.equalsIgnoreCase(item.category()))
                .mapToDouble(Item::price)
                .sum();
        double nonGroceryTotal = billDetails.getItems()
                .stream()
                .filter(item -> !GROCERIES.equalsIgnoreCase(item.category()))
                .mapToDouble(Item::price)
                .sum();

        // Calculate the percentage-based discount amount for non-grocery items
        double percentageDiscountOnNonGrocery = 0;
        final String userType = billDetails.getUserType();
        double totalAmount = billDetails.getTotalAmount();

        if (EMPLOYEE.equalsIgnoreCase(userType)) {
            // Checks if the user is an employee, apply 30% discount
            percentageDiscountOnNonGrocery = 0.30;
        } else if (AFFILIATE.equalsIgnoreCase(userType)) {
            // Checks if the user is an affiliate, apply 10% discount
            percentageDiscountOnNonGrocery = 0.10;
        } else if (CUSTOMER.equalsIgnoreCase(userType) && billDetails.getCustomerTenure() > 2) {
            // Checks if the user has been a customer for over 2 years, apply 5% discount
            percentageDiscountOnNonGrocery = 0.05;
        }

        double nonGroceryTotalAfterDiscount = nonGroceryTotal * percentageDiscountOnNonGrocery;

        // For every $100 on the bill, there is a flat $5 discount
        double additionalDiscount = Math.floor(totalAmount / 100) * 5;
        double finalDiscount = nonGroceryTotalAfterDiscount + additionalDiscount;

        return totalAmount - finalDiscount;
    }
}
