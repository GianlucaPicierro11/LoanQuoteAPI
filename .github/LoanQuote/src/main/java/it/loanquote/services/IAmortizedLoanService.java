package it.loanquote.services;

import java.math.BigDecimal;

public interface IAmortizedLoanService {
	
	/**
     * Calculates an approximate annual interest rate using only the principal, term and monthly repayment
     * @param principal the initial loan amount
     * @param term number of repayment terms
     * @param monthlyPayment amount of repayment per term
     * @return an approximation of the annual interest rate in decimal format (i.e. 0.1 = 10%)
     */
	public double getApproximateAnnualInterestRate(double principal, int term, double monthlyPayment);

    /**
     * Calculates the monthly repayment required using amortized interest
     * @param principal the initial loan amount
     * @param annualInterestRate the annual interest rate in decimal form (i.e. 0.1 = 10%)
     * @param numberOfPaymentPeriods number of repayment periods
     * @return the repayment required to repay capital and interest every month
     */
	public BigDecimal getMonthlyRepayment(BigDecimal principal, BigDecimal annualInterestRate, int numberOfPaymentPeriods);

}