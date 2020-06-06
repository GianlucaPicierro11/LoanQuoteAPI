package it.loanquote.dtos;

import java.math.BigDecimal;

public class PanDTO {
	
	private BigDecimal principal;
	private  BigDecimal annualInterestRate; 
	private  int numberOfPaymentPeriods;
	/**
	 * @return the principal
	 */
	public BigDecimal getPrincipal() {
		return principal;
	}
	/**
	 * @param principal the principal to set
	 */
	public void setPrincipal(BigDecimal principal) {
		this.principal = principal;
	}
	/**
	 * @return the annualInterestRate
	 */
	public BigDecimal getAnnualInterestRate() {
		return annualInterestRate;
	}
	/**
	 * @param annualInterestRate the annualInterestRate to set
	 */
	public void setAnnualInterestRate(BigDecimal annualInterestRate) {
		this.annualInterestRate = annualInterestRate;
	}
	/**
	 * @return the numberOfPaymentPeriods
	 */
	public int getNumberOfPaymentPeriods() {
		return numberOfPaymentPeriods;
	}
	/**
	 * @param numberOfPaymentPeriods the numberOfPaymentPeriods to set
	 */
	public void setNumberOfPaymentPeriods(int numberOfPaymentPeriods) {
		this.numberOfPaymentPeriods = numberOfPaymentPeriods;
	}


}
