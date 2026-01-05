package es.deusto.sd.proyecto.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.deusto.sd.proyecto.DTO.CaseInvestigationDTO;
import es.deusto.sd.proyecto.DTO.userDTO;
import es.deusto.sd.proyecto.Entity.CaseInvestigation;
import es.deusto.sd.proyecto.Service.APIResponse;
import es.deusto.sd.proyecto.Service.gestionBBDD;

import es.deusto.sd.proyecto.Entity.User;




import java.util.*;


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

    // =============================
    // USER MANAGEMENT
    // =============================

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
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.ok(dto);
    }


    // =============================
    // CASE INVESTIGATION
    // =============================

    // @Operation(summary = "Create case Investigation")
    // @PostMapping("/createCI")

    // public ResponseEntity<String> createCI(@RequestBody CaseInvestigationDTO ciDTO){

    //     APIResponse response = gBD.createCaseInvestigation(ciDTO);

    //     return new ResponseEntity<>(response.getMensaje(), response.getStatus());
    // }

}
