package com.fixitcampus.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) throws IOException {
		EstructuraCarpetas.crearEstructura();
		//CrearExcel.crearExcel();
		LeerExcel.leerExcel();
		//SpringApplication.run(BackendApplication.class, args);
		
	}

}
