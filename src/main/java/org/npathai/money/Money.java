package org.npathai.money;

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
        if (!currency.equals(other.currency)) {
            throw new CurrencyMismatchException(currency, other.currency);
        }
        return amount == other.amount;
    }

    public boolean isGreaterThan(Money other) {
        if (!currency.equals(other.currency)) {
            throw new CurrencyMismatchException(currency, other.currency);
        }

        return amount > other.amount;
    }

    public boolean isLessThan(Money other) {
        if (!currency.equals(other.currency)) {
            throw new CurrencyMismatchException(currency, other.currency);
        }
        return amount < other.amount;
    }
}
