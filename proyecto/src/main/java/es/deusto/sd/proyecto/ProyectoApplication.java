package es.deusto.sd.proyecto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProyectoApplication {

	// public static void main(String[] args) {
	// 	SpringApplication.run(ProyectoApplication.class, args);
	// }

	public static void main(String[] args) {
        // 1. Arranca Spring en modo "ventana" (no headless)
        var context = new org.springframework.boot.builder.SpringApplicationBuilder(ProyectoApplication.class)
                .headless(false)
                .run(args);

        // 2. Llama a tu interfaz
        java.awt.EventQueue.invokeLater(() -> {
            SwingApp gui = context.getBean(SwingApp.class);
            gui.createAndShowGui();
        });
    }

}
