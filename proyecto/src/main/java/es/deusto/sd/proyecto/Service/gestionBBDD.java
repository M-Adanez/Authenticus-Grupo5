package es.deusto.sd.proyecto.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.deusto.sd.proyecto.DAO.caseInvestigationRepository;
import es.deusto.sd.proyecto.DAO.userRepository;
import es.deusto.sd.proyecto.DTO.userDTO;
import es.deusto.sd.proyecto.Entity.CaseInvestigation;
import es.deusto.sd.proyecto.Entity.User;

@Service
public class gestionBBDD {

    private final userRepository userRepo;
    private final caseInvestigationRepository ciRepo;

    public gestionBBDD(userRepository userRepo, caseInvestigationRepository ciRepo) {
        this.userRepo = userRepo;
        this.ciRepo = ciRepo;
    }

    // ==========================================
    // MÉTODOS PARA USUARIOS
    // ==========================================

    @Transactional
    public APIResponse registerUser(userDTO usDTO) {
        if (userRepo.findByUsername(usDTO.getUsername()).isPresent()) {
            return APIResponse.YA_REGISTRADO;
        }
        
        User newUser = new User();
        newUser.setUsername(usDTO.getUsername());
        newUser.setPassword(usDTO.getPassword());
        // Asigna el resto de campos si existen en tu entidad User
        
        userRepo.save(newUser);
        return APIResponse.CREADO;
    }

    @Transactional
    public APIResponse deleteUserByUsername(String username) {
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isPresent()) {
            userRepo.delete(user.get());
            return APIResponse.BIEN;
        }
        return APIResponse.NO_EXISTE;
    }

    public userDTO getUserByUsername(String username) {
        Optional<User> us=userRepo.findByUsername(username);
        if(us.isPresent()){
            userDTO usDTO=new userDTO(us.get());
            return usDTO;
        }
        return null;
    }

    // ==========================================
    // MÉTODOS PARA INVESTIGACIONES (Cases)
    // ==========================================

    @Transactional
    public void saveCase(CaseInvestigation ci) {
        ciRepo.save(ci);
    }

    public List<CaseInvestigation> findAllByUserId(Long userId) {
        return ciRepo.findAllByUserId(userId);
    }

    public CaseInvestigation findCaseById(Long id) {
        return ciRepo.findById(id).orElse(null);
    }

    @Transactional
    public void updateCase(CaseInvestigation ci) {
        // save() en JPA sirve tanto para crear como para actualizar si el ID ya existe
        ciRepo.save(ci);
    }

    @Transactional
    public void deleteCase(Long id) {
        ciRepo.deleteById(id);
    }
}