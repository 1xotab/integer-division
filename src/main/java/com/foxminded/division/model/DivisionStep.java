package com.foxminded.division.model;

import java.util.Objects;

public class DivisionStep {
    private final int dividend;
    int remainder;
    private final int divisorMultiple;

    public DivisionStep(int dividend, int remainder, int divisorMultiple) {
        this.dividend = dividend;
        this.remainder = remainder;
        this.divisorMultiple = divisorMultiple;
    }


    public void setRemainder(int remainder) {
        this.remainder = remainder;
    }

    public int getDividend() {
        return dividend;
    }

    public int getRemainder() {
        return remainder;
    }

    public int getDivisorMultiple() {
        return divisorMultiple;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DivisionStep that = (DivisionStep) o;
        return dividend == that.dividend && remainder == that.remainder && divisorMultiple == that.divisorMultiple;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dividend, remainder, divisorMultiple);
    }

    @Override
    public String toString() {
        return "DivisionStep{" +
            "dividend=" + dividend +
            ", remainder=" + remainder +
            ", divisorMultiple=" + divisorMultiple +
            '}';
    }
}

