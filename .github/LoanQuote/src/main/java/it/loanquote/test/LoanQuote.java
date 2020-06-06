package it.loanquote.test;

import java.math.BigDecimal;

/**
 * Bean to represent a single loan quote containing repayment information
 */
public final class LoanQuote {
    /**
     * the initial amount of loan in pounds sterling
     */
    private final int loanAmount;

    /**
     * the annual interest rate for the loan as a percentage
     */
    private final BigDecimal rate;

    /**
     * the amount to repay (capital and interest) each month in pounds sterling
     */
    private final BigDecimal monthlyRepayment;

    /**
     * the total amount to repay, note that this might not be exactly a multiple of <code>monthlyRepayment</code>
     * as there needs to be a process in the last month to reconcile fractional pennies that might have been
     * overpaid or underpaid through monthly repayments
     */
    private final BigDecimal totalRepayment;

    /**
     * Constructs a <code>LoanQuote</code> containing the loan quote information specified
     * @param loanAmount       the initial amount of loan in pounds sterling
     * @param rate             the annual interest rate for the loan as a percentage
     * @param monthlyRepayment the amount to repay (capital and interest) each month in pounds sterling
     * @param totalRepayment   the total amount to repay, note that this might not be exactly a multiple of <code>monthlyRepayment</code>
     *                         as there needs to be a process in the last month to reconcile fractional pennies that might have been
     *                         overpaid or underpaid through monthly repayments
     */
    public LoanQuote(final int loanAmount, final BigDecimal rate, final BigDecimal monthlyRepayment, final BigDecimal totalRepayment) {
        this.loanAmount = loanAmount;
        this.rate = rate;
        this.monthlyRepayment = monthlyRepayment;
        this.totalRepayment = totalRepayment;
    }

    /**
     * Gets the initial amount of loan in pounds sterling
     *
     * @return the initial amount of loan in pounds sterling
     */
    public int getLoanAmount() {
        return loanAmount;
    }

    /**
     * Gets the annual interest rate for the loan as a percentage
     *
     * @return the annual interest rate for the loan as a percentage
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * Gets the amount to repay (capital and interest) each month in pounds sterling
     *
     * @return the amount to repay (capital and interest) each month in pounds sterling
     */
    public BigDecimal getMonthlyRepayment() {
        return monthlyRepayment;
    }

    /**
     * Gets the amount to repay (capital and interest) each month in pounds sterling
     *
     * @return the amount to repay (capital and interest) each month in pounds sterling
     */
    public BigDecimal getTotalRepayment() {
        return totalRepayment;
    }
}
