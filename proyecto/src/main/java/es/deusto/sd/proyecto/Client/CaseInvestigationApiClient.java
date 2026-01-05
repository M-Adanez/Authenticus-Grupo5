package es.deusto.sd.proyecto.Client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.deusto.sd.proyecto.DTO.CaseInvestigationDTO;
import es.deusto.sd.proyecto.Service.APIResponse;

@Controller
public class CaseInvestigationApiClient {
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private static final String BASE_URL = "http://localhost:8081/DB";

        public APIResponse createCaseInvestigation(CaseInvestigationDTO ciDTO) {

        try {
            String json = mapper.writeValueAsString(ciDTO);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/createCI"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

            HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

            return switch (response.statusCode()) {
                case 201 -> APIResponse.CREADO;
                case 400 -> APIResponse.FALTAN_DATOS;
                default -> APIResponse.MAL;
            };

        } catch (Exception e) {
            return APIResponse.MAL;
        }
    
    }

    public void deleteUser(Long id) throws Exception{
        String address = "http://localhost/users/" + id;

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(address))
            .header("Content-Type", "application/json")
            .GET()
            .build();

        HttpResponse<String> response =
        client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 201 && response.statusCode() != 200) {
            throw new RuntimeException("Error HTTP: " + response.statusCode());
        }

    }

}
