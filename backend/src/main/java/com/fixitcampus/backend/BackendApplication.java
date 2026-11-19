package com.fixitcampus.backend;

import com.fixitcampus.backend.conexion.ConexionBBDD;
import com.fixitcampus.backend.conexion.ImportadorBBDD;
import com.fixitcampus.backend.demo.DemoHilos;
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
		CrearExcel.crearExcel();
        DemoHilos.ejecutar();
	}
}
