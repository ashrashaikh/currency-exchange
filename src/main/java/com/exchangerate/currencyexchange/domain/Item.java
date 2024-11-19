package com.exchangerate.currencyexchange.domain;


/**
 * Item
 */
public record Item (
        String name,
        String category,
        Double price) {
}
