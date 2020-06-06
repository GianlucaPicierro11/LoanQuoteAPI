package it.aifa.cinquepercento.service;

import static it.aifa.cinquepercento.entity.QAccessRequest.accessRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import javax.transaction.Transactional;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.commons.collections4.IterableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.BooleanExpression;
import it.aifa.alfresco.client.dto.AlfrescoFolderDTO;
import it.aifa.alfresco.client.exception.AlfrescoClientException;
import it.aifa.alfresco.client.services.IAlfrescoCMISBindingUrlClient;
import it.aifa.alfresco.client.services.IAlfrescoService;
import it.aifa.authentication.filter.IAuthenticationFacade;
import it.aifa.cinquepercento.constant.MessageConstant;
import it.aifa.cinquepercento.converter.AccessRequestDataConverter;
import it.aifa.cinquepercento.dto.AccessRequestDataDTO;
import it.aifa.cinquepercento.dto.ClinicalCenterDataDTO;
import it.aifa.cinquepercento.entity.AccessRequest;
import it.aifa.cinquepercento.entity.DossierAccessRequest;
import it.aifa.cinquepercento.entity.Quote;
import it.aifa.cinquepercento.entity.RequestDrug;
import it.aifa.cinquepercento.entity.ScientificDocument;
import it.aifa.cinquepercento.entity.StAccApplicant;
import it.aifa.cinquepercento.entity.StAccClinicalData;
import it.aifa.cinquepercento.entity.StAccDrug;
import it.aifa.cinquepercento.entity.StAccPatient;
import it.aifa.cinquepercento.entity.StAccScientificDocument;
import it.aifa.cinquepercento.entity.StAccTherapeuticArea;
import it.aifa.cinquepercento.entity.StatusAccessRequest;
import it.aifa.cinquepercento.enumeration.Role;
import it.aifa.cinquepercento.enumeration.StatusAccessRequestEnum;
import it.aifa.cinquepercento.enumeration.StatusQuoteEnum;
import it.aifa.cinquepercento.repository.AccessRequestRepository;
import it.aifa.cinquepercento.repository.DossierAccessRequestRepository;
import it.aifa.cinquepercento.repository.QuoteRepository;
import it.aifa.cinquepercento.repository.StatusAccessRequestRepository;
import it.aifa.common.exception.APIException;
import it.aifa.common.exception.InternalErrorException;
import it.aifa.common.exception.NotFoundException;
import it.aifa.gestioneprofili.client.dto.UserRights;
import it.aifa.gestioneprofili.client.services.IGestioneProfiliService;
import it.aifa.jms.dto.EmailTemplateEnum;
import it.aifa.jms.dto.NotificationMessageDTO;
import it.aifa.jms.dto.NotificationTemplateEnum;

