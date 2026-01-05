package es.deusto.sd.proyecto.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import es.deusto.sd.proyecto.DAO.caseInvestigationRepository;
import es.deusto.sd.proyecto.Entity.AnalysisType;
import es.deusto.sd.proyecto.Entity.CaseInvestigation;
import es.deusto.sd.proyecto.Entity.User;
import es.deusto.sd.proyecto.Gateway.AnalysisGateway;
import es.deusto.sd.proyecto.Gateway.DatabaseGateway;

@Service
public class CaseInvestigationService {

    private final stateManagement instance;
    private final caseInvestigationRepository ciRepository;
    private final AnalysisGateway analysisGateway;
    private final DatabaseGateway databaseGateway;

    public CaseInvestigationService(
            caseInvestigationRepository ciRepository,
            AnalysisGateway analysisGateway,
            DatabaseGateway databaseGateway
    ) {
        this.instance = stateManagement.getInstance();
        this.ciRepository = ciRepository;
        this.analysisGateway = analysisGateway;
        this.databaseGateway = databaseGateway;
    }

    public APIResponse createCaseInvestigation(UUID token, es.deusto.sd.proyecto.DTO.CaseInvestigationDTO dto) {

        User user = instance.getUserByToken(token);

        if (user == null) {
            return APIResponse.NO_EXISTE;
        }

        if (dto.getName() == null || dto.getType() == null || dto.getDate() == null) {
            return APIResponse.FALTAN_DATOS;
        }

        CaseInvestigation ci = new CaseInvestigation(
                dto.getName(),
                dto.getType(),
                dto.getDate(),
                dto.getImageList()
        );

        ci.setUserId(user.getId());

        ciRepository.save(ci);

        return APIResponse.CREADO;
    }

    public List<CaseInvestigation> getCaseInvestigations(UUID token) {
        User user = instance.getUserByToken(token);
        return new ArrayList<>(ciRepository.findAllByUserId(user.getId()));
    }

    public List<CaseInvestigation> getCaseInvestigationsN(UUID token, int N) {
        List<CaseInvestigation> all = getCaseInvestigations(token);

        return all.stream()
                .sorted((a,b) -> b.getDate().compareTo(a.getDate()))
                .limit(N)
                .toList();
    }

    public List<CaseInvestigation> getCaseInvestigationsInDate(UUID token, Date start, Date end) {
        return getCaseInvestigations(token)
                .stream()
                .filter(ci -> ci.getDate().after(start) && ci.getDate().before(end))
                .toList();
    }

    public void deleteCaseInvestigation(UUID token, Long id) {
        Optional<CaseInvestigation> ciOpt = ciRepository.findById(id);

        if (ciOpt.isPresent()) {
            CaseInvestigation ci = ciOpt.get();
            if (ci.getUserId().equals(instance.getUserByToken(token).getId())) {
                ciRepository.delete(ci);
            }
        }
    }

    public Map<AnalysisType, Float> showCaseInvestigationResults(UUID token, Long id) {

        Optional<CaseInvestigation> optCi = ciRepository.findById(id);

        if (optCi.isEmpty()) return Map.of();

        CaseInvestigation ci = optCi.get();

        if (!ci.getUserId().equals(instance.getUserByToken(token).getId())) {
            return Map.of();
        }

        CaseInvestigation processed = analysisGateway.sendToRemoteProcessing(ci);

        databaseGateway.saveResults(processed);

        return processed.getResults();
    }
}
