package it.loanquote.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.loanquote.services.AmortizedLoan;
import it.loanquote.services.IAmortizedLoanService;

@RestController
@RequestMapping("/amortized-loan")
@Validated
public class AmortizedLoanController {
//
  private static final Logger log = LoggerFactory.getLogger(AmortizedLoanController.class);

  @Autowired
  private IAmortizedLoanService amortizedLoanService;

  @GetMapping
  public double getApproximateAnnualInterestRate(double principal, int term, double monthlyPayment
     ) {
    log.info("It has been required to get Approximate Annual Interest Rate");
    return amortizedLoanService.getApproximateAnnualInterestRate(principal, term, monthlyPayment);
  }

//  @AllUserRoles
//  @GetMapping(value = "/{requestId}")
//  public AccessRequestDTO getAccessRequestById(@PathVariable(required = true) Long requestId) {
//    log.info("It has been required to retrive access request");
//    return accessRequestService.getAccessRequestById(requestId);
//  }
//
//  @AllUserRoles
//  @GetMapping(value = "/{requestId}/status")
//  public StatusDTO getAccessRequestStatus(@PathVariable(required = true) Long requestId) {
//    log.info("It has been required to retrive status for access request with id {}", requestId);
//    return accessRequestService.getAccessRequestStatusById(requestId);
//  }
//
//  @AllUserRoles
//  @GetMapping(value = "/{accessRequestId}/revising")
//  public boolean isAccessRequestInRevising(@PathVariable(required = true) Long accessRequestId) {
//    log.info("It has been required to check if access request with id {} request is in revising",
//        accessRequestId);
//    return accessRequestService.isAccessRequestInRevising(accessRequestId);
//  }
//
}
