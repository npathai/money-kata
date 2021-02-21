package org.npathai.money;

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

        Money usd = Money.of(10, Currency.USD);
        assertThat(usd.toString()).isEqualTo("USD 10");
    }

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
}
