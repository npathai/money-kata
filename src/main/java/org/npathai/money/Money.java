package org.npathai.money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Money {

    private final long amount;
    private final Currency currency;

    public Money(long amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Money(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public static Money of(long amount, Currency currency) {
        return of((double) amount, currency);
    }

    public static Money of(BigDecimal amount, Currency currency) {
        return of(amount, currency, RoundingMode.UNNECESSARY);
    }

    private static Money of(BigDecimal amount, Currency currency, RoundingMode roundingMode) {
        amount = amount.setScale(currency.getDecimalPlaces(), roundingMode);
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

    public Money plus(Money... moniesToAdd) {
        Money total = this;
        for (Money moneyToAdd : moniesToAdd) {
            checkCurrency(moneyToAdd);
            total = total.plus(moneyToAdd);
        }
        return total;
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

    public Money minus(Money moneyToSubtract) {
        checkCurrency(moneyToSubtract);
        return Money.of(amount - moneyToSubtract.amount, currency);
    }

    public Money minus(Money...moniesToSubtract) {
        Money total = this;
        for (Money money : moniesToSubtract) {
            total = total.minus(money);
        }
        return total;
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

    public Money negated() {
        return Money.of(-amount, currency);
    }

    public Money abs() {
        return isNegative() ? negated() : this;
    }

    public boolean isNegative() {
        return amount < 0;
    }

    public boolean isPositive() {
        return amount > 0;
    }

    public boolean isPositiveOrZero() {
        return amount >= 0;
    }

    public boolean isNegativeOrZero() {
        return amount <= 0;
    }
}
