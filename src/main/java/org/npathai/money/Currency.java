package org.npathai.money;

public class Currency {
    public static final Currency INR = new Currency("INR", 2);
    public static final Currency USD = new Currency("USD", 2);

    private final String code;
    private final int decimalPlaces;

    Currency(String code, int decimalPlaces) {
        this.code = code;
        this.decimalPlaces = decimalPlaces;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }

    public int getDecimalPlaces() {
        return decimalPlaces;
    }
}
