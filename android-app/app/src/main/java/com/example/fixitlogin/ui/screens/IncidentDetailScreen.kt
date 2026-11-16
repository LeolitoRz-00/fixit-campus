package com.example.fixitlogin.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IncidentDetailScreen(
    onNavigateHome: () -> Unit,
    onNavigateRegister: () -> Unit,
    onNavigateMisIncidencias: () -> Unit,
    onLogout: () -> Unit,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    val colorFixIt = Color(0xFF009688)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        // Barra superior compartida con el menú desplegable del avatar
        FixItTopBar(
            onNavigateHome = onNavigateHome,
            onNavigateRegister = onNavigateRegister,
            onNavigateMisIncidencias = onNavigateMisIncidencias,
            onLogout = onLogout
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título principal
            Text(
                text = "Detalles Incidencia",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Estado y Asignación (Sin asignar + ABIERTO + Urgente)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "👤 Sin asignar", fontSize = 14.sp, color = Color.DarkGray)
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFE0E0E0)
                    ) {
                        Text(
                            text = "ABIERTO",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFFFD1D1)
                    ) {
                        Text(
                            text = "Urgente",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFD32F2F),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Contenedor de la foto subida (Simulado tal cual tu imagen)
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFEAEAEA),
                border = BorderStroke(1.dp, Color.Gray)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "📷", fontSize = 40.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "|Aquí aparece la foto subida de la incidencia|",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Bloque de información y descripción de la incidencia
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Fuga de agua en el techo",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Lab-Redes(Edificio A)",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "FIX-0005",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Hay una gotera importante cayendo del techo en el Laboratorio de Redes, justo encima de los equipos. Necesita revisión urgente antes de que cause daños eléctricos.",
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    lineHeight = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Botón Volver
            OutlinedButton(
                onClick = { onBack() },
                modifier = Modifier
                    .width(150.dp)
                    .height(45.dp),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, colorFixIt),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = colorFixIt)
            ) {
                Text(text = "Volver", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}