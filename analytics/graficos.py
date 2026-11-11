import io
import json
import matplotlib.pyplot as plt
import analisis


def generar_grafico(incidencias_usables: list) -> bytes:
    """
    Procesa los datos y genera el gráfico de estados y prioridades.
    Devuelve el PNG como bytes, creado en un búfer de memoria.
    """
    """
    Procesar los datos pasándole la lista cargada a la función
    """

    estados = analisis.estado_incidencias(incidencias_usables)
    nombres_estados = list(estados.keys())
    cantidad_estados = list(estados.values())

    prioridades = analisis.prioridad_incidencias(incidencias_usables)
    nombres_prioridades = list(prioridades.keys())
    cantidad_prioridades = list(prioridades.values())

    """
    Generar la gráfica (usamos plt.bar para datos categóricos)
    """

    fig, (ax1, ax2) = plt.subplots(1, 2, figsize=(12, 5))

    fig.suptitle('Análisis de Incidencias', fontsize=16, fontweight='bold')

    # Gráfico de barras para estados de incidencias
    ax1.bar(nombres_estados, cantidad_estados, width=0.6,
            color="#0D9488", edgecolor='black', linewidth=1.5)
    ax1.set_title("Distribución de Incidencias por Estado")
    ax1.set_xlabel("Estado")
    ax1.set_ylabel("Cantidad de Incidencias")

    # Gráfico circular para la prioridad de las incidencias
    colores_prioridad = ['#FEE2E2', '#FFEDD5', '#FEF9C3', '#D1FAE5']
    ax2.pie(
        cantidad_prioridades,
        labels=nombres_prioridades,
        autopct='%1.1f%%',
        startangle=140,
        colors=colores_prioridad[:len(nombres_prioridades)]
    )
    ax2.set_title("Distribución por Prioridad")

    fig.tight_layout()

    """
    Guardar en búfer de memoria
    """

    bufer_memoria = io.BytesIO()
    plt.savefig(bufer_memoria, format="png")
    plt.close()

    bufer_memoria.seek(0)
    return bufer_memoria.read()


def main() -> None:
    """Carga los datos, genera el gráfico y lo exporta a disco."""
    """
    Cargar primero los datos del archivo JSON
    """
    with open('data/incidencias.json', 'r', encoding='utf-8') as listado:
        incidencias_usables = json.load(listado)

    print("\nSe han detectado un número total de incidencias:",
          len(incidencias_usables))

    imagen_png = generar_grafico(incidencias_usables)

    """
    Exportar a disco
    """
    with open("grafico_incidencias.png", "wb") as archivo_imagen:
        archivo_imagen.write(imagen_png)

    print("Han quedado registradas correctamente como"
          + "'grafico_incidencias.png' "
          + "desde el búfer de memoria.")


if __name__ == '__main__':
    main()
