package org.npathai.money;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MoneyTest {

    public static final Money INR_10 = Money.of(10, Currency.INR);
    private static final Money INR_11 = Money.of(11, Currency.INR);
    private static final Money USD_10 = Money.of(10, Currency.USD);
    private static final Money INR_0 = Money.of(0, Currency.INR);
    private static final Money INR_2 = Money.of(2, Currency.INR);
    private static final Money INR_9 = Money.of(9, Currency.INR);


    @Test
    public void creatingAMoneyInstance() {
        Money inr = Money.of(10, Currency.INR);
        assertThat(inr.toString()).isEqualTo("INR 10.00");

        Money zero = Money.of(0, Currency.INR);
        assertThat(zero.toString()).isEqualTo("INR 0.00");

        Money usd = Money.of(10, Currency.USD);
        assertThat(usd.toString()).isEqualTo("USD 10.00");

        Money inrDouble = Money.of(10.00d, Currency.INR);
        assertThat(inrDouble.toString()).isEqualTo("INR 10.00");
    }

    @Nested
    class Comparison {

        @Test
        public void sameCurrencyEquality() {
            assertThat(INR_10.isEqualTo(INR_10)).isTrue();

            Money otherInr = Money.of(10, Currency.INR);
            assertThat(INR_10.isEqualTo(otherInr)).isTrue();
            assertThat(otherInr.isEqualTo(INR_10)).isTrue();

            assertThat(INR_10.isEqualTo(INR_11)).isFalse();
        }

        @Test
        public void isEqualThrowsExceptionWhenCurrenciesDiffer() {
            assertThatThrownBy(() -> INR_10.isEqualTo(USD_10))
                    .isInstanceOf(CurrencyMismatchException.class)
                    .hasMessage("Currencies differ: INR/USD");
        }

        @Test
        public void sameCurrencyGreaterThanComparison() {
            assertThat(INR_11.isGreaterThan(INR_10)).isTrue();
            assertThat(INR_11.isGreaterThan(INR_11)).isFalse();
            assertThat(INR_10.isGreaterThan(INR_11)).isFalse();
        }

        @Test
        public void isGreaterThanThrowsExceptionWhenCurrenciesDiffer() {
            assertThatThrownBy(() -> INR_11.isGreaterThan(USD_10))
                    .isInstanceOf(CurrencyMismatchException.class)
                    .hasMessage("Currencies differ: INR/USD");
        }

        @Test
        public void sameCurrencyLessThanComparison() {
            assertThat(INR_10.isLessThan(INR_11)).isTrue();
            assertThat(INR_10.isLessThan(INR_10)).isFalse();
            assertThat(INR_11.isLessThan(INR_10)).isFalse();
        }

        @Test
        public void isLessThanThrowsExceptionWhenCurrenciesDiffer() {
            assertThatThrownBy(() -> INR_11.isLessThan(USD_10))
                    .isInstanceOf(CurrencyMismatchException.class)
                    .hasMessage("Currencies differ: INR/USD");
        }
    }

    @Nested
    static
    class Arithmetic {

        @Test
        public void sameCurrencyPlus() {
            assertThat(INR_10.plus(INR_11)).isEqualTo(Money.of(21, Currency.INR));
            assertThat(INR_11.plus(INR_10)).isEqualTo(Money.of(21, Currency.INR));
        }

        @Test
        public void sameCurrencyPlusWithMultipleValues() {
            assertThat(INR_10.plus(INR_11, INR_2, INR_9)).isEqualTo(Money.of(32, Currency.INR));
            assertThat(INR_0.plus(INR_0, INR_11, INR_9)).isEqualTo(Money.of(20, Currency.INR));
        }

        @Test
        public void plusThrowsExceptionWhenCurrenciesDiffer() {
            assertThatThrownBy(() -> INR_10.plus(USD_10))
                    .isInstanceOf(CurrencyMismatchException.class)
                    .hasMessage("Currencies differ: INR/USD");
        }

        @Test
        public void plusWithMultipleValuesThrowsExceptionWhenCurrenciesDiffer() {
            assertThatThrownBy(() -> INR_10.plus(USD_10, INR_2))
                    .isInstanceOf(CurrencyMismatchException.class)
                    .hasMessage("Currencies differ: INR/USD");

            assertThatThrownBy(() -> USD_10.plus(USD_10, INR_2))
                    .isInstanceOf(CurrencyMismatchException.class)
                    .hasMessage("Currencies differ: USD/INR");
        }

        @Test
        public void sameCurrencyMinus() {
            assertThat(INR_10.minus(INR_11)).isEqualTo(Money.of(-1, Currency.INR));
            assertThat(INR_11.minus(INR_10)).isEqualTo(Money.of(1, Currency.INR));
            assertThat(Money.of(-1, Currency.INR).minus(Money.of(-1, Currency.INR))).isEqualTo(INR_0);
        }

        @Test
        public void minusWithMultipleValues() {
            assertThat(INR_11.minus(INR_2, INR_9)).isEqualTo(INR_0);
            assertThat(Money.of(12, Currency.INR).minus(INR_2, INR_9)).isEqualTo(Money.of(1, Currency.INR));
            assertThat(INR_11.minus(INR_2, INR_0)).isEqualTo(INR_9);
            assertThat(INR_10.minus(INR_2, INR_9)).isEqualTo(Money.of(-1, Currency.INR));
        }

        @Test
        public void minusThrowsExceptionWhenCurrenciesDiffer() {
            assertThatThrownBy(() -> INR_10.minus(USD_10))
                    .isInstanceOf(CurrencyMismatchException.class)
                    .hasMessage("Currencies differ: INR/USD");
        }

        @Test
        public void minusWithMultipleValuesThrowsExceptionWhenCurrenciesDiffer() {
            assertThatThrownBy(() -> INR_10.minus(USD_10, INR_2))
                    .isInstanceOf(CurrencyMismatchException.class)
                    .hasMessage("Currencies differ: INR/USD");

            assertThatThrownBy(() -> USD_10.minus(USD_10, INR_2))
                    .isInstanceOf(CurrencyMismatchException.class)
                    .hasMessage("Currencies differ: USD/INR");
        }

        @Test
        public void sameCurrencyMultiply() {
            assertThat(INR_11.multipliedBy(INR_10)).isEqualTo(Money.of(110, Currency.INR));
            assertThat(INR_10.multipliedBy(INR_11)).isEqualTo(Money.of(110, Currency.INR));
            assertThat(INR_10.multipliedBy(INR_0)).isEqualTo(INR_0);
            assertThat(INR_10.multipliedBy(Money.of(-1, Currency.INR))).isEqualTo(Money.of(-10, Currency.INR));
        }

        @Test
        public void multipliedByThrowsExceptionWhenCurrenciesDiffer() {
            assertThatThrownBy(() -> INR_10.multipliedBy(USD_10))
                    .isInstanceOf(CurrencyMismatchException.class)
                    .hasMessage("Currencies differ: INR/USD");
        }

        @Test
        public void sameCurrencyDivision() {
            assertThat(INR_10.dividedBy(INR_2)).isEqualTo(Money.of(5, Currency.INR));
            assertThat(INR_9.dividedBy(INR_2)).isEqualTo(Money.of(4, Currency.INR));
            assertThat(INR_10.dividedBy(INR_10)).isEqualTo(Money.of(1, Currency.INR));
        }

        // TODO check for divide by 0
        @Test
        public void divisionTruncatesDecimals() {
            assertThat(INR_11.dividedBy(INR_2)).isEqualTo(Money.of(5, Currency.INR));
            assertThat(INR_11.dividedBy(INR_10)).isEqualTo(Money.of(1, Currency.INR));
            assertThat(INR_2.dividedBy(INR_10)).isEqualTo(INR_0);
        }

        @Test
        public void negatedReturnsMoneyWithNegatedValue() {
            assertThat(INR_0.negated()).isEqualTo(INR_0);
            assertThat(INR_2.negated()).isEqualTo(Money.of(-2, Currency.INR));
            assertThat(Money.of(-2, Currency.INR).negated()).isEqualTo(INR_2);
        }

        @Test
        public void absReturnsAbsoluteValue() {
            assertThat(INR_0.abs()).isSameAs(INR_0);
            assertThat(INR_2.abs()).isSameAs(INR_2);
            assertThat(Money.of(-2, Currency.INR).abs()).isEqualTo(INR_2);
        }

    }

    @Nested
    class Queries {

        @Test
        public void isZero() {
            assertThat(INR_0.isZero()).isTrue();
            assertThat(INR_2.isZero()).isFalse();
            assertThat(Money.of(-1, Currency.INR).isZero()).isFalse();
        }

        @Test
        public void isNegative() {
            assertThat(INR_0.isNegative()).isFalse();
            assertThat(INR_2.isNegative()).isFalse();
            assertThat(Money.of(-1, Currency.INR).isNegative()).isTrue();
        }

        @Test
        public void isPositive() {
            assertThat(INR_0.isPositive()).isFalse();
            assertThat(INR_2.isPositive()).isTrue();
            assertThat(Money.of(-1, Currency.INR).isPositive()).isFalse();
        }

        @Test
        public void isPositiveOrZero() {
            assertThat(INR_0.isPositiveOrZero()).isTrue();
            assertThat(INR_2.isPositiveOrZero()).isTrue();
            assertThat(Money.of(-1, Currency.INR).isPositiveOrZero()).isFalse();
        }

        @Test
        public void isNegativeOrZero() {
            assertThat(INR_0.isNegativeOrZero()).isTrue();
            assertThat(INR_2.isNegativeOrZero()).isFalse();
            assertThat(Money.of(-1, Currency.INR).isNegativeOrZero()).isTrue();
        }
    }
}
