package es.deusto.sd.proyecto.Service;

import org.springframework.stereotype.Service;

import es.deusto.sd.proyecto.DAO.userRepository;
import es.deusto.sd.proyecto.DAO.caseInvestigationRepository;
import es.deusto.sd.proyecto.DTO.CaseInvestigationDTO;
import es.deusto.sd.proyecto.DTO.userDTO;
import es.deusto.sd.proyecto.Entity.User;
import es.deusto.sd.proyecto.Entity.CaseInvestigation;

import java.util.Optional;

@Service
public class gestionBBDD {

    private final userRepository usRepository;
    private final caseInvestigationRepository ciRepository;

    public gestionBBDD(userRepository usRepository, caseInvestigationRepository ciRepository) {
        this.usRepository = usRepository;
        this.ciRepository = ciRepository;
    }

    // ----------------------------
    // USUARIOS
    // ----------------------------

    public APIResponse registerUser(userDTO userDTO){

        if (userDTO.getUsername() == null || userDTO.getUsername().isEmpty() ||
            userDTO.getPassword() == null || userDTO.getPassword().isEmpty() ||
            userDTO.getNombre() == null || userDTO.getNombre().isEmpty() ||
            userDTO.getTlf() == null || userDTO.getTlf().isEmpty()) {

            return APIResponse.FALTAN_DATOS;
        }

        if (usRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            return APIResponse.YA_REGISTRADO;
        }

        User user = new User(userDTO);
        usRepository.save(user);

        return APIResponse.CREADO;
    }

    public APIResponse deleteUserByUsername(String username){

        Optional<User> user = usRepository.findByUsername(username);

        if(user.isEmpty()){
            return APIResponse.NO_EXISTE;
        }

        usRepository.delete(user.get());

        return APIResponse.BIEN;
    }

    public userDTO getUserByUsername(String username) {

        return usRepository.findByUsername(username)
                        .map(user -> new userDTO(
                                user.getUsername(),
                                user.getPassword(),
                                user.getNombre(),
                                user.getTlf()
                        ))
                        .orElse(null);
    }
    

}
