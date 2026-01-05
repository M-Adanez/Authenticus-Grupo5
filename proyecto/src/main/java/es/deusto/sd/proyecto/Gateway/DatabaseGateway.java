package es.deusto.sd.proyecto.Gateway;

import es.deusto.sd.proyecto.Entity.CaseInvestigation;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DatabaseGateway {

    private final String DB_SERVICE_URL = "http://localhost:8080/api/database/save-case";
    private final RestTemplate restTemplate;

    public DatabaseGateway() {
        this.restTemplate = new RestTemplate();
    }

    public void saveCaseRemotely(CaseInvestigation ci) {
        try {
            restTemplate.postForEntity(DB_SERVICE_URL, ci, Void.class);
        } catch (Exception e) {
            System.err.println("Error al guardar en BD vía HTTP: " + e.getMessage());
        }
    }

    // ⬇️ MÉTODO AÑADIDO
    public void saveResults(CaseInvestigation ci) {
        // reutilizamos tu método existente
        saveCaseRemotely(ci);
    }
}
