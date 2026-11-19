package com.fixitcampus.backend;


import com.fixitcampus.backend.demo.DemoHilos;
import com.fixitcampus.backend.util.CrearExcel;
import com.fixitcampus.backend.util.EstructuraCarpetas;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		EstructuraCarpetas.crearEstructura();
		CrearExcel.crearExcel();
        DemoHilos.ejecutar();
	}
}
