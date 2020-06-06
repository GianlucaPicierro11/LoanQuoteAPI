//package it.loanquote.repositories;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Optional;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.querydsl.QuerydslPredicateExecutor;
//import org.springframework.stereotype.Repository;
//import it.aifa.cinquepercento.entity.AccessRequest;
//
//@Repository
//public interface AccessRequestRepository
//    extends JpaRepository<AccessRequest, Long>, QuerydslPredicateExecutor<AccessRequest> {
//
//  public Optional<AccessRequest> findByIdAndDeletedIsFalse(Long id);
//
//  public Optional<AccessRequest> findByIdAndDeletedIsFalseAndStatusAccessRequest_Code(Long id,
//      String status);
//
//  public List<AccessRequest> findByStatusAccessRequest_CodeInAndUserId(List<String> statusList,
//      String uid);
//
//  public List<AccessRequest> findByStatusAccessRequest_CodeInAndUserId_AndDossierAccessRequest_Id(
//      List<String> statusList, String uid, Long dossierId);
//
//  public Optional<AccessRequest> findTopByDossierAccessRequest_IdOrderByIdDesc(Long dossierId);
//
//  public List<AccessRequest> findByDossierAccessRequest_IdOrderByIdDesc(Long dossierId);
//
//  // FIXME TROVARE IL MODO DI FARE UNA QUERY DSL
//  @Query(value = "select * from ACCESS_REQUESTS ar \n"
//      + "LEFT JOIN (ST_ACC_APPLICANT ap, ST_ACC_TAKING_CHARGE tg) ON (ar.ID = ap.REQUEST_ID  AND ar.ID = tg.REQUEST_ID)\n"
//      + "WHERE ar.DOSSIER_ID = ?1\n"
//      + "AND (ar.USER_ID = ?2 OR ap.DOCTOR_USER_ID=?2 OR tg.DOCTOR_UID= ?2)\n"
//      + "ORDER BY ar.ID DESC", nativeQuery = true)
//  public List<AccessRequest> findDoctorRequests(Long dossierId, String userId);
//
//  // FIXME TROVARE IL MODO DI FARE UNA QUERY DSL
//  @Query(value = "select * from ACCESS_REQUESTS ar\n"
//      + " LEFT JOIN ST_ACC_APPLICANT ap ON ar.ID = ap.REQUEST_ID \n"
//      + " LEFT JOIN ST_ACC_TAKING_CHARGE tg  ON ar.ID = tg.REQUEST_ID \n"
//      + " WHERE ar.DOSSIER_ID = ?1\n"
//      + " AND (ar.USER_ID = ?2 or ((ap.DOCTOR_USER_ID=?2 or ap.CLINICAL_CENTER_CODE =?3\n"
//      + " or tg.clinical_center_code = ?3  OR tg.DOCTOR_UID= ?2) and ar.status_id not in ?4))"
//      + "ORDER BY ar.ID DESC", nativeQuery = true)
//  public List<AccessRequest> findDirettoreSanitarioRequests(Long dossierId, String userId,
//      String clinicalCenterCode, Collection<Long> statusList);
//
//}
//
//
