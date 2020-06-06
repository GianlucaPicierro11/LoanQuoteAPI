package com.github.hansonhsc.loan.quote;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.github.hansonhsc.loan.quote.LoanQuoteApplication.createLoanQuoteCalculator;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoanQuoteApplicationCsvTest {
    static final String MARKET_CSV = "src/test/resources/market.csv";

    private static final List<Lender> MARKET_CSV_LENDERS = Arrays.asList(
            new Lender("Jane", new BigDecimal("0.069"), 480),
            new Lender("Fred", new BigDecimal("0.071"), 520),
            new Lender("Angela", new BigDecimal("0.071"), 60),
            new Lender("Dave", new BigDecimal("0.074"), 140),
            new Lender("Bob", new BigDecimal("0.075"), 640),
            new Lender("John", new BigDecimal("0.081"), 320),
            new Lender("Mary", new BigDecimal("0.104"), 170)
    );

    @Test
    void testWithInvalidFilePath() {
        assertThrows(
                LoanQuoteParameterValidationException.class,
                () -> createLoanQuoteCalculator("invalid"),
                "Creating loan quote calculator with non-existent CSV should throw"
        );
    }

    @Test
    void testWithValidCsv() throws LoanQuoteParameterValidationException {
        final LoanQuoteCalculator loanQuoteCalculator = createLoanQuoteCalculator(MARKET_CSV);

        assertNotNull(loanQuoteCalculator, "Creating loan quote calculator with valid CSV should create calculator");

        assertEquals(
                MARKET_CSV_LENDERS,
                loanQuoteCalculator.getLenders(),
                "Loan quote calculator should have correct lenders in correct order"
        );
    }

    @Test
    void testWithNoHeaders() {
        assertThrows(
                LoanQuoteParameterValidationException.class,
                () -> createLoanQuoteCalculator("src/test/resources/with_no_headers.csv"),
                "Creating loan quote calculator with a CSV without headers should throw"
        );
    }

    @Test
    void testWithWrongOrderHeaders() throws LoanQuoteParameterValidationException {
        final LoanQuoteCalculator loanQuoteCalculator = createLoanQuoteCalculator("src/test/resources/wrong_order_headers.csv");

        assertNotNull(loanQuoteCalculator, "Creating loan quote calculator with a CSV with headers in wrong order should create calculator");

        assertEquals(
                MARKET_CSV_LENDERS,
                loanQuoteCalculator.getLenders(),
                "Loan quote calculator should have correct lenders in correct order"
        );
    }

    @Test
    void testWithMissingColumn() {
        assertThrows(
                LoanQuoteParameterValidationException.class,
                () -> createLoanQuoteCalculator("src/test/resources/missing_column.csv"),
                "Creating loan quote calculator with a CSV with missing column should throw"
        );
    }

    @Test
    void testWithEscapedComma() throws LoanQuoteParameterValidationException {
        final LoanQuoteCalculator loanQuoteCalculator = createLoanQuoteCalculator("src/test/resources/escaped_comma.csv");

        assertNotNull(loanQuoteCalculator, "Creating loan quote calculator with valid CSV with escaped comma should create calculator");

        assertEquals(
                Arrays.asList(
                        new Lender("Doe, Jane", new BigDecimal("0.069"), 480),
                        new Lender("Bob", new BigDecimal("0.075"), 640)
                ),
                loanQuoteCalculator.getLenders(),
                "Loan quote calculator should have correct lenders in correct order"
        );
    }

    @Test
    void testWithIncompleteRow() {
        assertThrows(
                LoanQuoteParameterValidationException.class,
                () -> createLoanQuoteCalculator("src/test/resources/incomplete_row.csv"),
                "Creating loan quote calculator with a CSV with incomplete row should throw"
        );
    }

    @Test
    void testWithMissingName() {
        assertThrows(
                LoanQuoteParameterValidationException.class,
                () -> createLoanQuoteCalculator("src/test/resources/missing_name.csv"),
                "Creating loan quote calculator with a CSV with missing name should throw"
        );
    }

    @Test
    void testWithColonsInsteadOfCommas() {
        assertThrows(
                LoanQuoteParameterValidationException.class,
                () -> createLoanQuoteCalculator("src/test/resources/colons.csv"),
                "Creating loan quote calculator with a CSV with colons instead of commas should throw"
        );
    }

    @Test
    void testWithNotACsv() {
        assertThrows(
                LoanQuoteParameterValidationException.class,
                () -> createLoanQuoteCalculator("README.md"),
                "Creating loan quote calculator with not a CSV should throw"
        );
    }
}
