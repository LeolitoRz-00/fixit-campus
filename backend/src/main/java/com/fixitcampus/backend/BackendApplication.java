package com.fixitcampus.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.fixitcampus.backend.logger.ColaLogs;
import com.fixitcampus.backend.logger.LogWriter;
import com.fixitcampus.backend.model.LogMessage;
import com.fixitcampus.backend.model.TipoEvento;

import java.io.IOException;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		EstructuraCarpetas.crearEstructura();
		//CrearExcel.crearExcel();
		LeerExcel.leerExcel();
		//SpringApplication.run(BackendApplication.class, args);

        //
        ColaLogs cola = new ColaLogs();
        LogWriter escritor = new LogWriter("Escritor",cola, "logs.txt");
        LogWriter chismosito = new LogWriter("Chismosito",cola, "logs.txt");

        System.out.println("Estado antes de usar start(): " + escritor.getState());

        escritor.start();
        chismosito.start();
        System.out.println("Estado luego de usar start():" + escritor.getState());
        Thread.sleep(500); // ESPERAMOS ANTES DE QUE ARRANQUE EL HILO
        System.out.println("Estado con la cola vacía: " + escritor.getState());

        cola.agregar(new LogMessage(TipoEvento.INCIDENCIA, "Proyector roto en aula 3"));
        cola.agregar(new LogMessage(TipoEvento.FACTURA, "Factura de fontanería registrada"));
        System.out.println("Estado de otro escritor que intenta entrar: " + chismosito.getState());
        Thread.sleep(300);
        System.out.println("Estado mientras descansa: " + escritor.getState());

        // ESPERAR QUE NO EXISTAN MÁS MENSAJES
        while (cola.pendientes() > 0) {
            Thread.sleep(100); // preguntamos cada 100 ms
        }

        Thread.sleep(1000); // EL MAIN ESPERA A QUE EL LogWriter TERMINE DE ESCRIBIR POR SI LO NECESITA

        chismosito.interrupt();
        escritor.interrupt();
        escritor.join(); // ACA EL MAIN ESPERA AL LogWriter ANTES DE CONTINUAR CON EL PROGRAMA
        System.out.println("Estado al final: " + escritor.getState());
	}

}
