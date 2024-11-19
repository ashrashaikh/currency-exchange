package com.exchangerate.currencyexchange.controller;

import com.exchangerate.currencyexchange.domain.BillDetails;
import com.exchangerate.currencyexchange.domain.BillResponse;
import com.exchangerate.currencyexchange.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Calculate the final payable amount for
 * a given amount as per the current exchange rates
 */
@RestController
@RequestMapping("/api")
public class CurrencyExchangeRateController {

    @Autowired
    private BillingService billingService;

    /**
     * Calculate the payable amount
     * @param billDetails bill details
     * @return BillResponse with target currency and final payable amount
     */
    @PostMapping("/calculate")
    public ResponseEntity<BillResponse> calculatePayableAmount(@Validated @RequestBody BillDetails billDetails) {
        return ResponseEntity.ok(billingService.finalBillAfterDiscount(billDetails));
    }
}
