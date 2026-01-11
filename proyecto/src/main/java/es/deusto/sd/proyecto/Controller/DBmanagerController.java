package es.deusto.sd.proyecto.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.deusto.sd.proyecto.DTO.userDTO;
import es.deusto.sd.proyecto.Entity.CaseInvestigation;
import es.deusto.sd.proyecto.Service.APIResponse;
import es.deusto.sd.proyecto.Service.gestionBBDD;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/DB")
@Tag(name = "DB managing controller", description = "Operations and management of DB")
public class DBmanagerController {

    private final gestionBBDD gBD;

    public DBmanagerController(gestionBBDD gBD){
        this.gBD = gBD;
    }

    // ==========================================
    // USER MANAGEMENT (Gestión de Usuarios)
    // ==========================================

    @Operation(summary = "Store a new user")
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody userDTO usDTO) {
        APIResponse response = gBD.registerUser(usDTO);
        return new ResponseEntity<>(
                response.getMensaje(),
                response.getStatus()
        );
    }

    @Operation(summary = "Delete user information")
    @DeleteMapping("/delete/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable("username") String username){
        APIResponse response = gBD.deleteUserByUsername(username);
        return new ResponseEntity<>(response.getMensaje(), response.getStatus());
    }

    @Operation(summary = "Get user by username")
    @GetMapping("/user/{username}")
    public ResponseEntity<userDTO> getUserByUsername(@PathVariable String username) {
        userDTO dto = gBD.getUserByUsername(username);
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(dto);
    }

    // ==========================================
    // CASE INVESTIGATION (Gestión de Investigaciones vía HTTP)
    // ==========================================

    @Operation(summary = "Save a new case investigation")
    @PostMapping("/cases")
    public ResponseEntity<Void> saveCase(@RequestBody CaseInvestigation ci) {
        gBD.saveCase(ci); // Delega la persistencia real a gestionBBDD
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Find cases by User ID")
    @GetMapping("/cases/user/{userId}")
    public ResponseEntity<List<CaseInvestigation>> getCasesByUserId(@PathVariable Long userId) {
        List<CaseInvestigation> cases = gBD.findAllByUserId(userId); // Recupera lista de la BD
        return ResponseEntity.ok(cases);
    }

    @Operation(summary = "Find case by ID")
    @GetMapping("/cases/{id}")
    public ResponseEntity<CaseInvestigation> getCaseById(@PathVariable Long id) {
        CaseInvestigation ci = gBD.findCaseById(id);
        if (ci == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ci);
    }

    @Operation(summary = "Update an existing case")
    @PutMapping("/cases/{id}")
    public ResponseEntity<Void> updateCase(@PathVariable Long id, @RequestBody CaseInvestigation ci) {
        // Asegura que el objeto tiene el ID correcto antes de actualizar
        gBD.updateCase(ci); 
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete a case")
    @DeleteMapping("/cases/{id}")
    public ResponseEntity<Void> deleteCase(@PathVariable Long id) {
        gBD.deleteCase(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Save processed results")
    @PostMapping("/cases/results")
    public ResponseEntity<Void> saveResults(@RequestBody CaseInvestigation processed) {
        gBD.updateCase(processed);
        return ResponseEntity.ok().build();
    }
}