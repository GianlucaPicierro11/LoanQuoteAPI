package it.loanquote.services.quote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class LoanQuoteCalculatorQuoteTest {
    @Test
    void testMonthlyRepaymentWith1Loan() throws InsufficientLendersException {
        // loan consists of a single lender
        final Map<Lender, Integer> loans = new HashMap<>();
        final Lender lender = new Lender("Fred", new BigDecimal("0.1254"), 5000);
        loans.put(lender, 1000);

        final LoanQuoteCalculator loanQuoteCalculator = spy(new LoanQuoteCalculator(Collections.singletonList(lender)));

        // return our mock loans when required
        doReturn(loans).when(loanQuoteCalculator).getLendersForLoan(1000);

        // it will call getMonthlyRepayment exactly once with for Fred's 0.1254 rate and 1000 loan amount
        doReturn(new BigDecimal(7)).when(loanQuoteCalculator).getMonthlyRepayment(new BigDecimal("0.1254"), 1000);

        doReturn(0.0).when(loanQuoteCalculator).getApproximateAnnualInterestRate(anyInt(), any());

        final LoanQuote quote = loanQuoteCalculator.getQuote(1000);

        // verify invocations
        verify(loanQuoteCalculator, times(1)).getMonthlyRepayment(any(), any());

        // verify repayments
        assertEquals("7.00", quote.getMonthlyRepayment().toString());
        assertEquals("252.00", quote.getTotalRepayment().toString());
    }

    @Test
    void testMonthlyRepaymentWith2Loans() throws InsufficientLendersException {
        // loan consists of 2 lenders
        final Map<Lender, Integer> loans = new HashMap<>();
        final Lender lender1 = new Lender("Fred", new BigDecimal("0.1254"), 5000);
        loans.put(lender1, 1000);

        final Lender lender2 = new Lender("Bob", new BigDecimal("0.07"), 2000);
        loans.put(lender2, 2000);

        final LoanQuoteCalculator loanQuoteCalculator = spy(new LoanQuoteCalculator(Arrays.asList(lender1, lender2)));

        // return our mock loans when required
        doReturn(loans).when(loanQuoteCalculator).getLendersForLoan(1000);

        // it will call getMonthlyRepayment twice
        doReturn(new BigDecimal(7)).when(loanQuoteCalculator).getMonthlyRepayment(new BigDecimal("0.1254"), 1000);
        doReturn(new BigDecimal(5)).when(loanQuoteCalculator).getMonthlyRepayment(new BigDecimal("0.07"), 2000);

        doReturn(0.0).when(loanQuoteCalculator).getApproximateAnnualInterestRate(anyInt(), any());

        final LoanQuote quote = loanQuoteCalculator.getQuote(3000);

        // verify invocations
        verify(loanQuoteCalculator, times(2)).getMonthlyRepayment(any(), any());

        // verify repayments
        assertEquals("12.00", quote.getMonthlyRepayment().toString());
        assertEquals("432.00", quote.getTotalRepayment().toString());
    }

    @Test
    void testMonthlyRepaymentRoundingUp() throws InsufficientLendersException {
        // loan consists of 2 lenders
        final Map<Lender, Integer> loans = new HashMap<>();
        final Lender lender1 = new Lender("Fred", new BigDecimal("0.1254"), 5000);
        loans.put(lender1, 1000);

        final Lender lender2 = new Lender("Bob", new BigDecimal("0.07"), 2000);
        loans.put(lender2, 2000);

        final LoanQuoteCalculator loanQuoteCalculator = spy(new LoanQuoteCalculator(Arrays.asList(lender1, lender2)));

        // return our mock loans when required
        doReturn(loans).when(loanQuoteCalculator).getLendersForLoan(1000);

        // it will call getMonthlyRepayment twice
        doReturn(new BigDecimal("12.243")).when(loanQuoteCalculator).getMonthlyRepayment(new BigDecimal("0.1254"), 1000);
        doReturn(new BigDecimal("7.283")).when(loanQuoteCalculator).getMonthlyRepayment(new BigDecimal("0.07"), 2000);

        doReturn(0.0).when(loanQuoteCalculator).getApproximateAnnualInterestRate(anyInt(), any());

        final LoanQuote quote = loanQuoteCalculator.getQuote(3000);

        // verify invocations
        verify(loanQuoteCalculator, times(2)).getMonthlyRepayment(any(), any());

        // verify repayments

        // 12.243 + 7.283 = 19.526
        assertEquals("19.53", quote.getMonthlyRepayment().toString());

        // 19.526*36 = 702.936 ; 19.53*36 = 703.08, we didn't not force the customer to overpay due to fractional pennies
        assertEquals("702.94", quote.getTotalRepayment().toString());
    }

    @Test
    void testMonthlyRepaymentRoundingDown() throws InsufficientLendersException {
        // loan consists of 2 lenders
        final Map<Lender, Integer> loans = new HashMap<>();
        final Lender lender1 = new Lender("Fred", new BigDecimal("0.1254"), 5000);
        loans.put(lender1, 1000);

        final Lender lender2 = new Lender("Bob", new BigDecimal("0.07"), 2000);
        loans.put(lender2, 2000);

        final LoanQuoteCalculator loanQuoteCalculator = spy(new LoanQuoteCalculator(Arrays.asList(lender1, lender2)));

        // return our mock loans when required
        doReturn(loans).when(loanQuoteCalculator).getLendersForLoan(1000);

        // it will call getMonthlyRepayment twice
        doReturn(new BigDecimal("12.243")).when(loanQuoteCalculator).getMonthlyRepayment(new BigDecimal("0.1254"), 1000);
        doReturn(new BigDecimal("7.281")).when(loanQuoteCalculator).getMonthlyRepayment(new BigDecimal("0.07"), 2000);

        doReturn(0.0).when(loanQuoteCalculator).getApproximateAnnualInterestRate(anyInt(), any());

        final LoanQuote quote = loanQuoteCalculator.getQuote(3000);

        // verify invocations
        verify(loanQuoteCalculator, times(2)).getMonthlyRepayment(any(), any());

        // verify repayments

        // 12.243 + 7.281 = 19.524
        assertEquals("19.52", quote.getMonthlyRepayment().toString());

        // 19.524*36 = 702.864 ; 19.52*36 = 702.72, the customer would pay for those fractional pennies in the final month
        assertEquals("702.87", quote.getTotalRepayment().toString());
    }

    private void testRateRounding(final String expectedRate, final double calculatedRate) throws InsufficientLendersException {
        final Map<Lender, Integer> loans = new HashMap<>();

        final Lender lender = new Lender("Fred", new BigDecimal("0.1254"), 1000);
        loans.put(lender, 1000);

        final LoanQuoteCalculator loanQuoteCalculator = spy(new LoanQuoteCalculator(Collections.singletonList(lender)));

        // return our mock loans when required
        doReturn(loans).when(loanQuoteCalculator).getLendersForLoan(1000);
        doReturn(calculatedRate).when(loanQuoteCalculator).getApproximateAnnualInterestRate(anyInt(), any());

        final LoanQuote quote = loanQuoteCalculator.getQuote(1000);

        verify(loanQuoteCalculator, times(1)).getApproximateAnnualInterestRate(anyInt(), any());

        assertEquals(expectedRate, quote.getRate().toString());
    }

    @Test
    void testRateRounding() throws InsufficientLendersException {
        testRateRounding("12.5", 12.50);
        testRateRounding("12.5", 12.51);
        testRateRounding("12.5", 12.52);
        testRateRounding("12.5", 12.53);
        testRateRounding("12.5", 12.54);
        testRateRounding("12.5", 12.549);
        testRateRounding("12.6", 12.55);
        testRateRounding("12.6", 12.56);
        testRateRounding("13.0", 13);
    }
}
