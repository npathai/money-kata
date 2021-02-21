package org.npathai.money;

public class CurrencyMismatchException extends RuntimeException {

    private final Currency firstCurrency;
    private final Currency secondCurrency;

    public CurrencyMismatchException(Currency firstCurrency, Currency secondCurrency) {
        super("Currencies differ: " + firstCurrency + "/" + secondCurrency);
        this.firstCurrency = firstCurrency;
        this.secondCurrency = secondCurrency;
    }
}
