package es.deusto.sd.proyecto.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.deusto.sd.proyecto.Entity.CaseInvestigation;

@Repository
public interface caseInvestigationRepository extends JpaRepository<CaseInvestigation, Long> {

    List<CaseInvestigation> findAllByUserId(Long userId);

}