@Service
@Transactional
public class AccessRequestDataService extends CommonSendEmailService
    implements IAccessRequestDataService {

  private static final Logger log = LoggerFactory.getLogger(AccessRequestDataService.class);

  @Autowired
  private AccessRequestRepository accessRequestRepository;

  @Autowired
  private AccessRequestService accessRequestService;

  @Autowired
  private AccessRequestDataConverter accessRequestDataConverter;

  @Autowired
  private DossierAccessRequestRepository dossierAccessRequestRepository;

  @Autowired
  private StatusAccessRequestRepository statusAccessRequestRepository;

  @Autowired
  private IGestioneProfiliService gestioneProfiliService;

  @Autowired
  private IAuthenticationFacade authenticationFacade;

  @Autowired
  private IAlfrescoService alfrescoService;

  @Autowired
  private QuoteRepository quoteRepository;

  @Autowired
  private IQuoteService quoteService;

  @Autowired
  private IAlfrescoCMISBindingUrlClient alfrescoClient;

  @Override
  public ClinicalCenterDataDTO getClincalCenterForLastDossierAccessRequest(Long dossierId) {
    try {
      AccessRequestDataDTO lastDossierRequest =
          accessRequestDataConverter.convertToEntityAttribute(accessRequestRepository
              .findTopByDossierAccessRequest_IdOrderByIdDesc(dossierId).orElseThrow(
                  () -> new NotFoundException(MessageConstant.ACCESS_REQUEST_FOR_DOSSIER_NOT_FOUND,
                      dossierId)));

      return lastDossierRequest.getTakingChargeClinicalCenter() != null
          ? lastDossierRequest.getTakingChargeClinicalCenter()
          : lastDossierRequest.getApplicantClinicalCenter();
    } catch (APIException e) {
      log.error(e.getMessage(), e);
      throw e;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new InternalErrorException(MessageConstant.INTERNAL_SERVER_ERROR_MSG);
    }
  }

  @Override
  public AccessRequestDataDTO getLastDossierAccessRequest(Long dossierId) {
    try {
      return accessRequestDataConverter.convertToEntityAttribute(accessRequestRepository
          .findTopByDossierAccessRequest_IdOrderByIdDesc(dossierId).orElseThrow(
              () -> new NotFoundException(MessageConstant.ACCESS_REQUEST_FOR_DOSSIER_NOT_FOUND,
                  dossierId)));
    } catch (APIException e) {
      log.error(e.getMessage(), e);
      throw e;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new InternalErrorException(MessageConstant.INTERNAL_SERVER_ERROR_MSG);
    }
  }

  @Override
  public List<AccessRequestDataDTO> getAccessRequestData(Long dossierId) {

    log.info("Get dossier with id: {}", dossierId);
    try {

      String userRole = authenticationFacade.getUserRole();

      DossierAccessRequest dossierAccessRequest =
          this.dossierAccessRequestRepository.findById(dossierId).orElseThrow(
              () -> new NotFoundException(MessageConstant.DOSSIER_NOT_FOUND, dossierId));

      BooleanExpression predicate =
          accessRequest.dossierAccessRequest().id.eq(dossierAccessRequest.getId());

      switch (Role.valueOf(userRole)) {

        case MEDICO:
          // FIXME: si può utilizzare un predicate simile a quello del farmacista?
          String loggedUserUid = authenticationFacade.getUserUid();

          List<AccessRequest> result =
              accessRequestRepository.findDoctorRequests(dossierId, loggedUserUid);
          return accessRequestDataConverter.convertAllToEntityAttribute(result);
        case DIRETTORE_SANITARIO:
          // FIXME qui manca la parte di ordinamento e di paginazione
          return this.buildDirettoreSanitarioPredicate(dossierId);
        case DIRETTORE_AMMINISTRATIVO:
        case FARMACISTA:
          predicate = buildPharmacistPredicate(predicate);
          break;
        case AIFA_UCB:
        case AIFA_APA:
        case AIFA_APA_SIGNER:
          predicate = buildApaPredicate(predicate);
          break;
        default:
          break;
      }

      log.info("Predicate {}", predicate);

      Iterable<AccessRequest> queryResult = accessRequestRepository.findAll(predicate,
          Sort.by(Sort.Direction.fromString("desc"), "id"));

      List<AccessRequest> result = IterableUtils.toList(queryResult);

      return accessRequestDataConverter.convertAllToEntityAttribute(result);
    } catch (APIException e) {
      log.error(e.getMessage(), e);
      throw e;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new InternalErrorException(MessageConstant.INTERNAL_SERVER_ERROR_MSG);
    }
  }

  private BooleanExpression buildPharmacistPredicate(BooleanExpression predicate) {
    String userId = authenticationFacade.getUserUid();

    // FIXME verificare in seguito con il servizio di gestione profile
    UserRights userAbilitazini = gestioneProfiliService.getAbilitazioni(userId, "0");

    return predicate
        .and(addAccessRequestOwnClinicalCenterPredicate((String) userAbilitazini.getSis()))
        .andAnyOf(accessRequest.statusAccessRequest().id.notIn(
            StatusAccessRequestEnum.DRAFT.getId(), StatusAccessRequestEnum.PRESUBMIT.getId(),
            StatusAccessRequestEnum.RETIRED.getId()));
  }

  private List<AccessRequestDataDTO> buildDirettoreSanitarioPredicate(Long dossierId) {
    String userId = authenticationFacade.getUserUid();
    // FIXME verificare in seguito con il servizio di gestione profile
    UserRights userAbilitazioni = gestioneProfiliService.getAbilitazioni(userId, "0");

    List<Long> statusLists = Lists.newArrayList(StatusAccessRequestEnum.DRAFT.getId());
    statusLists.add(StatusAccessRequestEnum.PRESUBMIT.getId());
    List<AccessRequest> result = accessRequestRepository.findDirettoreSanitarioRequests(dossierId,
        userId, (String) userAbilitazioni.getSis(), statusLists);
    return this.accessRequestDataConverter.convertAllToEntityAttribute(result);
  }

  private BooleanExpression buildApaPredicate(BooleanExpression predicate) {
    log.info("Build Predicate for APA");
    BooleanExpression apaPredicate = accessRequest.userId.eq(authenticationFacade.getUserUid());
    apaPredicate = apaPredicate.or(accessRequest.statusAccessRequest().id
        .notIn(StatusAccessRequestEnum.DRAFT.getId(), StatusAccessRequestEnum.PRESUBMIT.getId()));
    return predicate.and(apaPredicate);
  }

  private BooleanExpression addAccessRequestOwnClinicalCenterPredicate(String clinicalCentercode) {
    return accessRequest.stAccTakingCharge().clinicalCenterCode.isNotEmpty()
        .and(accessRequest.stAccTakingCharge().clinicalCenterCode.length().gt(0))
        .and(accessRequest.stAccTakingCharge().clinicalCenterCode.eq(clinicalCentercode))
        .or(accessRequest.stAccTakingCharge().clinicalCenterCode.isNull()
            .or(accessRequest.stAccTakingCharge().clinicalCenterCode.length().eq(0))
            .and(accessRequest.stAccApplicant().clinicalCenterCode.eq(clinicalCentercode)));
  }

  @Override
  public AccessRequestDataDTO renewalAccessRequest(Long accessRequestId) {
    try {
      AccessRequest requestToCopy = accessRequestRepository.findById(accessRequestId).orElseThrow(
          () -> new NotFoundException("Access request with id {} not found", accessRequestId));

      if (requestToCopy.getChildRenewalRequest() != null) {
        throw new InternalErrorException(
            "The access request can be renewed only once, request with id {} already has a renewal child",
            accessRequestId);
      }

      StatusAccessRequest statusRequest =
          statusAccessRequestRepository.findById(StatusAccessRequestEnum.DRAFT.getId()).orElseThrow(
              () -> new NotFoundException(MessageConstant.STATUS_REQUEST_WITH_NAME_NOT_FOUND,
                  StatusAccessRequestEnum.DRAFT));

      AccessRequest renewalRequest = new AccessRequest();

      // FIXME DEVE essere un rinnovo, quindi i campi di rinnovo DEVONO essere valorizzati
      renewalRequest.setParentRenewalRequest(requestToCopy);
      renewalRequest.setRenewal(false);
      renewalRequest.setStatusAccessRequest(statusRequest);
      renewalRequest.setCreatedByApa(false);
      renewalRequest.setUserId(authenticationFacade.getUserUid());

      // Constructor deep copy
      renewalRequest.setStAccApplicant(new StAccApplicant(requestToCopy.getStAccApplicant()));
      renewalRequest.setStAccPatient(new StAccPatient(requestToCopy.getStAccPatient()));
      renewalRequest.setStAccDrug(new StAccDrug(requestToCopy.getStAccDrug(), false));
      renewalRequest.setStAccTherapeuticAreas(
          new StAccTherapeuticArea(requestToCopy.getStAccTherapeuticAreas()));
      renewalRequest
          .setStAccClinicalData(new StAccClinicalData(requestToCopy.getStAccClinicalData(), false));
      renewalRequest.setStAccScientificDocument(
          new StAccScientificDocument(requestToCopy.getStAccScientificDocument()));

      DossierAccessRequest dossierToCopy = requestToCopy.getDossierAccessRequest();

      DossierAccessRequest dossierRenewal = new DossierAccessRequest();
      dossierRenewal.setRenewalDossierId(dossierToCopy.getId());
      dossierRenewal.addAccessRequest(renewalRequest);

      dossierRenewal = dossierAccessRequestRepository.saveAndFlush(dossierRenewal);
      log.info("Created new Dossier with id {} for renewal Access request ",
          dossierRenewal.getId());

      // Create new folder for dossier
      AlfrescoFolderDTO folderDtoDossier =
          alfrescoService.createNewRequestFolder(dossierRenewal.getId());

      if (folderDtoDossier == null) {
        throw new AlfrescoClientException("Folder created for new dossier is null");
      }
      dossierRenewal.setAlfrescoFolderId(folderDtoDossier.getFolderId());
      dossierRenewal.setAlfrescoFolderPath(folderDtoDossier.getFolderPath());
      Folder folderDossier = alfrescoClient.getFolderByPath(folderDtoDossier.getFolderPath());

      Folder requestFolder =
          alfrescoClient.createFolder("ACC-REQ-" + renewalRequest.getId(), folderDossier);

      if (requestFolder != null) {
        renewalRequest.setAlfrescoFolderId(requestFolder.getId());
        renewalRequest.setAlfrescoFolderPath(requestFolder.getPath());
      }

      copyDrugsDocument(requestToCopy, renewalRequest);
      copyScientificDocument(requestToCopy, renewalRequest);

      dossierRenewal = dossierAccessRequestRepository.saveAndFlush(dossierRenewal);
      log.info(
          "Saved new Dossier (id: {}) with all information for renewal access request and start converting to DTO",
          dossierRenewal.getId());
      return this.accessRequestDataConverter
          .convertToEntityAttribute(dossierRenewal.getAccessRequests().get(0));

    } catch (APIException e) {
      log.error(e.getMessage(), e);
      throw e;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new InternalErrorException(MessageConstant.INTERNAL_SERVER_ERROR_MSG);
    }
  }

  /**
   * 
   * @param requestToCopy
   * @param renewalStScientificDocument
   * @param requestRenewal
   */
  public void copyScientificDocument(AccessRequest requestToCopy, AccessRequest requestRenewal) {
    log.info(
        "Start coping Scientific documents from Alfresco Access Request Folder with path {} to Alfresco Access Request Folder with path {}",
        requestToCopy.getAlfrescoFolderPath(), requestRenewal.getAlfrescoFolderPath());
    for (ScientificDocument document : requestToCopy.getStAccScientificDocument()
        .getScientificDocuments()) {
      Document d = alfrescoService.copyDocumentInNewFolder(document.getAlfrescoDocumentId(),
          requestRenewal.getAlfrescoFolderPath());
      for (ScientificDocument renewalDocument : requestRenewal.getStAccScientificDocument()
          .getScientificDocuments()) {
        if (renewalDocument.equals(document)) {
          renewalDocument.setAlfrescoDocumentId(d.getId());
          renewalDocument.setFileName(d.getName());
        }
      }
    }
  }

  /**
   * 
   * @param requestToCopy
   * @param renewalStDrug
   * @param requestRenewal
   */
  public void copyDrugsDocument(AccessRequest requestToCopy, AccessRequest requestRenewal) {
    log.info(
        "Start coping drugs document from Alfresco Access Request Folder with path {} to Alfresco Access Request Folder with path {}",
        requestToCopy.getAlfrescoFolderPath(), requestRenewal.getAlfrescoFolderPath());
    for (RequestDrug drug : requestToCopy.getStAccDrug().getRequestDrugs()) {
      Document d = alfrescoService.copyDocumentInNewFolder(drug.getAlfrescoReceiptId(),
          requestRenewal.getAlfrescoFolderPath());
      for (RequestDrug renewalDrug : requestRenewal.getStAccDrug().getRequestDrugs()) {
        if (renewalDrug.equals(drug)) {
          renewalDrug.setAlfrescoReceiptId(d.getId());
          renewalDrug.setDocumentReceiptName(d.getName());
        }
      }
    }
  }

  @Override
  public AccessRequestDataDTO retireAccessRequest(Long accessRequestId) {
    try {
      log.info("Start retire access request with id : {}", accessRequestId);

      AccessRequest accessRequest =
          this.accessRequestRepository.findById(accessRequestId).orElseThrow(
              () -> new NotFoundException("AccessRequest not found id : {}", accessRequestId));

      // posso ritirare una richiesta quando lo stato è 'compreso tra' SUBMITED e PARERE
      // FAVOREVOLE/SFAVOREVOLE
      if (Stream
          .of(StatusAccessRequestEnum.SUBMITTED, StatusAccessRequestEnum.REVISING,
              StatusAccessRequestEnum.VALIDATED, StatusAccessRequestEnum.SCIENTIFICALLY_EVALUATED,
              StatusAccessRequestEnum.DISCUSS_IN_SECRETARIAT,
              StatusAccessRequestEnum.IN_SECRETARIAT, StatusAccessRequestEnum.OPINION_ADDED,
              StatusAccessRequestEnum.OPINION_NEGATIVE, StatusAccessRequestEnum.OPINION_POSITIVE)
          .noneMatch(
              status -> status.name().equals(accessRequest.getStatusAccessRequest().getCode()))) {

        throw new InternalErrorException(
            "Fail while retiring request. Status access request is: {}",
            accessRequest.getStatusAccessRequest().getCode());
      }

      // FIXME bug 252, la richiesta potrebbe essere ritirata prima di aver inserito un quote, in
      // questo caso non deve dare errore
      Optional<Quote> quoteOp =
          this.quoteRepository.findTopByDossierAccessRequest_IdAndDeletedIsFalseOrderByIdDesc(
              accessRequest.getDossierAccessRequest().getId());
      // .orElseThrow(() -> new NotFoundException(MessageConstant.QUOTE_FOR_DOSSIER_NOT_FOUND,
      // accessRequest.getDossierAccessRequest().getId()));

      if (quoteOp.isPresent()) {
        this.quoteService.updateQuoteStatus(quoteOp.get().getId(), StatusQuoteEnum.INVALID.getId());
      }
      this.accessRequestService.updateAccessRequestStatus(accessRequestId,
          StatusAccessRequestEnum.RETIRED.getId());

      NotificationMessageDTO messageDTO = new NotificationMessageDTO(accessRequest.getId(),
          EmailTemplateEnum.RETIRED_ACCESS_REQUEST,
          NotificationTemplateEnum.RETIRED_ACCESS_REQUEST);
      List<String> params = new ArrayList<>();
      String userName = authenticationFacade.getGivenName();
      params.add(userName);
      super.sendEmailNotificationToDoctors(params, accessRequest, messageDTO);
      AccessRequestDataDTO dataDto =
          this.accessRequestDataConverter.convertToEntityAttribute(accessRequest);

      log.info("End retire access request with id : {}", accessRequestId);
      return dataDto;
    } catch (APIException e) {
      log.error(e.getMessage(), e);
      throw e;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new InternalErrorException(MessageConstant.INTERNAL_SERVER_ERROR_MSG);
    }
  }

}
