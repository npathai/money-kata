package org.npathai.money;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MoneyTest {

    public static final Money INR_10 = Money.of(10, Currency.INR);
    private static final Money INR_11 = Money.of(11, Currency.INR);
    private static final Money USD_10 = Money.of(10, Currency.USD);

    @Test
    public void creatingAMoneyInstance() {
        Money inr = Money.of(10, Currency.INR);
        assertThat(inr.toString()).isEqualTo("INR 10");

        Money zero = Money.of(0, Currency.INR);
        assertThat(zero.toString()).isEqualTo("INR 0");

        Money usd = Money.of(10, Currency.USD);
        assertThat(usd.toString()).isEqualTo("USD 10");
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
    class Arithmetic {

        @Test
        public void sameCurrencyPlus() {
            assertThat(INR_10.plus(INR_11)).isEqualTo(Money.of(21, Currency.INR));
            assertThat(INR_11.plus(INR_10)).isEqualTo(Money.of(21, Currency.INR));
        }

        @Test
        public void plusThrowsExceptionWhenCurrenciesDiffer() {
            assertThatThrownBy(() -> INR_10.plus(USD_10))
                    .isInstanceOf(CurrencyMismatchException.class)
                    .hasMessage("Currencies differ: INR/USD");
        }

        @Test
        public void sameCurrencyMinus() {
            assertThat(INR_10.minus(INR_11)).isEqualTo(Money.of(-1, Currency.INR));
            assertThat(INR_11.minus(INR_10)).isEqualTo(Money.of(1, Currency.INR));
        }

        @Test
        public void minusThrowsExceptionWhenCurrenciesDiffer() {
            assertThatThrownBy(() -> INR_10.minus(USD_10))
                    .isInstanceOf(CurrencyMismatchException.class)
                    .hasMessage("Currencies differ: INR/USD");
        }
    }
}
