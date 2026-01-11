package es.deusto.sd.proyecto.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import es.deusto.sd.proyecto.Entity.User;
public class userDTO {
        
    @Schema(description = "Id del usuario", example = "1")
    private Long id;

    @Schema(description = "Email del usuario", example = "user1@example.com")
    private String username;

    @Schema(description = "Contraseña del Usuario", example = "password123")
    private String password;
    
    @Schema(description = "Nombre del Usuario", example = "Unai Lopez Perez")
    private String nombre;

    @Schema(description = "Numero de Telefono del Usuario", example = "123456789")
    private String tlf;

    public userDTO() {
    }

    public userDTO(String username, String password){
        this.username=username;
        this.password=password;
    }

    public userDTO(String username, String password, String nombre, String tlf) {
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.tlf = tlf;
    }

    public userDTO(Long id, String username, String password, String nombre, String tlf) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.tlf = tlf;
    }

    public userDTO(User us){
        this.id = us.getId();
        this.username = us.getUsername();
        this.password = us.getPassword();
        this.nombre = us.getPassword();
        this.tlf = us.getTlf();
    }

    public Long getId(){
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTlf() {
        return this.tlf;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }

}
