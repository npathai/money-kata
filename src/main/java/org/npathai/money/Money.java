package org.npathai.money;

public class Money {


    private final long amount;
    private final Currency currency;

    public Money(long amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public static Money of(long amount, Currency currency) {
        return new Money(amount, currency);
    }

    @Override
    public String toString() {
        return currency + " " + amount;
    }
}
