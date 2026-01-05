package es.deusto.sd.proyecto.Service;

import es.deusto.sd.proyecto.Entity.AnalysisType;
import es.deusto.sd.proyecto.Entity.CaseInvestigation;
import java.io.*;
import java.util.*;
import java.net.*;
import org.springframework.stereotype.Component;

@Component
public class procesamientoDatosServidor {
    private int puerto = 5000;
    private dataProcessingService logicaProcesamiento = new dataProcessingService();

    public void iniciarServidor() {
        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("Servidor de Procesamiento listo en puerto " + puerto);
            
            while (true) {
                try (Socket cliente = serverSocket.accept();
                     ObjectInputStream in = new ObjectInputStream(cliente.getInputStream());
                     ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream())) {
                    
                    // 1. Recibir el caso desde CaseInvestigationService
                    CaseInvestigation ciEntrada = (CaseInvestigation) in.readObject();
                    
                    // 2. Procesarlo usando la lógica que ya tenías
                    Map<AnalysisType,List<Float>> ciProcesado = logicaProcesamiento.showCaseInvestigationResults(ciEntrada);
                    
                    // 3. Devolver el resultado
                    out.writeObject(ciProcesado);
                    out.flush();
                    
                } catch (Exception e) {
                    System.err.println("Error procesando petición: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}