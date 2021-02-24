package org.npathai.money;

import java.util.Objects;

public class Money {


    private final long amount;
    private final Currency currency;

    public Money(long amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public static Money of(long amount, Currency currency) {
        checkArgument(amount >= 0, "amount should be a positive value");
        return new Money(amount, currency);
    }

    private static void checkArgument(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }

    @Override
    public String toString() {
        return currency + " " + amount;
    }

    public boolean isEqualTo(Money other) {
        checkCurrency(other);
        return amount == other.amount;
    }

    public boolean isGreaterThan(Money other) {
        checkCurrency(other);
        return amount > other.amount;
    }

    public boolean isLessThan(Money other) {
        checkCurrency(other);
        return amount < other.amount;
    }

    public Money plus(Money other) {
        checkCurrency(other);
        return Money.of(amount + other.amount, currency);
    }

    private void checkCurrency(Money other) {
        if (!currency.equals(other.currency)) {
            throw new CurrencyMismatchException(currency, other.currency);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return amount == money.amount &&
                Objects.equals(currency, money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }
}
