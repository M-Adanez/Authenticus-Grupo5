package es.deusto.sd.proyecto.Gateway;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import es.deusto.sd.proyecto.Entity.CaseInvestigation;

@Component
public class DatabaseGateway {

    private final RestTemplate restTemplate;
    // URL del microservicio que gestiona la base de datos
    private final String DB_SERVICE_URL = "http://localhost:8081/api/database/cases"; 

    public DatabaseGateway() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Guarda una nueva investigación mediante un POST HTTP.
     */
    public boolean saveCase(CaseInvestigation ci) {
        try {
            ResponseEntity<Void> response = restTemplate.postForEntity(DB_SERVICE_URL, ci, Void.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Recupera todos los casos de un usuario mediante un GET HTTP.
     */
    public List<CaseInvestigation> findAllByUserId(Long userId) {
        try {
            String url = DB_SERVICE_URL + "/user/" + userId;
            CaseInvestigation[] response = restTemplate.getForObject(url, CaseInvestigation[].class);
            return response != null ? Arrays.asList(response) : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Busca un caso específico por su ID mediante un GET HTTP.
     */
    public CaseInvestigation findById(Long id) {
        try {
            String url = DB_SERVICE_URL + "/" + id;
            return restTemplate.getForObject(url, CaseInvestigation.class);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Actualiza un caso existente (por ejemplo, para añadir archivos) mediante un PUT HTTP.
     */
    public boolean updateCase(CaseInvestigation ci) {
        try {
            String url = DB_SERVICE_URL + "/" + ci.getId();
            restTemplate.put(url, ci);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Elimina un caso por su ID mediante un DELETE HTTP.
     */
    public void deleteCase(Long id) {
        try {
            String url = DB_SERVICE_URL + "/" + id;
            restTemplate.delete(url);
        } catch (Exception e) {
            // Log error
        }
    }

    /**
     * Guarda los resultados procesados enviando un POST HTTP.
     */
    public void saveResults(CaseInvestigation processed) {
        try {
            String url = DB_SERVICE_URL + "/results";
            restTemplate.postForEntity(url, processed, Void.class);
        } catch (Exception e) {
            // Log error
        }
    }
}