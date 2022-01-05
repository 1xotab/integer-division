package com.foxminded.division.calculator;

import com.foxminded.division.model.DivisionResult;
import com.foxminded.division.model.DivisionStep;

import java.util.ArrayList;

public class IntegerDivisionCalculator {

    public DivisionResult calculateDivisionResult(int dividend, int divisor) {

        dividend = Math.abs(dividend);

        int[] dividendDigits = convertNumberToDigits(dividend);
        ArrayList<DivisionStep> divisionSteps = new ArrayList<>();

        if(dividend<divisor){
            divisionSteps.add(new DivisionStep(dividend,dividend,0));
        }

        int remainder = 0;

        int counter = -1;

        for (int localDividend : dividendDigits) {

            counter++;
            remainder = combineNumbers(remainder, localDividend);
            if (remainder >= divisor) {
                divisionSteps.add(buildDivisionStep(remainder, divisor));
                remainder = remainder % divisor;
            }

            if (remainder < divisor && counter == dividendDigits.length - 1) {
                divisionSteps.get(divisionSteps.size() - 1).setRemainder(remainder);
            }
        }
            int quotient = dividend / divisor;

            return new DivisionResult(dividend, divisor, quotient, divisionSteps);

    }
    private int[] convertNumberToDigits(int convertible) {
        return Integer.toString(convertible).chars().map(c -> c - '0').toArray();
    }

    private int combineNumbers(int x, int y) {
        return x * 10 + y;
    }

    private DivisionStep buildDivisionStep(int dividend, int divisor) {
        int stepRemainder = dividend % divisor;
        int stepDivisorMultiple = dividend - stepRemainder;

        return new DivisionStep(dividend, stepRemainder, stepDivisorMultiple);
    }
}

