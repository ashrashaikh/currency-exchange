package com.exchangerate.currencyexchange.service;

import com.exchangerate.currencyexchange.domain.BillDetails;
import com.exchangerate.currencyexchange.domain.BillResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This service calculates and returns the final bill after applying the discount.
 */
@Service
public class BillingService {

    @Autowired
    private OpenExchangeRatesService exchangeRatesService;

    @Autowired
    private DiscountCalculationService discountService;

    /**
     * Generates final bill after applying the discount
     * @param billDetails user bill details
     * @return final bill with target currency and the payable amount
     */
    public BillResponse finalBillAfterDiscount(final BillDetails billDetails) {
        final double payableAmountAfterDiscount = discountService.applyDiscounts(billDetails);
        final double latestExchangeRate = exchangeRatesService.getLatestExchangeRates(
                billDetails.getOriginalCurrency(), billDetails.getTargetCurrency());
        return new BillResponse(billDetails.getTargetCurrency(), payableAmountAfterDiscount * latestExchangeRate);
    }
}
