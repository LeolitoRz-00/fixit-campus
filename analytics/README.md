# Análisis de incidencias

Scripts de consola que analizan un listado de incidencias en formato JSON:
cuentan cuántas hay por estado y por prioridad, y generan un gráfico en PNG.

## Estructura

    analytics/
    ├── data/
    │   └── incidencias.json    # datos de ejemplo (20+ incidencias)
    ├── entorno/                # entorno virtual (no se sube al repositorio)
    ├── analisis.py             # funciones de análisis y resumen por consola
    ├── grafico.py              # genera el gráfico en memoria y lo guarda
    ├── requirements.txt        # dependencias con versiones fijadas
    └── README.md

## Requisitos

- Python 3.9 o superior
- pip

## Puesta en marcha

Desde la carpeta `analytics/`:

**1. Crear el entorno virtual**

    python -m venv entorno

**2. Activarlo**

    # Windows (cmd)
    entorno\Scripts\activate

**3. Instalar las dependencias**

    pip install -r requirements.txt

## Uso

Los scripts deben lanzarse desde la carpeta `analytics/`, porque leen el
JSON con la ruta relativa `data/incidencias.json`.

**Resumen por consola** (total, incidencias por estado y por prioridad):

    python analisis.py

**Gráfico** (barras por estado y gráfico circular por prioridad):

    python grafico.py

El gráfico se genera en un búfer de memoria mediante la función
`generar_grafico()` y se guarda en `grafico_incidencias.png`.

## Comprobación de estilo (PEP 8)

    pip install flake8
    flake8 analisis.py grafico.py

Si no muestra ningún aviso, el código cumple PEP 8.
