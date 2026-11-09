import java.io.File;
import java.util.List;

public class EstructuraCarpetas {

    public static void crearEstructura(){
        //1. Preparación del entorno de trabajo
        //Lista de todas las carpetas necesarias

        List<String> rutasCarpetas = List.of(
                "fixit-campus/backend/src",
                "fixit-campus/backend/tests",
                "fixit-campus/analytics/app",
                "fixit-campus/analytics/tests",
                "fixit-campus/web",
                "fixit-campus/android-app",
                "fixit-campus/facturacion",
                "fixit-campus/database",
                "fixit-campus/data",
                "fixit-campus/design",
                "fixit-campus/docs/api",
                "fixit-campus/docs/english",
                "fixit-campus/docs/digitalizacion",
                "fixit-campus/docs/ipe",
                "fixit-campus/docs/sostenibilidad"
        );

        //Se crean las carpetas de todas las rutas que se han definido en la lista de arriba

        rutasCarpetas.forEach(ruta -> new File(ruta).mkdirs());
    }
}
