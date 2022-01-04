package com.foxminded.division.formatter;

import com.foxminded.division.calculator.IntegerDivisionCalculator;
import com.foxminded.division.model.DivisionResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IntegerDivisionFormatterTest {

    IntegerDivisionCalculator calculator = new IntegerDivisionCalculator();
    IntegerDivisionFormatter formatter = new IntegerDivisionFormatter();


    @Test
    void formatDivisionResult_shouldThrowException_whenFormatDivisionResultNull() {

        assertThrows(NullPointerException.class, () -> formatter.formatDivisionResult(null));
    }

    @Test
    void formatDivisionResult_shouldThrowException_whenDivisorIsZero() {

        assertThrows(ArithmeticException.class, () -> formatter.formatDivisionResult(calculator.calculateDivisionResult(1234, 0)));
    }

    @Test
    void formatDivisionResult_shouldPrintOneStep_whenDividendIsZero() {

        DivisionResult divisionResult = calculator.calculateDivisionResult(0, 2000);
        String actual = formatter.formatDivisionResult(divisionResult);

        String expected =
                "_0|2000" + "\n" +
                " 0|----" + "\n" +
                " -|0" + "\n" +
                " 0";

        assertEquals(expected, actual);
    }

    @Test
    void formatDivisionResult_shouldFormatDivisionResult_whenDividendMoreThanDivisor() {
        DivisionResult result = calculator.calculateDivisionResult(1234, 100);
        String formattedResult = formatter.formatDivisionResult(result);

        String expected =
                "_1234|100" + "\n" +
                " 100 |---" + "\n" +
                " --- |12" + "\n" +
                " _234" + "\n" +
                "  200" + "\n" +
                "  ---" + "\n" +
                "  34";

        assertEquals(expected, formattedResult);
    }

//    @Test
//    void formatDivisionResult_shouldPutCorrectNumberDashes_whenDivisorGreaterThanQuotient() {
//
//        DivisionResult result = calculator.calculateDivisionResult(100, 20);
//        String formattedResult = formatter.formatDivisionResult(result);
//
//        boolean substringAvailability = formattedResult.contains(" 100|--");
//
//        assertTrue(substringAvailability);
//    }
//
//    @Test
//    void formatDivisionResult_shouldPutCorrectNumberDashes_whenDivisorLessThanQuotient() {
//
//        DivisionResult result = calculator.calculateDivisionResult(100, 5);
//        String formattedResult = formatter.formatDivisionResult(result);
//
//        boolean substringAvailability = formattedResult.contains(" 10 |--");
//
//        assertTrue(substringAvailability);
//    }

    @Test
    void formatDivisionResult_shouldFormatStringWithSeveralSteps_whenDividendIsLessThanZero() {

        DivisionResult divisionResult = calculator.calculateDivisionResult(642, -2);
        String actual = formatter.formatDivisionResult(divisionResult);

        String expected =
            "_642|-2" + "\n" +
                " 6  |----" + "\n" +
                " -  |-321" + "\n" +
                " _4" + "\n" +
                "  4" + "\n" +
                "  -" + "\n" +
                "  _2" + "\n" +
                "   2" + "\n" +
                "   -" + "\n" +
                "   0";

        assertEquals(expected, actual);
    }

    @Test
    void formatDivisionResult_shouldFormatDivisionResult_whenDividendHasZeros() {

        DivisionResult divisionResult = calculator.calculateDivisionResult(300010003, 2);
        String actual = formatter.formatDivisionResult(divisionResult);

        String expected =
                "_300010003|2" + "\n" +
                " 2        |---------" + "\n" +
                " -        |150005001" + "\n" +
                " _10" + "\n" +
                "  10" + "\n" +
                "  --" + "\n" +
                "  _10" + "\n" +
                "   10" + "\n" +
                "   --" + "\n" +
                "   _3" + "\n" +
                "    2" + "\n" +
                "    -" + "\n" +
                "    1";

        assertEquals(expected, actual);
    }

    @Test
    void formatDivisionResult_shouldFormatDivisionResult_whenDividendHasManyZeros() {

        DivisionResult divisionResult = calculator.calculateDivisionResult(30000003, 15);
        String actual = formatter.formatDivisionResult(divisionResult);

        String expected =
            "_30000003|15" + "\n" +
                " 30      |-------" + "\n" +
                " --      |2000000" + "\n" +
                " 3";


        assertEquals(expected, actual);
    }
}


