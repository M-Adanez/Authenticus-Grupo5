package es.deusto.sd.proyecto.Entity;

import es.deusto.sd.proyecto.DTO.userDTO;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Usuarios")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username; 
    private String password;
    private String nombre;
    private String tlf;


    public User() {
    }

    public User(String user, String pass){
        this.username = user;
        this.password = pass;
    }

    public User(userDTO userDTO){
        this.username = userDTO.getUsername();
        this.password = userDTO.getPassword();
        this.nombre = userDTO.getNombre();
        this.tlf = userDTO.getTlf();
    }

    public User(String username, String password, String nombre, String tlf) {
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.tlf = tlf;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User other = (User) obj;
        return java.util.Objects.equals(username, other.username) &&
               java.util.Objects.equals(password, other.password);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(username, password);
    }
}
