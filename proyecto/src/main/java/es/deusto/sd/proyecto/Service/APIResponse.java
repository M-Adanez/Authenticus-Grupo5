package es.deusto.sd.proyecto.Service;

import org.springframework.http.HttpStatus;

public enum APIResponse {
    FALTAN_DATOS(HttpStatus.BAD_REQUEST,"Faltan Datos"),
    YA_REGISTRADO(HttpStatus.CONFLICT,"Ya Registrado"),
    YA_LOGEADO(HttpStatus.CONFLICT,"Ya Logeado"),
    CREADO(HttpStatus.CREATED,"Creado"),
    CONTRASEÑA_MAL(HttpStatus.UNAUTHORIZED,"Contraseña Erronea"),
    NO_EXISTE(HttpStatus.NOT_FOUND,"No Existe"),
    BIEN(HttpStatus.OK,"Correcto"),
    MAL(HttpStatus.BAD_REQUEST,"Solicitud Erronea"),
    TOKEN(HttpStatus.OK,"");

    private final HttpStatus status;
    private String mensaje;


    APIResponse(HttpStatus stat,String mens){
        this.status=stat;
        this.mensaje=mens;
    }

    public HttpStatus getStatus(){
        return this.status;
    }

    public String getMensaje(){
        return this.mensaje;
    }

    public void setMensaje(String mens){
        this.mensaje=mens;
    }
}
