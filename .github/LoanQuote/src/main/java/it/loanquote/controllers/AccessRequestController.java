package it.aifa.cinquepercento.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import it.aifa.cinquepercento.annotation.role.AllUserRoles;
import it.aifa.cinquepercento.annotation.role.IsDoctorOrApaUser;
import it.aifa.cinquepercento.dto.AccessRequestDTO;
import it.aifa.cinquepercento.dto.StatusDTO;
import it.aifa.cinquepercento.service.IAccessRequestService;

@RestController
@RequestMapping("/access-requests")
@Validated
public class AccessRequestController {

  private static final Logger log = LoggerFactory.getLogger(AccessRequestController.class);

  @Autowired
  private IAccessRequestService accessRequestService;

  @IsDoctorOrApaUser
  @PostMapping
  public AccessRequestDTO saveAccessRequest(@Validated AccessRequestDTO request,
      BindingResult bindingResult) {
    log.info("It has been required to save new access request");
    return accessRequestService.saveAccessRequest(request, bindingResult);
  }

  @AllUserRoles
  @GetMapping(value = "/{requestId}")
  public AccessRequestDTO getAccessRequestById(@PathVariable(required = true) Long requestId) {
    log.info("It has been required to retrive access request");
    return accessRequestService.getAccessRequestById(requestId);
  }

  @AllUserRoles
  @GetMapping(value = "/{requestId}/status")
  public StatusDTO getAccessRequestStatus(@PathVariable(required = true) Long requestId) {
    log.info("It has been required to retrive status for access request with id {}", requestId);
    return accessRequestService.getAccessRequestStatusById(requestId);
  }

  @AllUserRoles
  @GetMapping(value = "/{accessRequestId}/revising")
  public boolean isAccessRequestInRevising(@PathVariable(required = true) Long accessRequestId) {
    log.info("It has been required to check if access request with id {} request is in revising",
        accessRequestId);
    return accessRequestService.isAccessRequestInRevising(accessRequestId);
  }

}
