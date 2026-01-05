package es.deusto.sd.proyecto.Config;

import es.deusto.sd.proyecto.Service.procesamientoDatosServidor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketServerConfig {

    @Bean
    public CommandLineRunner startSocketServer(procesamientoDatosServidor servidor) {
        return args -> {
            // Ejecutamos el servidor en un hilo nuevo para no bloquear Spring
            Thread serverThread = new Thread(servidor::iniciarServidor);
            serverThread.setDaemon(true); // Se cierra si la app principal se cierra
            serverThread.start();
        };
    }
}