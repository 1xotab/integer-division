package com.foxminded.division.formatter;

import com.foxminded.division.model.DivisionResult;
import com.foxminded.division.model.DivisionStep;

import java.util.ArrayList;
import java.util.List;

public class IntegerDivisionFormatter {
    private static final String SPACE = " ";
    private static final String MINUS = "_";
    private static final String NEWLINE = "\n";
    private static final String DASH = "-";
    private static final String VERTICALLINE = "|";

    public String formatDivisionResult(DivisionResult divisionResult) {
        return createDivisionHeader(divisionResult) + createDivisionBody(divisionResult);
    }

    private String createDivisionHeader(DivisionResult divisionResult) {
        int dividend = divisionResult.getDividend();
        int divisor = divisionResult.getDivisor();
        int quotient = divisionResult.getQuotient();
        int firstStepDivisorMultiple = divisionResult.getSteps().get(0).getDivisorMultiple();
        int spaceAmountToEndLine = calculateSpaceAmountToEndLine(dividend,divisor,firstStepDivisorMultiple);
        int dashAmountBelowDivisorMultiple = calculateNumberLength(firstStepDivisorMultiple);
        int dashAmountInAnswer = calculateDashAmount(quotient, divisor);
        int indentOfFirstDivisorMultiple = calculateIndentOfFirstDivisorMultiple(dividend,divisor);

        if (dividend < divisor) {
            spaceAmountToEndLine = 0;
        } else {
            indentOfFirstDivisorMultiple = 1;
        }
        return MINUS + dividend + VERTICALLINE + divisor
            + NEWLINE
            + SPACE.repeat(indentOfFirstDivisorMultiple) + firstStepDivisorMultiple
            + SPACE.repeat(spaceAmountToEndLine) + VERTICALLINE + DASH.repeat(dashAmountInAnswer)
            + NEWLINE
            + SPACE.repeat(indentOfFirstDivisorMultiple) + DASH.repeat(dashAmountBelowDivisorMultiple)
            + SPACE.repeat(spaceAmountToEndLine) + VERTICALLINE + quotient;
    }

    private int calculateSpaceAmountToEndLine(int dividend,int divisor,int firstStepDivisorMultiple){
        int result = 0;

        if(dividend>=divisor){
          result = calculateNumberLength(dividend) - calculateNumberLength(firstStepDivisorMultiple);
        }
        return result;
    }

    private int calculateIndentOfFirstDivisorMultiple(int dividend,int divisor){
        int result = 1;

        if(dividend<divisor){
            result = calculateNumberLength(dividend);
        }
        return result;


    }

    private String createDivisionBody(DivisionResult divisionResult) {
        StringBuilder result = new StringBuilder();
        int[] dividendDigits = convertNumberToDigits(divisionResult.getDividend());
        ArrayList<Integer> indents = calculateIndents(divisionResult, dividendDigits);

        for (int i = 1; i < divisionResult.getSteps().size() - 1; i++) {
            DivisionStep currentStep = divisionResult.getSteps().get(i);
            int currentIndent = indents.get(i - 1);

            result.append(formatDivisionStep(currentStep, currentIndent));
        }

        List<DivisionStep> steps = divisionResult.getSteps();
        int lastRemainder = divisionResult.getSteps().get(steps.size() - 1).getRemainder();
        int remainderIntend = calculateRemainderIndent(dividendDigits, lastRemainder);

        result.append(NEWLINE).append(SPACE.repeat(remainderIntend)).append(lastRemainder);

        return result.toString();
    }

    private String formatDivisionStep(DivisionStep step, int indent) {
        int dividendLength = calculateNumberLength(step.getElementaryDividend());
        int sizeDifferenceBetweenDividendAndDivisorMultiple = dividendLength - calculateNumberLength(step.getDivisorMultiple());

        return NEWLINE
            + SPACE.repeat(indent - 2)
            + MINUS + step.getElementaryDividend()
            + NEWLINE
            + SPACE.repeat(indent - 1 + sizeDifferenceBetweenDividendAndDivisorMultiple)
            + step.getDivisorMultiple()
            + NEWLINE
            + SPACE.repeat(indent - 1)
            + DASH.repeat(dividendLength);
    }

    private ArrayList<Integer> calculateIndents(DivisionResult divisionResult, int[] dividendDigits) {
        ArrayList<Integer> intends = new ArrayList<>();
        int indent = 2;
        int measuredLineLength = (MINUS + divisionResult.getSteps().get(1).getElementaryDividend()).length();

        for (int i = 1; i < divisionResult.getSteps().size() - 1; i++) {
            DivisionStep lastStep = divisionResult.getSteps().get(i - 1);
            int previousRemainder = lastStep.getRemainder();
            int previousDivisorMultiple = lastStep.getDivisorMultiple();
            int elementaryDividend = divisionResult.getSteps().get(i).getElementaryDividend();

            indent = indent + calculateNumberLength(previousDivisorMultiple) - calculateNumberLength(previousRemainder);

            if (previousRemainder == 0) {
                indent = 1 + indent + calculateZeroStepsIndent(dividendDigits, measuredLineLength - 1);
            }
            measuredLineLength = (SPACE.repeat(indent - 2) + MINUS + elementaryDividend).length();

            intends.add(indent);
        }
        return intends;
    }

    private int calculateRemainderIndent(int[] dividendDigits, int lastRemainder) {
        int lastRemainderLength = calculateNumberLength(lastRemainder);

        return  1 + dividendDigits.length - lastRemainderLength;
    }

    private int calculateDashAmount(int quotient, int divisor) {
        int divisorLength = calculateNumberLength(divisor) + (divisor < 0 ? 1 : 0);
        int quotientLength = calculateNumberLength(quotient) + (quotient < 0 ? 1 : 0);

        return Math.max(divisorLength,quotientLength);
    }

    private int calculateZeroStepsIndent(int[] dividendDigits, int start) {
        int counter = 0;

        for (int i = start; i < dividendDigits.length; i++) {
            if (dividendDigits[i] == 0) {
                counter++;
            } else {
                break;
            }
        }
        return counter;
    }

    private int[] convertNumberToDigits(int convertible) {
        return Integer.toString(convertible).chars().map(c -> c - '0').toArray();
    }

    private int calculateNumberLength(int number) {
        return String.valueOf(Math.abs(number)).length();
    }
}

