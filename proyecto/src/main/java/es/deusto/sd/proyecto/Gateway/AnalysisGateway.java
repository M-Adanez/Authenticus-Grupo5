package es.deusto.sd.proyecto.Gateway;

import es.deusto.sd.proyecto.Entity.CaseInvestigation;
import org.springframework.stereotype.Component;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

@Component // Lo marcamos como componente para inyectarlo en el Service
public class AnalysisGateway {

    private final String HOST = "localhost";
    private final int PORT = 5000;

    public CaseInvestigation sendToRemoteProcessing(CaseInvestigation ci) {
        try (Socket socket = new Socket(HOST, PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject(ci);
            out.flush();

            return (CaseInvestigation) in.readObject();

        } catch (Exception e) {
            // Aquí podrías lanzar una excepción personalizada de negocio
            System.err.println("Error en la conexión remota: " + e.getMessage());
            return null;
        }
    }
}