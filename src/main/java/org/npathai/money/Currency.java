package org.npathai.money;

public class Currency {
    public static final Currency INR = new Currency("INR");
    public static final Currency USD = new Currency("USD");

    private final String code;

    Currency(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }
}
