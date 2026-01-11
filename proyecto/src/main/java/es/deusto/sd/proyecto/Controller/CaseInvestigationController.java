package es.deusto.sd.proyecto.Controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.Map;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.deusto.sd.proyecto.Entity.CaseInvestigation;
import es.deusto.sd.proyecto.DTO.CaseInvestigationDTO; // Add this import
import es.deusto.sd.proyecto.Service.APIResponse;
import es.deusto.sd.proyecto.Service.CaseInvestigationService;
import es.deusto.sd.proyecto.Entity.AnalysisType;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/cases")
@Tag(name = "Case Investigation", description = "CRUD operations for Case Investigation")
public class CaseInvestigationController {

    private final CaseInvestigationService ciService;

    public CaseInvestigationController(CaseInvestigationService ciService){
        this.ciService = ciService;
    }

    // Create Case Investigation
    @Operation(summary = "Create a new Case Investigation")
    @PostMapping("/{token}")
    public ResponseEntity<String> createCase(
            @PathVariable("token") String token,
            @RequestBody CaseInvestigationDTO ciDTO) {

        APIResponse res = ciService.createCaseInvestigation(UUID.fromString(token), ciDTO);

        switch (res) {

            case CREADO:
                return new ResponseEntity<>("Caso Creado", HttpStatus.CREATED);

            case FALTAN_DATOS:
                return new ResponseEntity<>("Faltan Datos", HttpStatus.BAD_REQUEST);
            
            case NO_EXISTE:
                return new ResponseEntity<>("Token inválido / usuario no logeado", HttpStatus.UNAUTHORIZED);

            case MAL:
                return new ResponseEntity<>("Mal", HttpStatus.INTERNAL_SERVER_ERROR);

            default:
                return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

    // Get last 5 cases
    @Operation(summary = "Get the last 5 Case Investigations")
    @GetMapping("/{token}/last")
    public ResponseEntity<List<CaseInvestigationDTO>> getLast5(@PathVariable("token") String token){
        return ResponseEntity.ok(ciService.getCaseInvestigationsN(UUID.fromString(token),5));
    }

    // Get last N cases
    @Operation(summary = "Get the last N Case Investigations")
    @GetMapping("/{token}/last/{n}")
    public ResponseEntity<List<CaseInvestigationDTO>> getLastN(@PathVariable("token") String token, @PathVariable("n") int n){
        return ResponseEntity.ok(ciService.getCaseInvestigationsN(UUID.fromString(token), n));
    }

    // Get cases by date range
    @Operation(summary = "Get Case Investigations by date range")
    @GetMapping("/{token}/dates")
    public ResponseEntity<List<CaseInvestigationDTO>> getByDate(
        @PathVariable("token") String token,
        @RequestParam Date startDate,
        @RequestParam Date endDate){

        return ResponseEntity.ok(ciService.getCaseInvestigationsInDate(UUID.fromString(token), startDate, endDate));
    }

    // Show ci results 
    @Operation(summary = "Show case investigation Results")
    @GetMapping("/{token}/{id}/show")
    public ResponseEntity<Map<AnalysisType,Float>> showResults(
        @PathVariable("token") String token, // user identifier
        @PathVariable("id") Long id // ci id
        ){

        return ResponseEntity.ok(ciService.showCaseInvestigationResults(UUID.fromString(token), id));
    }

    // Delete Case by ID
    @Operation(summary = "Delete a Case Investigation by ID")
    @DeleteMapping("/{token}/{id}")
    public ResponseEntity<String> delete(@PathVariable("token") String token, @PathVariable("id") Long id){
        ciService.deleteCaseInvestigation(UUID.fromString(token), id);
        return ResponseEntity.ok("Deleted successfully");
    }

    // // Add files to case
    // @Operation(summary = "Add files to a case")
    // @PutMapping("/{token}/{id}/files")
    // public ResponseEntity<String> addFiles(
    //     @PathVariable("token") String token,
    //     @PathVariable("id") Long id,
    //     @RequestBody List<String> filesURL){
    //     ciService.addFilesToCase(UUID.fromString(token), filesURL, id);
    //     return ResponseEntity.ok("Files added successfully");
    // }
}