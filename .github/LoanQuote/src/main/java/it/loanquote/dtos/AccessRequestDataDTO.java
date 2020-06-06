//package it.loanquote.dtos;
//
//import java.util.Date;
//import javax.validation.constraints.NotNull;
//import com.fasterxml.jackson.annotation.JsonFormat;
//
//public class AccessRequestDataDTO {
//
//  private Long id;
//
//  private Long dossierId;
//
//  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
//  private Date firstSubmissionDate;
//
//  private String protocolNumber;
//
//  private StatusDTO statusAccessRequest;
//
//  private AccessRequestDTO parentRenewalRequest;
//
//  private AccessRequestDTO childRenewalRequest;
//
//  @NotNull
//  private FileDTO fileAccessRequestSigned;
//
//  /* user id of whom created the access request */
//  private String userId;
//
//  private String applicantUid;
//
//  private ClinicalCenterDataDTO applicantClinicalCenter;
//
//  private ClinicalCenterDataDTO takingChargeClinicalCenter;
//
//  private String takingChargeUid;
//
//  /* Check if validation is present */
//  private Boolean isValidationPresent;
//
//  /* Check if evaluation is present */
//  private Boolean isEvaluationPresent;
//
//  /* Check if evaluation letter is present */
//  private Boolean isPresentLetterEvaluation = Boolean.FALSE;
//
//  /* Check if secretariat letter is present */
//  private Boolean isPresentLetterSecretariat = Boolean.FALSE;
//
//  /* Check if start treatment is present */
//  private Boolean isTreatmentPresent;
//
//  /* check if the last quote has a rectification in "draft" */
//  private Boolean isQuoteInRectification;
//
//  /* check if this access request has a rectification in status draft */
//  private Boolean isPresentSomeDraftRectification;
//
//  /*
//   * check if this access request has a rectification in status draft (for allowed user) or in
//   * status submited
//   */
//  private Boolean isRectificationPresent;
//
//  /*
//   * check if this request is a renewal and is status is DRAFT or PRESUBMIT
//   * 
//   */
//  private Boolean isRequestRenewalInDraft;
//
//  private Boolean existsDetermination;
//
//  /**
//   * @return the id
//   */
//  public Long getId() {
//    return id;
//  }
//
//  /**
//   * @param id the id to set
//   */
//  public void setId(Long id) {
//    this.id = id;
//  }
//
//  /**
//   * @return the statusAccessRequest
//   */
//  public StatusDTO getStatusAccessRequest() {
//    return statusAccessRequest;
//  }
//
//  /**
//   * @param statusAccessRequest the statusAccessRequest to set
//   */
//  public void setStatusAccessRequest(StatusDTO statusAccessRequest) {
//    this.statusAccessRequest = statusAccessRequest;
//  }
//
//  /**
//   * @return the userId
//   */
//  public String getUserId() {
//    return userId;
//  }
//
//  /**
//   * @param userId the userId to set
//   */
//  public void setUserId(String userId) {
//    this.userId = userId;
//  }
//
//  public Date getFirstSubmissionDate() {
//    return firstSubmissionDate;
//  }
//
//  public void setFirstSubmissionDate(Date firstSubmissionDate) {
//    this.firstSubmissionDate = firstSubmissionDate;
//  }
//
//  public String getProtocolNumber() {
//    return protocolNumber;
//  }
//
//  public void setProtocolNumber(String protocolNumber) {
//    this.protocolNumber = protocolNumber;
//  }
//
//  public FileDTO getFileAccessRequestSigned() {
//    return fileAccessRequestSigned;
//  }
//
//  public void setFileAccessRequestSigned(FileDTO fileAccessRequestSigned) {
//    this.fileAccessRequestSigned = fileAccessRequestSigned;
//  }
//
//
//  public Long getDossierId() {
//    return dossierId;
//  }
//
//  public void setDossierId(Long dossierId) {
//    this.dossierId = dossierId;
//  }
//
//  public String getApplicantUid() {
//    return applicantUid;
//  }
//
//  public void setApplicantUid(String applicantUid) {
//    this.applicantUid = applicantUid;
//  }
//
//  public String getTakingChargeUid() {
//    return takingChargeUid;
//  }
//
//  public void setTakingChargeUid(String takingChargeUid) {
//    this.takingChargeUid = takingChargeUid;
//  }
//
//  public Boolean getIsValidationPresent() {
//    return isValidationPresent;
//  }
//
//  public void setIsValidationPresent(Boolean isValidationPresent) {
//    this.isValidationPresent = isValidationPresent;
//  }
//
//  public Boolean getIsEvaluationPresent() {
//    return isEvaluationPresent;
//  }
//
//  public void setIsEvaluationPresent(Boolean isEvaluationPresent) {
//    this.isEvaluationPresent = isEvaluationPresent;
//  }
//
//  public Boolean getIsPresentLetterEvaluation() {
//    return isPresentLetterEvaluation;
//  }
//
//  public void setIsPresentLetterEvaluation(Boolean isPresentLetterEvaluation) {
//    this.isPresentLetterEvaluation = isPresentLetterEvaluation;
//  }
//
//  public Boolean getIsPresentLetterSecretariat() {
//    return isPresentLetterSecretariat;
//  }
//
//  public void setIsPresentLetterSecretariat(Boolean isPresentLetterSecretariat) {
//    this.isPresentLetterSecretariat = isPresentLetterSecretariat;
//  }
//
//  public AccessRequestDTO getChildRenewalRequest() {
//    return childRenewalRequest;
//  }
//
//  public void setChildRenewalRequest(AccessRequestDTO childRenewalRequest) {
//    this.childRenewalRequest = childRenewalRequest;
//  }
//
//  public AccessRequestDTO getParentRenewalRequest() {
//    return parentRenewalRequest;
//  }
//
//  public void setParentRenewalRequest(AccessRequestDTO parentRenewalRequest) {
//    this.parentRenewalRequest = parentRenewalRequest;
//  }
//
//  public ClinicalCenterDataDTO getApplicantClinicalCenter() {
//    return applicantClinicalCenter;
//  }
//
//  public void setApplicantClinicalCenter(ClinicalCenterDataDTO applicantClinicalCenter) {
//    this.applicantClinicalCenter = applicantClinicalCenter;
//  }
//
//  public ClinicalCenterDataDTO getTakingChargeClinicalCenter() {
//    return takingChargeClinicalCenter;
//  }
//
//  public void setTakingChargeClinicalCenter(ClinicalCenterDataDTO takingChargeClinicalCenter) {
//    this.takingChargeClinicalCenter = takingChargeClinicalCenter;
//  }
//
//  /**
//   * @return the isTreatmentPresent
//   */
//  public Boolean getIsTreatmentPresent() {
//    return isTreatmentPresent;
//  }
//
//  /**
//   * @param isTreatmentPresent the isTreatmentPresent to set
//   */
//  public void setIsTreatmentPresent(Boolean isTreatmentPresent) {
//    this.isTreatmentPresent = isTreatmentPresent;
//  }
//
//  /**
//   * @return the isQuoteInRectification
//   */
//  public Boolean getIsQuoteInRectification() {
//    return isQuoteInRectification;
//  }
//
//  /**
//   * @param isQuoteInRectification the isQuoteInRectification to set
//   */
//  public void setIsQuoteInRectification(Boolean isQuoteInRectification) {
//    this.isQuoteInRectification = isQuoteInRectification;
//  }
//
//  /**
//   * @return the isPresentSomeDraftRectification
//   */
//  public Boolean getIsPresentSomeDraftRectification() {
//    return isPresentSomeDraftRectification;
//  }
//
//  /**
//   * @param isPresentSomeDraftRectification the isPresentSomeDraftRectification to set
//   */
//  public void setIsPresentSomeDraftRectification(Boolean isPresentSomeDraftRectification) {
//    this.isPresentSomeDraftRectification = isPresentSomeDraftRectification;
//  }
//
//  /**
//   * @return the isRectificationPresent
//   */
//  public Boolean getIsRectificationPresent() {
//    return isRectificationPresent;
//  }
//
//  /**
//   * @param isRectificationPresent the isRectificationPresent to set
//   */
//  public void setIsRectificationPresent(Boolean isRectificationPresent) {
//    this.isRectificationPresent = isRectificationPresent;
//  }
//
//  /**
//   * @return the isRequestRenewalInDraft
//   */
//  public Boolean getIsRequestRenewalInDraft() {
//    return isRequestRenewalInDraft;
//  }
//
//  /**
//   * @param isRequestRenewalInDraft the isRequestRenewalInDraft to set
//   */
//  public void setIsRequestRenewalInDraft(Boolean isRequestRenewalInDraft) {
//    this.isRequestRenewalInDraft = isRequestRenewalInDraft;
//  }
//
//  /**
//   * @return the existsDetermination
//   */
//  public Boolean getExistsDetermination() {
//    return existsDetermination;
//  }
//
//  /**
//   * @param existsDetermination the existsDetermination to set
//   */
//  public void setExistsDetermination(Boolean existsDetermination) {
//    this.existsDetermination = existsDetermination;
//  }
//
//}
