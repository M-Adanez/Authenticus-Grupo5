package es.deusto.sd.proyecto.Controller;

import es.deusto.sd.proyecto.DTO.CaseInvestigationDTO;
import es.deusto.sd.proyecto.Service.gestionBBDD;
import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/api/database")
// public class dataBaseController {

//     private final gestionBBDD databaseService;

//     public dataBaseController(gestionBBDD databaseService) {
//         this.databaseService = databaseService;
//     }

//     @PostMapping("/save-case")
//     public void receiveAndSaveCase(@RequestBody CaseInvestigationDTO ciDTO) {
//         // Llama al método que ya tenías en tu clase gestionBBDD
//         databaseService.createCaseInvestigation(ciDTO);
//     }
//}