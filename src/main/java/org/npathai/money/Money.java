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
        return new Money(amount, currency);
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

    private void checkCurrency(Money addToAdd) {
        if (!currency.equals(addToAdd.currency)) {
            throw new CurrencyMismatchException(currency, addToAdd.currency);
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

    public Money minus(Money moneyToSubtract) {
        checkCurrency(moneyToSubtract);
        return Money.of(amount - moneyToSubtract.amount, currency);
    }

    public Money multipliedBy(Money moneyToMultiplyWith) {
        checkCurrency(moneyToMultiplyWith);
        return Money.of(amount * moneyToMultiplyWith.amount, currency);
    }

    public Money dividedBy(Money moneyToDivideWith) {
        checkCurrency(moneyToDivideWith);
        return Money.of(amount / moneyToDivideWith.amount, currency);
    }

    public boolean isZero() {
        return amount == 0;
    }
}
