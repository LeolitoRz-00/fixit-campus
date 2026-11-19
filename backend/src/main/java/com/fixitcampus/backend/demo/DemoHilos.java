package com.fixitcampus.backend.demo;

import com.fixitcampus.backend.util.GeneradorInformeWord;
import com.fixitcampus.backend.service.LeerExcel;
import com.fixitcampus.backend.logger.ColaLogs;
import com.fixitcampus.backend.logger.LogWriter;
import com.fixitcampus.backend.model.LogMessage;
import com.fixitcampus.backend.model.TipoEvento;
import com.fixitcampus.backend.service.AnalizadorLogs;
import com.fixitcampus.backend.threads.AvisoSistema;
import com.fixitcampus.backend.threads.SimuladorErroresOdoo;
import com.fixitcampus.backend.threads.SimuladorIncidencias;
import com.fixitcampus.backend.util.GeneradorRutas;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DemoHilos {

    // "INTERRUPTOR": false = Odoo dañado (3 ERROR), true = Odoo funcionando (3 INCIDENCIA)
    private static final boolean ODOO_FUNCIONA = false;

    // Evita el aviso de IntelliJ: esta clase solo tiene métodos estáticos
    private DemoHilos() {
    }

    // Muestra el estado de los dos escritores en un momento concreto
    private static void mostrarEstados(String momento, Thread escritor, Thread chismosito) {
        System.out.println("[" + momento + "] "
                + escritor.getName() + ": " + escritor.getState() + " | "
                + chismosito.getName() + ": " + chismosito.getState());
    }

    // Arranca un hilo productor, dice cuál es y qué hace, y espera a que termine (sin mostrar estados)
    private static void lanzar(Thread hilo, String tecnica, String queHace) throws InterruptedException {
        System.out.println(">> Hilo '" + hilo.getName() + "' (" + tecnica + "): " + queHace);
        hilo.start();
        hilo.join(); // el main espera a que termine de generar mensajes
        System.out.println("<< Hilo '" + hilo.getName() + "' ha terminado");
    }

    public static void ejecutar() throws InterruptedException, IOException {

        // Cada ejecución tiene su propia carpeta de logs, con la fecha y hora
        String carpetaLogs = GeneradorRutas.nuevaCarpetaLogs();
        System.out.println("Logs de esta ejecución en: " + carpetaLogs);

        ColaLogs cola = new ColaLogs();
        LogWriter escritor = new LogWriter("Escritor", cola, carpetaLogs);
        LogWriter chismosito = new LogWriter("Chismosito", cola, carpetaLogs);

        // ===== LOS 6 ESTADOS (solo Escritor y Chismosito) =====

        mostrarEstados("1. Antes de start()", escritor, chismosito);          // NEW

        escritor.start();
        chismosito.start();

        // Desde aquí hay hilos vivos: pase lo que pase, hay que detenerlos en el finally
        try {
            mostrarEstados("2. Justo tras start()", escritor, chismosito);    // RUNNABLE

            Thread.sleep(500); // dejamos que lleguen a sacar() y se queden esperando
            mostrarEstados("3. Cola vacía", escritor, chismosito);            // WAITING

            // El main coge la llave de la cola y NO la suelta durante 300 ms
            synchronized (cola) {
                cola.agregar(new LogMessage(TipoEvento.INCIDENCIA, "Proyector roto en aula 3"));
                cola.agregar(new LogMessage(TipoEvento.FACTURA, "Factura de fontanería registrada"));
                Thread.sleep(300);
                mostrarEstados("4. El main retiene la llave", escritor, chismosito); // BLOCKED
            }

            Thread.sleep(300); // ya han escrito y están en el sleep(1000)
            mostrarEstados("5. Descansando tras escribir", escritor, chismosito); // TIMED_WAITING

            // ===== HILOS PRODUCTORES =====

            lanzar(new Thread(new SimuladorIncidencias(cola), "Incidencias"),
                    "implements Runnable",
                    "genera 5 averías inventadas, una cada 300 ms");

            lanzar(new SimuladorErroresOdoo(cola, ODOO_FUNCIONA).crearHilo(),
                    "lambda",
                    ODOO_FUNCIONA
                            ? "Odoo funciona: deja 3 mensajes INCIDENCIA de sincronización correcta"
                            : "Odoo dañado: deja 3 mensajes ERROR de sincronización fallida");

            lanzar(new AvisoSistema(cola).crearHilo(),
                    "clase anónima",
                    "deja 2 avisos periódicos del sistema");

            // ===== ERRORES DE LA BASE DE DATOS (Excel) =====
            // Usamos la MISMA cola: los LogWriter de siempre los escriben en error.log
            LeerExcel.leerExcel(cola);

            // ===== ATOMICINTEGER =====

            // Esperamos a que se hayan ESCRITO todos los mensajes que entraron en la cola
            while (LogWriter.getEscritos() < cola.getTotalAgregados()) {
                Thread.sleep(100);
            }
            System.out.println("Mensajes escritos (AtomicInteger): " + LogWriter.getEscritos());

            // ===== EXECUTORSERVICE + FUTURE =====

            ExecutorService pool = Executors.newSingleThreadExecutor();
            try {
                Future<Map<String, Integer>> resultado = pool.submit(new AnalizadorLogs(carpetaLogs));
                Map<String, Integer> conteo = resultado.get(); // BLOQUEA hasta tener el resultado
                System.out.println("Eventos por tipo (Future): " + conteo);
            } catch (ExecutionException e) {
                System.out.println("El análisis falló: " + e.getCause().getMessage());
            } finally {
                pool.shutdown(); // sin esto el programa no termina
            }

            // Informe Word: los logs ya están escritos y analizados
            GeneradorInformeWord.generarInforme();

        } finally {
            // CIERRE: se ejecuta aunque algo haya fallado
            chismosito.interrupt();
            escritor.interrupt();
            escritor.join();
            chismosito.join();
            mostrarEstados("6. Al final", escritor, chismosito);              // TERMINATED
        }
    }
}