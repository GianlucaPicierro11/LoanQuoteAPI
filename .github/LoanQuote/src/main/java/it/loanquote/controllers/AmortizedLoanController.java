package it.loanquote.controllers;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.loanquote.dtos.PanDTO;
import it.loanquote.dtos.PtmDTO;
import it.loanquote.services.AmortizedLoan;
import it.loanquote.services.IAmortizedLoanService;

@RestController
@RequestMapping("/amortized-loan")
@Validated
public class AmortizedLoanController {

  private static final Logger log = LoggerFactory.getLogger(AmortizedLoanController.class);

  @Autowired
  private IAmortizedLoanService amortizedLoanService;

  @PostMapping("/approximate-annual-interest-rate")
  public double getApproximateAnnualInterestRate(@RequestBody PtmDTO request) {
    log.info("It has been required to get Approximate Annual Interest Rate");
    return amortizedLoanService.getApproximateAnnualInterestRate(request.getPrincipal(), request.getTerm(), request.getMonthlyPayment());
  }
  
  @PostMapping("/monthly-repayment")
  public BigDecimal getMonthlyRepayment(@RequestBody PanDTO request) {
    log.info("It has been required to get Approximate Annual Interest Rate");
    return amortizedLoanService.getMonthlyRepayment(request.getPrincipal(),request.getAnnualInterestRate(),request.getNumberOfPaymentPeriods());
  }
  
}
