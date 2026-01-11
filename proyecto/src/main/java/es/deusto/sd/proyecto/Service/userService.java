package es.deusto.sd.proyecto.Service;

import org.springframework.stereotype.Service;

import es.deusto.sd.proyecto.DTO.userDTO;
import es.deusto.sd.proyecto.Entity.User;
import es.deusto.sd.proyecto.Client.UserApiClient;
import java.util.UUID;
import es.deusto.sd.proyecto.DAO.userRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class userService {

    private final userRepository userRepository;
    private final stateManagement instance;
    private final UserApiClient client;

    public userService(UserApiClient client, userRepository userRepository) {
    this.instance = stateManagement.getInstance();
    this.client = client;
    this.userRepository = userRepository; // <--- Asigna el parámetro a la variable de clase
}

    private UUID generateToken(){
        return UUID.randomUUID();
    }


    // ----------------------------
    // REGISTER
    // ----------------------------
    public APIResponse registerUser(userDTO usDTO){

        APIResponse r = client.createUser(usDTO);

        return r;
    }


    // ----------------------------
    // LOGIN
    // ----------------------------
    public APIResponse loginUser(userDTO userDTO){

        if (userDTO.getUsername() == null || userDTO.getPassword() == null){
            return APIResponse.FALTAN_DATOS;
        }

        userDTO remoteUserDTO = null;

        try{
            remoteUserDTO = client.getUserByUsername(userDTO.getUsername());
        }catch(Exception e){
            return APIResponse.NO_EXISTE;
        }

        if (instance.getLoggedUssers().containsValue(new User(remoteUserDTO))){
            return APIResponse.YA_LOGEADO;
        }

        if (remoteUserDTO == null) {
            return APIResponse.NO_EXISTE;
        }

        if (!remoteUserDTO.getPassword().equals(userDTO.getPassword())){
            return APIResponse.CONTRASEÑA_MAL;
        }

        User remoteEntity = new User(remoteUserDTO);

        UUID token = generateToken();
        instance.addLogin(token, remoteEntity);

        APIResponse.TOKEN.setMensaje(token.toString());
        return APIResponse.TOKEN;
    }


    // ----------------------------
    // DELETE
    // ----------------------------
    public APIResponse deleteUser(UUID token){

        User user = instance.getUserByToken(token);

        if (user == null) {
            return APIResponse.NO_EXISTE;
        }

        APIResponse resp = client.deleteUserByUsername(user.getUsername());

        if (resp != APIResponse.BIEN){
            // No se ha podido borrar en DB → NO hacemos logout
            return resp;
        }

        // Si DB OK → eliminar de memoria
        instance.logout(token);

        return APIResponse.BIEN;
    }


    // ----------------------------
    // LOGOUT
    // ----------------------------
    public APIResponse logout(String token){

        UUID uuid = UUID.fromString(token);

        if (instance.getLoggedUssers().containsKey(uuid)){
            instance.logout(uuid);
            return APIResponse.BIEN;
        }

        return APIResponse.MAL;
    }

    // ----------------------------
    // ELIMINAR USUARIO DE LA BASE DE DATOS
    // ----------------------------
    @Transactional
    public boolean permaDeleteByUsername(String username) {
        // Primero comprobamos si el usuario existe por su nombre de usuario
        if (userRepository.existsByUsername(username)) { 
            userRepository.deleteByUsername(username);
            return true;
        }
        return false;
    }

}
