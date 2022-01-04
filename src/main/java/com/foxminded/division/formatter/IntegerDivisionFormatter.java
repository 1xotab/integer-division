package com.foxminded.division.formatter;

import com.foxminded.division.model.DivisionResult;
import com.foxminded.division.model.DivisionStep;

public class IntegerDivisionFormatter {
    private static final String SPACE = " ";
    private static final String MINUS = "_";
    private static final String NEWLINE = "\n";
    private static final String DASH = "-";
    private static final String VERTICALLINE = "|";

    public String formatDivisionResult(DivisionResult divisionResult) {
//        if (divisionResult.getDividend() < divisionResult.getDivisor()) {
//         return createDivisionStepWhenDividendLessThenDivisor(divisionResult.getDividend(), divisionResult.getDivisor());
//        }
//        if (divisionResult.getDividend() != 0) {
//            return createDivisionHeader(divisionResult) + createDivisionBody(divisionResult);
//        } else
//            return "_0|" + divisionResult.getDivisor() + NEWLINE + " 0|" + DASH.repeat(calculateNumberLength(divisionResult.getDivisor())) + NEWLINE + " -|0";

        return createDivisionHeader(divisionResult)+createDivisionBody(divisionResult);
    }

    private String createDivisionHeader(DivisionResult divisionResult) {

        int dividend =divisionResult.getDividend();
        int divisor = divisionResult.getDivisor();
        int quotient = divisionResult.getQuotient();

        int firstStepDivisorMultiple = divisionResult.getSteps().get(0).getDivisorMultiple();
        int spaceAmountToEndLine = calculateNumberLength(divisionResult.getDividend()) - calculateNumberLength(firstStepDivisorMultiple);
        int dashAmountBelowDivisorMultiple = calculateNumberLength(firstStepDivisorMultiple);
        int dashAmountInAnswer = Math.max(calculateNumberLength(divisionResult.getQuotient()), calculateNumberLength(divisionResult.getDivisor()));
        int indentAmountBeforeZero = calculateNumberLength(dividend);
        int dashAmountBelowDivisor = calculateNumberLength(divisor);

        if(divisor<0 | quotient<0) dashAmountInAnswer++;


        if(divisionResult.getDividend()<divisionResult.getDivisor()) {

            return MINUS + dividend + VERTICALLINE + divisor
                + NEWLINE + SPACE.repeat(indentAmountBeforeZero)
                + 0 + VERTICALLINE
                + DASH.repeat(dashAmountBelowDivisor) + NEWLINE
                + SPACE.repeat(indentAmountBeforeZero)
                + DASH + VERTICALLINE + 0;
        }
        else {
            return MINUS + divisionResult.getDividend()
                + VERTICALLINE
                + divisionResult.getDivisor()
                + NEWLINE
                + SPACE + firstStepDivisorMultiple
                + SPACE.repeat(spaceAmountToEndLine)
                + VERTICALLINE
                + DASH.repeat(dashAmountInAnswer)
                + NEWLINE + SPACE
                + DASH.repeat(dashAmountBelowDivisorMultiple)
                + SPACE.repeat(spaceAmountToEndLine)
                + VERTICALLINE + divisionResult.getQuotient();

        }
    }

    private String createDivisionBody(DivisionResult divisionResult) {
        StringBuilder result = new StringBuilder();
        int indent = 2;
        int lastStepIndex = divisionResult.getSteps().size() - 1;

        for (int i = 1; i < divisionResult.getSteps().size(); i++, indent++) {
            result.append(formatStep(divisionResult.getSteps().get(i), indent));
        }
        result.append(NEWLINE);
        result.append(SPACE.repeat(indent-1));
        result.append(divisionResult.getSteps().get(lastStepIndex).getRemainder());

        return result.toString();
    }

    private String formatStep(DivisionStep step, int indent) {
        int dividendLength = calculateNumberLength(step.getDividend());

        return NEWLINE
            + SPACE.repeat(indent - 1)
            + MINUS + step.getDividend()
            + NEWLINE
            + SPACE.repeat(indent)
            + step.getDivisorMultiple()
            + NEWLINE
            + SPACE.repeat(indent)
            + DASH.repeat(dividendLength);
    }

//    private String createDivisionStepWhenDividendLessThenDivisor(int dividend, int divisor) {
//        int indentAmountBeforeZero = calculateNumberLength(dividend);
//        int dashAmountBelowDivisor = calculateNumberLength(divisor);
//
//        return MINUS + dividend + VERTICALLINE + divisor
//            + NEWLINE + SPACE.repeat(indentAmountBeforeZero)
//            + 0 + VERTICALLINE
//            + DASH.repeat(dashAmountBelowDivisor) + NEWLINE
//            + SPACE.repeat(indentAmountBeforeZero)
//            + DASH + VERTICALLINE + 0
//            + NEWLINE + SPACE + dividend;
//    }

    private int calculateNumberLength(int number) {
        return String.valueOf(Math.abs(number)).length();
    }
}

