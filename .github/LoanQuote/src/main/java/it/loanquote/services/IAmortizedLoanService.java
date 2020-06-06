package it.loanquote.services;

import java.math.BigDecimal;

public interface IAmortizedLoanService {

	double getApproximateAnnualInterestRate(double principal, int term, double monthlyPayment);

	BigDecimal getMonthlyRepayment(BigDecimal principal, BigDecimal annualInterestRate, int numberOfPaymentPeriods);

}