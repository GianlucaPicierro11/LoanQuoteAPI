package it.loanquote.services.quote;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoanQuoteCalculatorLenderSortingTest {
    @Test
    void testNoLender() {
        assertEquals(Collections.emptyList(), new LoanQuoteCalculator(Collections.emptyList()).getLenders());
    }

    @Test
    void testOneLender() {
        assertEquals(
                Collections.singletonList(
                        new Lender("Bob", new BigDecimal("0.12"), 6000)
                ),
                new LoanQuoteCalculator(
                        Collections.singletonList(
                                new Lender("Bob", new BigDecimal("0.12"), 6000)
                        )
                ).getLenders()
        );
    }

    @Test
    void testTwoLendersWithDifferentRates() {
        assertEquals(
                Arrays.asList(
                        new Lender("Fred", new BigDecimal("0.02"), 6000),
                        new Lender("Bob", new BigDecimal("0.12"), 6000)
                ),
                new LoanQuoteCalculator(
                        Arrays.asList(
                                new Lender("Bob", new BigDecimal("0.12"), 6000),
                                new Lender("Fred", new BigDecimal("0.02"), 6000)
                        )
                ).getLenders()
        );
    }

    @Test
    void testTwoLendersWithSameRatesDifferentAmount() {
        assertEquals(
                Arrays.asList(
                        new Lender("Fred", new BigDecimal("0.12"), 7000),
                        new Lender("Bob", new BigDecimal("0.12"), 6000)
                ),
                new LoanQuoteCalculator(
                        Arrays.asList(
                                new Lender("Bob", new BigDecimal("0.12"), 6000),
                                new Lender("Fred", new BigDecimal("0.12"), 7000)
                        )
                ).getLenders()
        );
    }

    @Test
    void testTwoLendersWithSameRatesSameAmount() {
        assertEquals(
                Arrays.asList(
                        new Lender("Bob", new BigDecimal("0.12"), 6000),
                        new Lender("Fred", new BigDecimal("0.12"), 6000)
                ),
                new LoanQuoteCalculator(
                        Arrays.asList(
                                new Lender("Bob", new BigDecimal("0.12"), 6000),
                                new Lender("Fred", new BigDecimal("0.12"), 6000)
                        )
                ).getLenders()
        );
    }

    @Test
    void test5LendersWith2SameRateDifferentAmount() {
        assertEquals(
                Arrays.asList(
                        new Lender("Jane", new BigDecimal("0.069"), 480),
                        new Lender("Fred", new BigDecimal("0.071"), 520),
                        new Lender("Angela", new BigDecimal("0.071"), 60),
                        new Lender("Dave", new BigDecimal("0.074"), 140),
                        new Lender("Bob", new BigDecimal("0.075"), 640)
                ),
                new LoanQuoteCalculator(
                        Arrays.asList(
                                new Lender("Bob", new BigDecimal("0.075"), 640),
                                new Lender("Jane", new BigDecimal("0.069"), 480),
                                new Lender("Fred", new BigDecimal("0.071"), 520),
                                new Lender("Dave", new BigDecimal("0.074"), 140),
                                new Lender("Angela", new BigDecimal("0.071"), 60)
                        )
                ).getLenders()
        );
    }

    @Test
    void test5LendersWithSameRateButDifferentAmount() {
        assertEquals(
                Arrays.asList(
                        new Lender("Jane", new BigDecimal("0.07"), 5),
                        new Lender("Fred", new BigDecimal("0.07"), 4),
                        new Lender("Bob", new BigDecimal("0.07"), 3),
                        new Lender("Dave", new BigDecimal("0.07"), 2),
                        new Lender("Angela", new BigDecimal("0.07"), 1)
                ),
                new LoanQuoteCalculator(
                        Arrays.asList(
                                new Lender("Bob", new BigDecimal("0.07"), 3),
                                new Lender("Jane", new BigDecimal("0.07"), 5),
                                new Lender("Fred", new BigDecimal("0.07"), 4),
                                new Lender("Dave", new BigDecimal("0.07"), 2),
                                new Lender("Angela", new BigDecimal("0.07"), 1)
                        )
                ).getLenders()
        );
    }
}
