package com.github.hansonhsc.loan.quote;

import org.junit.jupiter.api.Test;

import static com.github.hansonhsc.loan.quote.LoanQuoteApplication.getLoanAmount;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoanQuoteApplicationLoanAmountTest {
    @Test
    void testNotANumber() {
        final LoanQuoteParameterValidationException e = assertThrows(
                LoanQuoteParameterValidationException.class, () -> getLoanAmount("NOT_A_NUMBER"),
                "Get loan amount with not a number should throw"
        );

        assertEquals(
                "Invalid loan amount format, must be an integer: NOT_A_NUMBER",
                e.getMessage(),
                "Get loan amount with not a number should throw with correct message"
        );
    }

    private void testInvalidLoanAmount(final int loanAmount) {
        final String loanAmountAsString = Integer.toString(loanAmount);

        final LoanQuoteParameterValidationException e = assertThrows(
                LoanQuoteParameterValidationException.class, () -> getLoanAmount(loanAmountAsString),
                "Get loan amount with " + loanAmountAsString + " should throw"
        );

        assertEquals(
                "Invalid loan amount, must be any 100 increment between 1000-15000 inclusive: " + loanAmountAsString,
                e.getMessage(),
                "Get loan amount with " + loanAmountAsString + " should throw with correct message"
        );
    }

    @Test
    void testInvalidLoanAmounts() {
        testInvalidLoanAmount(0);
        testInvalidLoanAmount(-1000);
        testInvalidLoanAmount(-1);
        testInvalidLoanAmount(999);
        testInvalidLoanAmount(900);
        testInvalidLoanAmount(1001);
        testInvalidLoanAmount(1050);
        testInvalidLoanAmount(20000);
        testInvalidLoanAmount(15001);
        testInvalidLoanAmount(15100);
    }

    @Test
    void test0Prefix() {
        final LoanQuoteParameterValidationException e = assertThrows(
                LoanQuoteParameterValidationException.class, () -> getLoanAmount("01000"),
                "Get loan amount with 01000 should throw"
        );

        assertEquals(
                "Invalid loan amount format, must be an integer without leading zeroes: 01000",
                e.getMessage(),
                "Get loan amount with 01000 should throw with correct message"
        );
    }

    @Test
    void testPlusPrefix() {
        final LoanQuoteParameterValidationException e = assertThrows(
                LoanQuoteParameterValidationException.class, () -> getLoanAmount("+1000"),
                "Get loan amount with +1000 should throw"
        );

        assertEquals(
                "Invalid loan amount format, must be an integer without leading plus: +1000",
                e.getMessage(),
                "Get loan amount with +1000 should throw with correct message"
        );
    }

    @Test
    void testDecimal() {
        final LoanQuoteParameterValidationException e = assertThrows(
                LoanQuoteParameterValidationException.class, () -> getLoanAmount("1000.00"),
                "Get loan amount with 1000.00 should throw"
        );

        assertEquals(
                "Invalid loan amount format, must be an integer: 1000.00",
                e.getMessage(),
                "Get loan amount with 1000.00 should throw with correct message"
        );
    }

    private void testValidLoanAmount(final int loanAmount) throws LoanQuoteParameterValidationException {
        assertEquals(loanAmount, getLoanAmount(Integer.toString(loanAmount)), "Get loan amount with " + loanAmount + " should return same number");
    }

    @Test
    void testValidLoanAmounts() throws LoanQuoteParameterValidationException {
        for (int amount = LoanQuoteApplication.MIN_LOAN_AMOUNT; amount <= LoanQuoteApplication.MAX_LOAN_AMOUNT; amount += LoanQuoteApplication.LOAN_AMOUNT_INCREMENT) {
            testValidLoanAmount(amount);
        }
    }
}
