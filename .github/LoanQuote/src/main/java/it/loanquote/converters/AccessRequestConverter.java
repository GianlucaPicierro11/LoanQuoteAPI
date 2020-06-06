package it.loanquote.converters;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;
import it.aifa.cinquepercento.dto.AccessRequestDTO;
import it.aifa.cinquepercento.dto.StAccDrugDTO;
import it.aifa.cinquepercento.dto.StAccGenerateRequestDTO;
import it.aifa.cinquepercento.dto.StAccTherapeuticAreaDTO;
import it.aifa.cinquepercento.entity.AccessRequest;
import it.aifa.cinquepercento.entity.DurationLabel;
import it.aifa.cinquepercento.repository.DurationLabelRepository;
import it.aifa.mesh.entity.VMesh;
import it.aifa.mesh.repository.VMeshRepository;

@Converter
@Service
public class AccessRequestConverter implements AttributeConverter<AccessRequestDTO, AccessRequest> {

  @Autowired
  private StAccApplicantConverter stAccApplicantConverter;

  @Autowired
  private StAccPatientConverter stAccPatientConverter;

  @Autowired
  private StAccDrugConverter stAccDrugConverter;

  @Autowired
  private StAccClinicalDataConverter stAccClinicalDataConverter;

  @Autowired
  private StAccTherapeuticAreaConverter stAccTherapeuticAreaConverter;

  @Autowired
  private StAccTakingChargeConverter stAccTakingChargeConverter;

  @Autowired
  private StAccScientificDocumentConverter stAccScientificDocumentConverter;

  @Autowired
  private VMeshRepository meshRepository;

  @Autowired
  private DurationLabelRepository durationLabelRepository;

  @Autowired
  private StatusRequestConverter statusRequestConverter;

  @Override
  public AccessRequest convertToDatabaseColumn(AccessRequestDTO dto) {
    AccessRequest entity = new AccessRequest();
    BeanUtils.copyProperties(dto, entity, "statusAccessRequest");

    return entity;
  }

  @Override
  public AccessRequestDTO convertToEntityAttribute(AccessRequest entity) {

    AccessRequestDTO dto = new AccessRequestDTO();
    BeanUtils.copyProperties(entity, dto, "statusAccessRequest");
    if (entity.getStatusAccessRequest() != null) {
      dto.setStatusAccessRequest(
          statusRequestConverter.convertToEntityAttribute(entity.getStatusAccessRequest()));
    }
    dto.setDossierId(entity.getDossierAccessRequest().getId());
    return dto;
  }

  public StAccGenerateRequestDTO buildDatasourceAccessRequestPDF(AccessRequest accReq) {
    StAccGenerateRequestDTO result = new StAccGenerateRequestDTO();

    result.setStAccPatientDTO(
        stAccPatientConverter.convertToEntityAttribute(accReq.getStAccPatient()));
    result.setStAccApplicantDTO(
        stAccApplicantConverter.convertToEntityAttribute(accReq.getStAccApplicant()));

    if (accReq.getCreatedByApa() != null) {
      AccessRequestDTO accessRequestDTO = this.convertToEntityAttribute(accReq);
      accessRequestDTO.setCreatedByApa(accReq.getCreatedByApa());
      accessRequestDTO.setApaDocumentName(accReq.getApaDocumentName());
      result.setAccessRequestDTO(accessRequestDTO);
    }

    if (accReq.getAlfrescoApaDocumentId() != null) {
      AccessRequestDTO accessRequestDTO = this.convertToEntityAttribute(accReq);
      accessRequestDTO.setAlfrescoApaDocumentId(accReq.getAlfrescoApaDocumentId());
      result.setAccessRequestDTO(accessRequestDTO);
    }

    if (accReq.getStAccDrug() != null) {
      result.setStAccDrugDTO(stAccDrugConverter.convertToEntityAttribute(accReq.getStAccDrug()));
      StAccDrugDTO drugDto = result.getStAccDrugDTO();
      Optional<DurationLabel> duration =
          durationLabelRepository.findById(drugDto.getDurationLabelId());
      if (duration.isPresent()) {
        drugDto.setDurationLabel(duration.get().getName());
      }
    }

    result.setStAccClinicalDataDTO(
        stAccClinicalDataConverter.convertToEntityAttribute(accReq.getStAccClinicalData()));

    if (accReq.getStAccTherapeuticAreas() != null) {
      VMesh meshEntity =
          meshRepository.findByLanguageCodeIgnoreCaseAndTherapeuticAreaCodeAndDescriptionCode("it",
              accReq.getStAccTherapeuticAreas().getTherapeuticArea(),
              accReq.getStAccTherapeuticAreas().getTherapeuticAreaDescription());

      result.setStAccTherapeuticAreaDTO(stAccTherapeuticAreaConverter
          .convertToEntityAttribute(accReq.getStAccTherapeuticAreas()));
      // getting the string value for therapeuticArea and therapeuticAreaDescription
      if (meshEntity != null) {
        StAccTherapeuticAreaDTO dto = result.getStAccTherapeuticAreaDTO();
        dto.setTherapeuticAreaValue(meshEntity.getTherapeuticArea());
        dto.setTherapeuticAreaDescriptionValue(meshEntity.getDescription());
      }
    }

    result.setStAccScientificDocumentDTO(stAccScientificDocumentConverter
        .convertToEntityAttribute(accReq.getStAccScientificDocument()));

    result.setStAccTakingChargeDTO(
        stAccTakingChargeConverter.convertToEntityAttribute(accReq.getStAccTakingCharge()));

    result.setAccessRequestDTO(this.convertToEntityAttribute(accReq));

    return result;
  }

  public List<AccessRequestDTO> convertAllToEntityAttribute(List<AccessRequest> entities) {
    return Optional.ofNullable(entities).orElse(Lists.newArrayList()).stream()
        .map(this::convertToEntityAttribute).collect(Collectors.toList());
  }


}
