import json


def estado_incidencias(incidencias: list) -> dict:
    """
    Función que devuelve un diccionario con
    el número de incidencias por estado.
    """
    estados = {}
    for incidencia in incidencias:
        estado = incidencia['estado']
        if estado in estados:
            estados[estado] += 1
        else:
            estados[estado] = 1
    return estados


def prioridad_incidencias(incidencias: list) -> dict:
    """
    Función que devuelve un diccionario con
    el número de incidencias por prioridad.
    """
    prioridades = {}
    for incidencia in incidencias:
        prioridad = incidencia['prioridad']
        if prioridad in prioridades:
            prioridades[prioridad] += 1
        else:
            prioridades[prioridad] = 1
    return prioridades


def main() -> None:
    """Carga las incidencias y muestra el resumen por consola."""
    # Cargamos el archivo JSON con las incidencias,
    # Utilizamos "r" para leer, "w" para escribir y "a" para añadir.
    with open('data/incidencias.json', 'r', encoding='utf-8') as listado:
        incidencias_usables = json.load(listado)

    print("\n1. Número de incidencias: ", len(incidencias_usables))

    estados_incidencias = estado_incidencias(incidencias_usables)
    print("\n2. Incidencias por estado:")
    for estado, cantidad in estados_incidencias.items():
        print(f"  {estado}: {cantidad}")

    prioridades_incidencias = prioridad_incidencias(incidencias_usables)
    print("\n3. Incidencias por prioridad:")
    for prioridad, cantidad in prioridades_incidencias.items():
        print(f"  {prioridad}: {cantidad}")


if __name__ == '__main__':
    main()
