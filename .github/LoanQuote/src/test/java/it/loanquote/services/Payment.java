package it.loanquote.services;

import com.opencsv.bean.CsvBindByName;

import java.math.BigDecimal;

public class Payment {
    @CsvBindByName(column = "Rate", required = true)
    private BigDecimal rate;

    @CsvBindByName(column = "Principal", required = true)
    private int principal;

    @CsvBindByName(column = "Payment", required = true)
    private BigDecimal payment;

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(final BigDecimal rate) {
        this.rate = rate;
    }

    public int getPrincipal() {
        return principal;
    }

    public void setPrincipal(final int principal) {
        this.principal = principal;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(final BigDecimal payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "rate=" + rate +
                ", principal=" + principal +
                ", payment=" + payment +
                '}';
    }
}
