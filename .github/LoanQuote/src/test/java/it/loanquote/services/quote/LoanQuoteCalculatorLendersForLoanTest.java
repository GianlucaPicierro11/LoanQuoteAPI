package com.github.hansonhsc.loan.quote;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoanQuoteCalculatorLendersForLoanTest {
    @Test
    void test0Lenders() {
        assertThrows(
                InsufficientLendersException.class,
                () -> new LoanQuoteCalculator(Collections.emptyList()).getLendersForLoan(1000),
                "Calculator with no lenders should throw when getting quote for valid amount"
        );

        assertThrows(
                InsufficientLendersException.class,
                () -> new LoanQuoteCalculator(Collections.emptyList()).getLendersForLoan(0),
                "Calculator with no lenders should still throw when getting quote for 0 amount"
        );
    }

    @Test
    void test1LenderWith0Amount() throws InsufficientLendersException {
        final Map<Lender, Integer> expected = new HashMap<>();
        expected.put(new Lender("Bob", new BigDecimal("0.1"), 1000), 0);

        assertEquals(
                expected,
                new LoanQuoteCalculator(Collections.singletonList(
                        new Lender("Bob", new BigDecimal("0.1"), 1000)
                )).getLendersForLoan(0)
        );
    }

    @Test
    void test1LenderWithInsufficientAmount() throws InsufficientLendersException {
        assertThrows(
                InsufficientLendersException.class,
                () -> new LoanQuoteCalculator(Collections.singletonList(
                        new Lender("Bob", new BigDecimal("0.1"), 1000)
                )).getLendersForLoan(2000)
        );
    }

    @Test
    void test2LendersWithInsufficientAmount() throws InsufficientLendersException {
        assertThrows(
                InsufficientLendersException.class,
                () -> new LoanQuoteCalculator(Arrays.asList(
                        new Lender("Bob", new BigDecimal("0.1"), 1000),
                        new Lender("Fred", new BigDecimal("0.07"), 1500)
                )).getLendersForLoan(3000)
        );
    }

    @Test
    void test1LenderWithMoreThanAmount() throws InsufficientLendersException {
        final Map<Lender, Integer> expected = new HashMap<>();
        expected.put(new Lender("Bob", new BigDecimal("0.1"), 2000), 1000);

        assertEquals(
                expected,
                new LoanQuoteCalculator(Collections.singletonList(
                        new Lender("Bob", new BigDecimal("0.1"), 2000)
                )).getLendersForLoan(1000)
        );
    }

    @Test
    void test1LenderWithExactAmount() throws InsufficientLendersException {
        final Map<Lender, Integer> expected = new HashMap<>();
        expected.put(new Lender("Bob", new BigDecimal("0.1"), 1000), 1000);

        assertEquals(
                expected,
                new LoanQuoteCalculator(Collections.singletonList(
                        new Lender("Bob", new BigDecimal("0.1"), 1000)
                )).getLendersForLoan(1000)
        );
    }

    @Test
    void test2LendersWithMoreThanAmount() throws InsufficientLendersException {
        final Map<Lender, Integer> expected = new HashMap<>();
        expected.put(new Lender("Fred", new BigDecimal("0.07"), 1000), 1000);
        expected.put(new Lender("Bob", new BigDecimal("0.1"), 500), 200);

        assertEquals(
                expected,
                new LoanQuoteCalculator(Arrays.asList(
                        new Lender("Bob", new BigDecimal("0.1"), 500),
                        new Lender("Fred", new BigDecimal("0.07"), 1000)
                )).getLendersForLoan(1200)
        );
    }

    @Test
    void test2LendersWithExactAmount() throws InsufficientLendersException {
        final Map<Lender, Integer> expected = new HashMap<>();
        expected.put(new Lender("Fred", new BigDecimal("0.07"), 1000), 1000);
        expected.put(new Lender("Bob", new BigDecimal("0.1"), 500), 500);

        assertEquals(
                expected,
                new LoanQuoteCalculator(Arrays.asList(
                        new Lender("Bob", new BigDecimal("0.1"), 500),
                        new Lender("Fred", new BigDecimal("0.07"), 1000)
                )).getLendersForLoan(1500)
        );
    }

    @Test
    void test5LendersWith1LenderNeeded() throws InsufficientLendersException {
        final Map<Lender, Integer> expected = new HashMap<>();
        expected.put(new Lender("Bill", new BigDecimal("0.06"), 5000), 1000);

        assertEquals(
                expected,
                new LoanQuoteCalculator(Arrays.asList(
                        new Lender("Bob", new BigDecimal("0.1"), 500),
                        new Lender("Fred", new BigDecimal("0.07"), 1000),
                        new Lender("Bill", new BigDecimal("0.06"), 5000),
                        new Lender("Ted", new BigDecimal("0.07"), 1000),
                        new Lender("Mary", new BigDecimal("0.07"), 1000)
                )).getLendersForLoan(1000)
        );
    }

    @Test
    void test5LendersWith3LendersNeeded() throws InsufficientLendersException {
        final Map<Lender, Integer> expected = new HashMap<>();
        expected.put(new Lender("Bill", new BigDecimal("0.06"), 5000), 5000);
        expected.put(new Lender("Fred", new BigDecimal("0.065"), 1000), 1000);
        expected.put(new Lender("Ted", new BigDecimal("0.069"), 1000), 200);

        assertEquals(
                expected,
                new LoanQuoteCalculator(Arrays.asList(
                        new Lender("Bob", new BigDecimal("0.1"), 500),
                        new Lender("Fred", new BigDecimal("0.065"), 1000),
                        new Lender("Bill", new BigDecimal("0.06"), 5000),
                        new Lender("Ted", new BigDecimal("0.069"), 1000),
                        new Lender("Mary", new BigDecimal("0.07"), 1000)
                )).getLendersForLoan(6200)
        );
    }

    @Test
    void test5LendersWithAllLendersNeeded() throws InsufficientLendersException {
        final Map<Lender, Integer> expected = new HashMap<>();
        expected.put(new Lender("Bob", new BigDecimal("0.1"), 500), 500);
        expected.put(new Lender("Fred", new BigDecimal("0.065"), 1000), 1000);
        expected.put(new Lender("Bill", new BigDecimal("0.06"), 5000), 5000);
        expected.put(new Lender("Ted", new BigDecimal("0.069"), 1000), 1000);
        expected.put(new Lender("Mary", new BigDecimal("0.07"), 1000), 1000);

        assertEquals(
                expected,
                new LoanQuoteCalculator(Arrays.asList(
                        new Lender("Bob", new BigDecimal("0.1"), 500),
                        new Lender("Fred", new BigDecimal("0.065"), 1000),
                        new Lender("Bill", new BigDecimal("0.06"), 5000),
                        new Lender("Ted", new BigDecimal("0.069"), 1000),
                        new Lender("Mary", new BigDecimal("0.07"), 1000)
                )).getLendersForLoan(8500)
        );
    }

    @Test
    void test2LendersWithSameRate() throws InsufficientLendersException {
        final Map<Lender, Integer> expected = new HashMap<>();
        expected.put(new Lender("Bill", new BigDecimal("0.06"), 5000), 5000);
        expected.put(new Lender("Fred", new BigDecimal("0.065"), 1000), 1000);
        expected.put(new Lender("Mary", new BigDecimal("0.069"), 2000), 200);

        assertEquals(
                expected,
                new LoanQuoteCalculator(Arrays.asList(
                        new Lender("Bob", new BigDecimal("0.1"), 500),
                        new Lender("Fred", new BigDecimal("0.065"), 1000),
                        new Lender("Bill", new BigDecimal("0.06"), 5000),
                        new Lender("Ted", new BigDecimal("0.069"), 1000),
                        new Lender("Mary", new BigDecimal("0.069"), 2000)
                )).getLendersForLoan(6200)
        );
    }
}
