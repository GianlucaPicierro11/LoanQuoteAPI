package com.github.hansonhsc.loan.quote;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.github.hansonhsc.loan.quote.LoanQuoteApplicationCsvTest.MARKET_CSV;
import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class LoanQuoteApplicationTestCase {
    protected abstract List<String> runApplication(String... args);

    private void assertApplicationOutput(final int requestedAmount, final String rate, final String monthlyRepayment, final String totalRepayment) {
        final List<String> output = runApplication(MARKET_CSV, Integer.toString(requestedAmount));

        assertEquals(
                Arrays.asList(
                        "Requested amount: £" + requestedAmount,
                        "Rate: " + rate + "%",
                        "Monthly repayment: £" + monthlyRepayment,
                        "Total repayment: £" + totalRepayment
                ),
                output,
                "Output has expected lines"
        );
    }

    private void assertErrorMessage(final String errorMessage, final String... args) {
        final List<String> output = runApplication(args);

        assertEquals(
                Arrays.asList(errorMessage, "Usage: java -jar [loan_quote_jar_file] [market_file] [loan_amount]"),
                output,
                "Running application with no args should give error message"
        );
    }

    @Test
    void testNoArgs() {
        assertErrorMessage("Invalid number of arguments: 0. Expected: 2");
    }

    @Test
    void test1Arg() {
        assertErrorMessage("Invalid number of arguments: 1. Expected: 2", MARKET_CSV);
    }

    @Test
    void testExtraArg() {
        assertErrorMessage("Invalid number of arguments: 3. Expected: 2", MARKET_CSV, "1000", "extra");
    }

    @Test
    void testInvalidCsvPath() {
        assertErrorMessage("Invalid market file: invalid", "invalid", "1000");
    }

    @Test
    void testInvalidCsvFile() {
        assertErrorMessage("Unable to parse invalid market file: Error capturing CSV header!", "README.md", "1000");
    }

    @Test
    void test1000Amount() {
        assertApplicationOutput(1000, "7.0", "30.88", "1111.65");
    }

    @Test
    void testAmountNotANumber() {
        assertErrorMessage("Invalid loan amount format, must be an integer: NOT_A_NUMBER", MARKET_CSV, "NOT_A_NUMBER");
    }

    @Test
    void testAmountWithDecimalPlaces() {
        assertErrorMessage("Invalid loan amount format, must be an integer: 1000.00", MARKET_CSV, "1000.00");
    }

    @Test
    void testAmountWithLeadingZero() {
        assertErrorMessage("Invalid loan amount format, must be an integer without leading zeroes: 01000", MARKET_CSV, "01000");
    }

    @Test
    void testNegativeAmount() {
        assertErrorMessage("Invalid loan amount, must be any 100 increment between 1000-15000 inclusive: -1000", MARKET_CSV, "-1000");
    }

    @Test
    void testZeroAmount() {
        assertErrorMessage("Invalid loan amount, must be any 100 increment between 1000-15000 inclusive: 0", MARKET_CSV, "0");
    }

    @Test
    void test999Amount() {
        assertErrorMessage("Invalid loan amount, must be any 100 increment between 1000-15000 inclusive: 999", MARKET_CSV, "999");
    }

    @Test
    void test1050Amount() {
        assertErrorMessage("Invalid loan amount, must be any 100 increment between 1000-15000 inclusive: 1050", MARKET_CSV, "1050");
    }

    @Test
    void test15000Amount() {
        assertErrorMessage("Insufficient offers from lenders to satisfy the loan. Try a smaller loan amount.", MARKET_CSV, "15000");
    }

    @Test
    void testLeadingPlus() {
        assertErrorMessage("Invalid loan amount format, must be an integer without leading plus: +1000", MARKET_CSV, "+1000");
    }
}
