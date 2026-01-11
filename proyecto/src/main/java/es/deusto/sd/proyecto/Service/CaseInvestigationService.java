package es.deusto.sd.proyecto.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import es.deusto.sd.proyecto.Entity.AnalysisType;
import es.deusto.sd.proyecto.Entity.CaseInvestigation;
import es.deusto.sd.proyecto.Entity.User;
import es.deusto.sd.proyecto.Gateway.AnalysisGateway;
import es.deusto.sd.proyecto.Gateway.DatabaseGateway;
import es.deusto.sd.proyecto.DTO.CaseInvestigationDTO;

@Service
public class CaseInvestigationService {

    private final stateManagement instance;
    private final AnalysisGateway analysisGateway;
    private final DatabaseGateway databaseGateway;

    public CaseInvestigationService(
            AnalysisGateway analysisGateway,
            DatabaseGateway databaseGateway
    ) {
        this.instance = stateManagement.getInstance();
        this.analysisGateway = analysisGateway;
        this.databaseGateway = databaseGateway;
    }

    /**
     * Crea una investigación enviando los datos a la BD mediante HTTP.
     */
    public APIResponse createCaseInvestigation(UUID token, CaseInvestigationDTO dto) {
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

        // Acceso a BD mediante HTTP a través del Gateway
        boolean success = databaseGateway.saveCase(ci);

        return success ? APIResponse.CREADO : APIResponse.MAL;
    }

    /**
     * Recupera las investigaciones del usuario mediante una petición HTTP GET.
     */
    public List<CaseInvestigationDTO> getCaseInvestigations(UUID token) {
        User user = instance.getUserByToken(token);
        
        if (user == null) return new ArrayList<>();

        // Acceso a BD mediante HTTP
        List<CaseInvestigation> all = databaseGateway.findAllByUserId(user.getId());
        
        List<CaseInvestigationDTO> allDTO = new ArrayList<>();
        for(CaseInvestigation ci : all){
            allDTO.add(new CaseInvestigationDTO(ci));
        }
        return allDTO;
    }

    /**
     * Recupera los últimos N casos.
     */
    public List<CaseInvestigationDTO> getCaseInvestigationsN(UUID token, int N) {
        List<CaseInvestigationDTO> all = getCaseInvestigations(token);
        
        return all.stream()
                  .limit(N)
                  .toList();
    }

    /**
     * Filtra investigaciones por fecha tras obtenerlas vía HTTP.
     */
    public List<CaseInvestigationDTO> getCaseInvestigationsInDate(UUID token, Date start, Date end) {
        return getCaseInvestigations(token)
                .stream()
                .filter(ci -> ci.getDate().after(start) && ci.getDate().before(end))
                .toList();
    }

    /**
     * Elimina una investigación solicitándolo mediante HTTP DELETE.
     */
    public void deleteCaseInvestigation(UUID token, Long id) {
        // Obtenemos el caso primero para verificar propiedad
        CaseInvestigation ci = databaseGateway.findById(id);

        if (ci != null) {
            User user = instance.getUserByToken(token);
            if (user != null && ci.getUserId().equals(user.getId())) {
                databaseGateway.deleteCase(id);
            }
        }
    }

    /**
     * Procesa resultados usando Gateways externos (HTTP).
     */
    public Map<AnalysisType, Float> showCaseInvestigationResults(UUID token, Long id) {
        CaseInvestigation ci = databaseGateway.findById(id);

        if (ci == null) return Map.of();

        User user = instance.getUserByToken(token);
        if (user == null || !ci.getUserId().equals(user.getId())) {
            return Map.of();
        }

        // Procesamiento remoto
        CaseInvestigation processed = analysisGateway.sendToRemoteProcessing(ci);

        // Guardado de resultados mediante HTTP
        databaseGateway.saveResults(processed);

        return processed.getResults();
    }

    /**
     * Añade archivos a un caso existente mediante HTTP PUT/POST.
     */
    public APIResponse addFilesToCase(UUID token, List<String> filesURL, Long id) {
        User user = instance.getUserByToken(token);
        if (user == null) {
            return APIResponse.NO_EXISTE;
        }

        CaseInvestigation ci = databaseGateway.findById(id);
        if (ci == null) {
            return APIResponse.NO_EXISTE;
        }

        if (!ci.getUserId().equals(user.getId())) {
            return APIResponse.MAL;
        }

        if (filesURL == null || filesURL.isEmpty()) {
            return APIResponse.FALTAN_DATOS;
        }

        // Actualizamos la lista localmente
        ci.getImageList().addAll(filesURL);

        // Enviamos la actualización a la BD mediante HTTP
        boolean success = databaseGateway.updateCase(ci);

        return success ? APIResponse.BIEN : APIResponse.MAL;
    }
}