package it.loanquote.dtos;

public class PtmDTO {
	
	private double principal;
	private int term; 
	private double monthlyPayment;

	/**
	 * @return the principal
	 */
	public double getPrincipal() {
		return principal;
	}


	/**
	 * @param principal the principal to set
	 */
	public void setPrincipal(double principal) {
		this.principal = principal;
	}


	/**
	 * @return the term
	 */
	public int getTerm() {
		return term;
	}


	/**
	 * @param term the term to set
	 */
	public void setTerm(int term) {
		this.term = term;
	}


	/**
	 * @return the monthlyPayment
	 */
	public double getMonthlyPayment() {
		return monthlyPayment;
	}


	/**
	 * @param monthlyPayment the monthlyPayment to set
	 */
	public void setMonthlyPayment(double monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}
	

}
