////package it.loanquote.entities;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.JoinTable;
//import javax.persistence.ManyToMany;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//
///**
// * The Class AccessRequest.
// */
//@Entity
//@Table(name = "ACCESS_REQUESTS")
//public class AccessRequest extends AuditableDate implements Serializable {
//
//  /** The Constant serialVersionUID. */
//  private static final long serialVersionUID = 763212992927529694L;
//
////  /** The id. */
//  @Id
//  @Column(name = "ID")
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  private Long id;
//
//  /** The parent renewal request. */
//  @OneToOne
//  @JoinColumn(name = "RENEWAL_REQUEST_ID", referencedColumnName = "ID")
//  private AccessRequest parentRenewalRequest;
//
//  /** The child renewal request. */
//  @OneToOne(mappedBy = "parentRenewalRequest")
//  private AccessRequest childRenewalRequest;
//
//  /** The deleted. */
//  @Column(name = "DELETED")
//  private boolean deleted;
//
//  /** The created by apa. */
//  @Column(name = "CREATEDBY_APA")
//  private Boolean createdByApa;
//
//  /** The apa notes. */
//  @Column(name = "APA_NOTES")
//  private String apaNotes;
//
//  /** The alfresco folder id. */
//  @Column(name = "ALFRESCO_FOLDER_ID")
//  private String alfrescoFolderId;
//
//  /** The alfresco folder path. */
//  @Column(name = "ALFRESCO_FOLDER_PATH")
//  private String alfrescoFolderPath;
//
//  /** The apa document name. */
//  @Column(name = "APA_DOCUMENT_NAME")
//  private String apaDocumentName;
//
//  /** The alfresco apa document id. */
//  @Column(name = "ALFRESCO_APA_DOCUMENT_ID")
//  private String alfrescoApaDocumentId;
//
//  /** The submitted date. */
//  @Temporal(TemporalType.TIMESTAMP)
//  @Column(name = "SUBMITTED_DATE")
//  private Date submittedDate;
//
//  /** The user id. */
//  @Column(name = "USER_ID")
//  private String userId;
//
//  /** The renewal. */
//  @Column(name = "RENEWAL")
//  private boolean renewal;
//
//  /** The request date. */
//  @Temporal(TemporalType.TIMESTAMP)
//  @Column(name = "REQUEST_DATE")
//  private Date requestDate;
//
//  /** The protocol number. */
//  @Column(name = "PROTOCOL_NUMBER")
//  private String protocolNumber;
//
//  /** The docspa document id. */
//  @Column(name = "DOCSPA_DOCUMENT_ID")
//  private String docspaDocumentId;
//
//  /** The renewal notes. */
//  @Column(name = "RENEWAL_NOTES")
//  private String renewalNotes;
//
//  /** The status access request. */
//  // bi-directional many-to-one association to StatusAccessRequest
//  @ManyToOne(fetch = FetchType.EAGER)
//  @JoinColumn(name = "STATUS_ID", referencedColumnName = "ID")
//  private StatusAccessRequest statusAccessRequest;
//
//  /** The st acc applicant. */
//  @OneToOne(mappedBy = "accessRequest", cascade = CascadeType.ALL)
//  private StAccApplicant stAccApplicant;
//
//  /** The st acc therapeutic areas. */
//  @OneToOne(mappedBy = "accessRequest", cascade = CascadeType.ALL)
//  private StAccTherapeuticArea stAccTherapeuticAreas;
//
//  /** The st acc clinical data. */
//  @OneToOne(mappedBy = "accessRequest", cascade = CascadeType.ALL)
//  private StAccClinicalData stAccClinicalData;
//
//  /** The st acc drug. */
//  @OneToOne(mappedBy = "accessRequest", cascade = CascadeType.ALL)
//  private StAccDrug stAccDrug;
//
//  /** The st acc taking charge. */
//  @OneToOne(mappedBy = "accessRequest", cascade = CascadeType.ALL)
//  private StAccTakingCharge stAccTakingCharge;
//
//  /** The st acc patient. */
//  @OneToOne(mappedBy = "accessRequest", cascade = CascadeType.ALL)
//  private StAccPatient stAccPatient;
//
//  /** The st acc scientific document. */
//  @OneToOne(mappedBy = "accessRequest", cascade = CascadeType.ALL)
//  private StAccScientificDocument stAccScientificDocument;
//
//  /** The st acc submission request. */
//  @OneToOne(mappedBy = "accessRequest", cascade = CascadeType.ALL, orphanRemoval = true)
//  private StAccSubmissionRequest stAccSubmissionRequest;
//
//  /** The dossier access request. */
//  @ManyToOne
//  @JoinColumn(name = "DOSSIER_ID", referencedColumnName = "ID")
//  private DossierAccessRequest dossierAccessRequest;
//
//  /** The validation administratives. */
//  @OneToMany(mappedBy = "accessRequest")
//  private List<ValidationAdministrative> validationAdministratives;
//
//  /** The evaluation scientifics. */
//  @OneToMany(mappedBy = "accessRequest")
//  private List<EvaluationScientific> evaluationScientifics;
//
//  /** The secretariat sessions. */
//  @ManyToMany
//  @JoinTable(name = "R_SECRETARIAT_SESSION_ACCESS_REQUEST",
//      joinColumns = {@JoinColumn(name = "ACCESS_REQUEST_ID", referencedColumnName = "ID")},
//      inverseJoinColumns = {
//          @JoinColumn(name = "SECRETARIAT_SESSION_ID", referencedColumnName = "ID")})
//  private List<SecretariatSession> secretariatSessions;
//
//  /** The letter evaluation. */
//  @OneToOne(mappedBy = "accessRequest")
//  private LetterEvaluation letterEvaluation;
//
//  /** The secretariat opinions. */
//  @OneToMany(mappedBy = "accessRequest")
//  private List<SecretariatOpinion> secretariatOpinions;
//
//  /** The determinations. */
//  @OneToMany(mappedBy = "accessRequest")
//  private List<Determination> determinations;
//
//  /** The letter secretariat. */
//  @OneToOne(mappedBy = "accessRequest")
//  private LetterSecretariat letterSecretariat;
//
//  /** The rectifications. */
//  @OneToMany(mappedBy = "accessRequest")
//  private List<Rectification> rectifications;
//
//  /** The patient treatment. */
//  @OneToOne(mappedBy = "accessRequest", cascade = {CascadeType.PERSIST})
//  private PatientTreatment patientTreatment;
//
//  /** The patient transfers. */
//  @OneToOne(mappedBy = "accessRequest")
//  private PatientTransfer patientTransfers;
//
//  /**
//   * Instantiates a new access request.
//   */
//  public AccessRequest() {
//    super();
//  }
//
//  /**
//   * Gets the id.
//   *
//   * @return the id
//   */
//  public Long getId() {
//    return id;
//  }
//
//  /**
//   * Sets the id.
//   *
//   * @param id the new id
//   */
//  public void setId(Long id) {
//    this.id = id;
//  }
//
//  /**
//   * Gets the deleted.
//   *
//   * @return the deleted
//   */
//  public boolean getDeleted() {
//    return deleted;
//  }
//
//  /**
//   * Sets the deleted.
//   *
//   * @param deleted the new deleted
//   */
//  public void setDeleted(boolean deleted) {
//    this.deleted = deleted;
//  }
//
//  /**
//   * Gets the submitted date.
//   *
//   * @return the submitted date
//   */
//  public Date getSubmittedDate() {
//    return this.submittedDate;
//  }
//
//  /**
//   * Sets the submitted date.
//   *
//   * @param submittedDate the new submitted date
//   */
//  public void setSubmittedDate(Date submittedDate) {
//    this.submittedDate = submittedDate;
//  }
//
//  /**
//   * Gets the user id.
//   *
//   * @return the user id
//   */
//  public String getUserId() {
//    return this.userId;
//  }
//
//  /**
//   * Sets the user id.
//   *
//   * @param userId the new user id
//   */
//  public void setUserId(String userId) {
//    this.userId = userId;
//  }
//
//  /**
//   * Gets the renewal.
//   *
//   * @return the renewal
//   */
//  public boolean getRenewal() {
//    return renewal;
//  }
//
//  /**
//   * Sets the renewal.
//   *
//   * @param renewal the new renewal
//   */
//  public void setRenewal(boolean renewal) {
//    this.renewal = renewal;
//  }
//
//  /**
//   * Gets the request date.
//   *
//   * @return the request date
//   */
//  public Date getRequestDate() {
//    return requestDate;
//  }
//
//  /**
//   * Gets the parent renewal request.
//   *
//   * @return the parent renewal request
//   */
//  public AccessRequest getParentRenewalRequest() {
//    return parentRenewalRequest;
//  }
//
//  /**
//   * Sets the parent renewal request.
//   *
//   * @param parentRenewalRequest the new parent renewal request
//   */
//  public void setParentRenewalRequest(AccessRequest parentRenewalRequest) {
//    this.parentRenewalRequest = parentRenewalRequest;
//  }
//
//  /**
//   * Gets the child renewal request.
//   *
//   * @return the child renewal request
//   */
//  public AccessRequest getChildRenewalRequest() {
//    return childRenewalRequest;
//  }
//
//  /**
//   * Sets the child renewal request.
//   *
//   * @param childRenewalRequest the new child renewal request
//   */
//  public void setChildRenewalRequest(AccessRequest childRenewalRequest) {
//    this.childRenewalRequest = childRenewalRequest;
//  }
//
//  /**
//   * Sets the request date.
//   *
//   * @param requestDate the new request date
//   */
//  public void setRequestDate(Date requestDate) {
//    this.requestDate = requestDate;
//  }
//
//  /**
//   * Gets the protocol number.
//   *
//   * @return the protocol number
//   */
//  public String getProtocolNumber() {
//    return protocolNumber;
//  }
//
//  /**
//   * Sets the protocol number.
//   *
//   * @param protocolNumber the new protocol number
//   */
//  public void setProtocolNumber(String protocolNumber) {
//    this.protocolNumber = protocolNumber;
//  }
//
//  /**
//   * Gets the st acc clinical data.
//   *
//   * @return the st acc clinical data
//   */
//  public StAccClinicalData getStAccClinicalData() {
//    return this.stAccClinicalData;
//  }
//
//  /**
//   * Gets the alfresco folder id.
//   *
//   * @return the alfresco folder id
//   */
//  public String getAlfrescoFolderId() {
//    return alfrescoFolderId;
//  }
//
//  /**
//   * Sets the alfresco folder id.
//   *
//   * @param alfrescoFolderId the new alfresco folder id
//   */
//  public void setAlfrescoFolderId(String alfrescoFolderId) {
//    this.alfrescoFolderId = alfrescoFolderId;
//  }
//
//  /**
//   * Gets the alfresco folder path.
//   *
//   * @return the alfresco folder path
//   */
//  public String getAlfrescoFolderPath() {
//    return alfrescoFolderPath;
//  }
//
//  /**
//   * Sets the alfresco folder path.
//   *
//   * @param alfrescoFolderPath the new alfresco folder path
//   */
//  public void setAlfrescoFolderPath(String alfrescoFolderPath) {
//    this.alfrescoFolderPath = alfrescoFolderPath;
//  }
//
//  /**
//   * Gets the renewal notes.
//   *
//   * @return the renewal notes
//   */
//  public String getRenewalNotes() {
//    return renewalNotes;
//  }
//
//  /**
//   * Sets the renewal notes.
//   *
//   * @param renewalNotes the new renewal notes
//   */
//  public void setRenewalNotes(String renewalNotes) {
//    this.renewalNotes = renewalNotes;
//  }
//
//  /**
//   * Sets the st acc clinical data.
//   *
//   * @param stAccClinicalData the new st acc clinical data
//   */
//  public void setStAccClinicalData(StAccClinicalData stAccClinicalData) {
//    this.stAccClinicalData = stAccClinicalData;
//    stAccClinicalData.setAccessRequest(this);
//  }
//
//  /**
//   * Gets the status access request.
//   *
//   * @return the status access request
//   */
//  public StatusAccessRequest getStatusAccessRequest() {
//    return statusAccessRequest;
//  }
//
//  /**
//   * Sets the status access request.
//   *
//   * @param statusAccessRequest the new status access request
//   */
//  public void setStatusAccessRequest(StatusAccessRequest statusAccessRequest) {
//    this.statusAccessRequest = statusAccessRequest;
//  }
//
//  /**
//   * Gets the st acc therapeutic areas.
//   *
//   * @return the st acc therapeutic areas
//   */
//  public StAccTherapeuticArea getStAccTherapeuticAreas() {
//    return stAccTherapeuticAreas;
//  }
//
//  /**
//   * Sets the st acc therapeutic areas.
//   *
//   * @param stAccTherapeuticAreas the new st acc therapeutic areas
//   */
//  public void setStAccTherapeuticAreas(StAccTherapeuticArea stAccTherapeuticAreas) {
//    this.stAccTherapeuticAreas = stAccTherapeuticAreas;
//    stAccTherapeuticAreas.setAccessRequest(this);
//  }
//
//  /**
//   * Gets the st acc drug.
//   *
//   * @return the st acc drug
//   */
//  public StAccDrug getStAccDrug() {
//    return stAccDrug;
//  }
//
//  /**
//   * Sets the st acc drug.
//   *
//   * @param stAccDrug the new st acc drug
//   */
//  public void setStAccDrug(StAccDrug stAccDrug) {
//    this.stAccDrug = stAccDrug;
//    stAccDrug.setAccessRequest(this);
//  }
//
//  /**
//   * Gets the st acc patient.
//   *
//   * @return the stAccPatients
//   */
//  public StAccPatient getStAccPatient() {
//    return stAccPatient;
//  }
//
//  /**
//   * Sets the st acc patient.
//   *
//   * @param stAccPatient the new st acc patient
//   */
//  public void setStAccPatient(StAccPatient stAccPatient) {
//    this.stAccPatient = stAccPatient;
//    stAccPatient.setAccessRequest(this);
//  }
//
//  /**
//   * Gets the st acc applicant.
//   *
//   * @return the stAccApplicant
//   */
//  public StAccApplicant getStAccApplicant() {
//    return stAccApplicant;
//  }
//
//  /**
//   * Sets the st acc applicant.
//   *
//   * @param stAccApplicant the stAccApplicant to set
//   */
//  public void setStAccApplicant(StAccApplicant stAccApplicant) {
//    this.stAccApplicant = stAccApplicant;
//    stAccApplicant.setAccessRequest(this);
//  }
//
//  /**
//   * Gets the st acc submission request.
//   *
//   * @return the st acc submission request
//   */
//  public StAccSubmissionRequest getStAccSubmissionRequest() {
//    return stAccSubmissionRequest;
//  }
//
//  /**
//   * Sets the st acc submission request.
//   *
//   * @param stAccSubmissionRequest the new st acc submission request
//   */
//  public void setStAccSubmissionRequest(StAccSubmissionRequest stAccSubmissionRequest) {
//    this.stAccSubmissionRequest = stAccSubmissionRequest;
//  }
//
//  /**
//   * Gets the st acc taking charge.
//   *
//   * @return the st acc taking charge
//   */
//  public StAccTakingCharge getStAccTakingCharge() {
//    return stAccTakingCharge;
//  }
//
//  /**
//   * Sets the st acc taking charge.
//   *
//   * @param stAccTakingCharge the new st acc taking charge
//   */
//  public void setStAccTakingCharge(StAccTakingCharge stAccTakingCharge) {
//    this.stAccTakingCharge = stAccTakingCharge;
//    stAccTakingCharge.setAccessRequest(this);
//  }
//
//  /**
//   * To string.
//   *
//   * @return the string
//   */
//  @Override
//  public String toString() {
//    return "AccessRequest [id=" + id + ", userId=" + userId + ", renewal=" + renewal
//        + ", stAccApplicant=" + stAccApplicant + "]";
//  }
//
//  /**
//   * Gets the st acc scientific document.
//   *
//   * @return the st acc scientific document
//   */
//  public StAccScientificDocument getStAccScientificDocument() {
//    return stAccScientificDocument;
//  }
//
//  /**
//   * Sets the st acc scientific document.
//   *
//   * @param stAccScientificDocument the new st acc scientific document
//   */
//  public void setStAccScientificDocument(StAccScientificDocument stAccScientificDocument) {
//    this.stAccScientificDocument = stAccScientificDocument;
//    stAccScientificDocument.setAccessRequest(this);
//
//  }
//
//  /**
//   * Gets the dossier access request.
//   *
//   * @return the dossier access request
//   */
//  public DossierAccessRequest getDossierAccessRequest() {
//    return this.dossierAccessRequest;
//  }
//
//  /**
//   * Sets the dossier access request.
//   *
//   * @param dossierAccessRequest the new dossier access request
//   */
//  public void setDossierAccessRequest(DossierAccessRequest dossierAccessRequest) {
//    this.dossierAccessRequest = dossierAccessRequest;
//  }
//
//  /**
//   * Gets the apa notes.
//   *
//   * @return the apa notes
//   */
//  public String getApaNotes() {
//    return apaNotes;
//  }
//
//  /**
//   * Sets the apa notes.
//   *
//   * @param apaNotes the new apa notes
//   */
//  public void setApaNotes(String apaNotes) {
//    this.apaNotes = apaNotes;
//  }
//
//  /**
//   * Gets the apa document name.
//   *
//   * @return the apa document name
//   */
//  public String getApaDocumentName() {
//    return apaDocumentName;
//  }
//
//  /**
//   * Sets the apa document name.
//   *
//   * @param apaDocumentName the new apa document name
//   */
//  public void setApaDocumentName(String apaDocumentName) {
//    this.apaDocumentName = apaDocumentName;
//  }
//
//  /**
//   * Gets the alfresco apa document id.
//   *
//   * @return the alfresco apa document id
//   */
//  public String getAlfrescoApaDocumentId() {
//    return alfrescoApaDocumentId;
//  }
//
//  /**
//   * Sets the alfresco apa document id.
//   *
//   * @param alfrescoApaDocumentId the new alfresco apa document id
//   */
//  public void setAlfrescoApaDocumentId(String alfrescoApaDocumentId) {
//    this.alfrescoApaDocumentId = alfrescoApaDocumentId;
//  }
//
//  /**
//   * Gets the created by apa.
//   *
//   * @return the created by apa
//   */
//  public Boolean getCreatedByApa() {
//    return createdByApa;
//  }
//
//  /**
//   * Sets the created by apa.
//   *
//   * @param createdByApa the new created by apa
//   */
//  public void setCreatedByApa(Boolean createdByApa) {
//    this.createdByApa = createdByApa;
//  }
//
//  /**
//   * Adds the administrative validation.
//   *
//   * @param validationAdministrative the validation administrative
//   * @return the validation administrative
//   */
//  public ValidationAdministrative addAdministrativeValidation(
//      ValidationAdministrative validationAdministrative) {
//    if (getValidationAdministratives() == null) {
//      this.validationAdministratives = new ArrayList<>();
//    }
//    getValidationAdministratives().add(validationAdministrative);
//    validationAdministrative.setAccessRequest(this);
//
//    return validationAdministrative;
//  }
//
//  /**
//   * Removes the administrative validation.
//   *
//   * @param validationAdministrative the validation administrative
//   * @return the validation administrative
//   */
//  public ValidationAdministrative removeAdministrativeValidation(
//      ValidationAdministrative validationAdministrative) {
//    getValidationAdministratives().remove(validationAdministrative);
//    validationAdministrative.setAccessRequest(null);
//
//    return validationAdministrative;
//  }
//
//  /**
//   * Gets the evaluation scientifics.
//   *
//   * @return the evaluation scientifics
//   */
//  public List<EvaluationScientific> getEvaluationScientifics() {
//    return this.evaluationScientifics;
//  }
//
//  /**
//   * Sets the evaluation scientifics.
//   *
//   * @param evaluationScientifics the new evaluation scientifics
//   */
//  public void setEvaluationScientifics(List<EvaluationScientific> evaluationScientifics) {
//    this.evaluationScientifics = evaluationScientifics;
//  }
//
//  /**
//   * Adds the evaluation scientific.
//   *
//   * @param evaluationScientifics the evaluation scientifics
//   * @return the evaluation scientific
//   */
//  public EvaluationScientific addEvaluationScientific(EvaluationScientific evaluationScientifics) {
//    if (getEvaluationScientifics() == null) {
//      this.evaluationScientifics = new ArrayList<>();
//    }
//    getEvaluationScientifics().add(evaluationScientifics);
//    evaluationScientifics.setAccessRequest(this);
//
//    return evaluationScientifics;
//  }
//
//  /**
//   * Removes the evaluation scientific.
//   *
//   * @param evaluationScientifics the evaluation scientifics
//   * @return the evaluation scientific
//   */
//  public EvaluationScientific removeEvaluationScientific(
//      EvaluationScientific evaluationScientifics) {
//    getEvaluationScientifics().remove(evaluationScientifics);
//    evaluationScientifics.setAccessRequest(null);
//
//    return evaluationScientifics;
//  }
//
//  /**
//   * Gets the secretariat sessions.
//   *
//   * @return the secretariat sessions
//   */
//  public List<SecretariatSession> getSecretariatSessions() {
//    return secretariatSessions;
//  }
//
//  /**
//   * Sets the secretariat sessions.
//   *
//   * @param secretariatSessions the new secretariat sessions
//   */
//  public void setSecretariatSessions(List<SecretariatSession> secretariatSessions) {
//    this.secretariatSessions = secretariatSessions;
//  }
//
//  /**
//   * Gets the letter evaluation.
//   *
//   * @return the letter evaluation
//   */
//  public LetterEvaluation getLetterEvaluation() {
//    return letterEvaluation;
//  }
//
//  /**
//   * Sets the letter evaluation.
//   *
//   * @param letterEvaluation the new letter evaluation
//   */
//  public void setLetterEvaluation(LetterEvaluation letterEvaluation) {
//    this.letterEvaluation = letterEvaluation;
//  }
//
//  /**
//   * Gets the secretariat opinions.
//   *
//   * @return the secretariat opinions
//   */
//  public List<SecretariatOpinion> getSecretariatOpinions() {
//    return this.secretariatOpinions;
//  }
//
//  /**
//   * Sets the secretariat opinions.
//   *
//   * @param secretariatOpinions the new secretariat opinions
//   */
//  public void setSecretariatOpinions(List<SecretariatOpinion> secretariatOpinions) {
//    this.secretariatOpinions = secretariatOpinions;
//  }
//
//  /**
//   * Gets the validation administratives.
//   *
//   * @return the validation administratives
//   */
//  public List<ValidationAdministrative> getValidationAdministratives() {
//    return validationAdministratives;
//  }
//
//  /**
//   * Sets the validation administratives.
//   *
//   * @param validationAdministratives the new validation administratives
//   */
//  public void setValidationAdministratives(
//      List<ValidationAdministrative> validationAdministratives) {
//    this.validationAdministratives = validationAdministratives;
//  }
//
//  /**
//   * Gets the determinations.
//   *
//   * @return the determinations
//   */
//  public List<Determination> getDeterminations() {
//    return determinations;
//  }
//
//  /**
//   * Sets the determinations.
//   *
//   * @param determinations the new determinations
//   */
//  public void setDeterminations(List<Determination> determinations) {
//    this.determinations = determinations;
//  }
//
//  /**
//   * Adds the secretariat opinion.
//   *
//   * @param secretariatOpinion the secretariat opinion
//   * @return the secretariat opinion
//   */
//  public SecretariatOpinion addSecretariatOpinion(SecretariatOpinion secretariatOpinion) {
//    getSecretariatOpinions().add(secretariatOpinion);
//    secretariatOpinion.setAccessRequest(this);
//
//    return secretariatOpinion;
//  }
//
//  /**
//   * Removes the secretariat opinion.
//   *
//   * @param secretariatOpinion the secretariat opinion
//   * @return the secretariat opinion
//   */
//  public SecretariatOpinion removeSecretariatOpinion(SecretariatOpinion secretariatOpinion) {
//    getSecretariatOpinions().remove(secretariatOpinion);
//    secretariatOpinion.setAccessRequest(null);
//
//    return secretariatOpinion;
//  }
//
//  /**
//   * Gets the letter secretariat.
//   *
//   * @return the letter secretariat
//   */
//  public LetterSecretariat getLetterSecretariat() {
//    return letterSecretariat;
//  }
//
//  /**
//   * Sets the letter secretariat.
//   *
//   * @param letterSecretariat the new letter secretariat
//   */
//  public void setLetterSecretariat(LetterSecretariat letterSecretariat) {
//    this.letterSecretariat = letterSecretariat;
//  }
//
//  /**
//   * Gets the rectifications.
//   *
//   * @return the rectifications
//   */
//  public List<Rectification> getRectifications() {
//    return rectifications;
//  }
//
//  /**
//   * Sets the rectifications.
//   *
//   * @param rectifications the new rectifications
//   */
//  public void setRectifications(List<Rectification> rectifications) {
//    this.rectifications = rectifications;
//  }
//
//  /**
//   * Adds the rectification.
//   *
//   * @param rectification the rectification
//   * @return the rectification
//   */
//  public Rectification addRectification(Rectification rectification) {
//    if (getRectifications() == null) {
//      this.rectifications = new ArrayList<>();
//    }
//    getRectifications().add(rectification);
//    rectification.setAccessRequest(this);
//
//    return rectification;
//  }
//
//  /**
//   * Removes the rectification.
//   *
//   * @param rectification the rectification
//   * @return the rectification
//   */
//  public Rectification removeRectification(Rectification rectification) {
//    getRectifications().remove(rectification);
//    rectification.setAccessRequest(null);
//
//    return rectification;
//  }
//
//  /**
//   * Gets the patient transfers.
//   *
//   * @return the patientTransfers
//   */
//  public PatientTransfer getPatientTransfers() {
//    return patientTransfers;
//  }
//
//  /**
//   * Sets the patient transfers.
//   *
//   * @param patientTransfers the patientTransfers to set
//   */
//  public void setPatientTransfers(PatientTransfer patientTransfers) {
//    this.patientTransfers = patientTransfers;
//  }
//
//  /**
//   * Gets the patient treatment.
//   *
//   * @return the patientTreatment
//   */
//  public PatientTreatment getPatientTreatment() {
//    return patientTreatment;
//  }
//
//  /**
//   * Sets the patient treatment.
//   *
//   * @param patientTreatment the patientTreatment to set
//   */
//  public void setPatientTreatment(PatientTreatment patientTreatment) {
//    this.patientTreatment = patientTreatment;
//  }
//
//  /**
//   * Gets the docspa document id.
//   *
//   * @return the docspa document id
//   */
//  public String getDocspaDocumentId() {
//    return docspaDocumentId;
//  }
//
//  /**
//   * Sets the docspa document id.
//   *
//   * @param docspaDocumentId the new docspa document id
//   */
//  public void setDocspaDocumentId(String docspaDocumentId) {
//    this.docspaDocumentId = docspaDocumentId;
//  }
//
//}
