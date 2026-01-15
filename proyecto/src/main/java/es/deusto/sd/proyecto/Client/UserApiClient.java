package es.deusto.sd.proyecto.Client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import es.deusto.sd.proyecto.DTO.userDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.deusto.sd.proyecto.Entity.User;
import es.deusto.sd.proyecto.Service.APIResponse;

import org.springframework.stereotype.Component;

@Component
public class UserApiClient {

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    private final String BASE_URL = "http://localhost:8081/DB";

    public APIResponse createUser(userDTO usDTO) {

        try {
            String json = mapper.writeValueAsString(usDTO);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/register"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            int code = response.statusCode();

            switch (code) {
                case 201:
                    return APIResponse.CREADO;

                case 400:
                    return APIResponse.FALTAN_DATOS;

                case 409:
                    return APIResponse.YA_REGISTRADO;

                default:
                    return APIResponse.MAL;
            }

        } catch (Exception e) {
            return APIResponse.MAL;
        }
    }


    public userDTO getUserByUsername(String username) {

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/user/" + username))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println(response.body());

            if (response.statusCode() == 404) {
                return null;
            }

            if (response.statusCode() != 200) {
                System.out.println(response.statusCode());
                return null;
            }

            return mapper.readValue(response.body(), userDTO.class);

        } catch (Exception e) {
            return null;
        }
    }


    public APIResponse deleteUserByUsername(String username) {

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/delete/" + username))
                    .DELETE()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            return switch (response.statusCode()) {
                case 200 -> APIResponse.BIEN;
                case 404 -> APIResponse.NO_EXISTE;
                default -> APIResponse.MAL;
            };

        } catch (Exception e) {
            return APIResponse.MAL;
        }
    }

    
}
