package com.example.fixitlogin.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fixitlogin.R

@Composable
fun HomeScreen(
    onNavigateHome: () -> Unit,
    onNavigateRegister: () -> Unit,
    onNavigateMisIncidencias: () -> Unit,
    onLogout: () -> Unit
) {
    val colorFondoBotones = Color(0xFFE8F6F3)
    val colorBordeBotones = Color(0xFF009688)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        // Barra superior compartida
        FixItTopBar(
            onNavigateHome = onNavigateHome,
            onNavigateRegister = onNavigateRegister,
            onNavigateMisIncidencias = onNavigateMisIncidencias,
            onLogout = onLogout
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Bienvenido, Usuario",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "¿En qué podemos ayudarte hoy?",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            BotonMenuGigante(
                titulo = "Registrar incidencia",
                iconoId = R.drawable.ic_alerta,
                colorFondo = colorFondoBotones,
                colorBorde = colorBordeBotones
            ) {
                onNavigateRegister()
            }

            BotonMenuGigante(
                titulo = "Mis Incidencias",
                iconoId = R.drawable.ic_archivo,
                colorFondo = colorFondoBotones,
                colorBorde = colorBordeBotones
            ) {
                onNavigateMisIncidencias()
            }

            BotonMenuGigante(
                titulo = "Soporte",
                iconoId = R.drawable.ic_chat,
                colorFondo = colorFondoBotones,
                colorBorde = colorBordeBotones
            ) {
                // Soporte
            }
        }
    }
}

@Composable
fun BotonMenuGigante(
    titulo: String,
    iconoId: Int,
    colorFondo: Color,
    colorBorde: Color,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp),
        shape = RoundedCornerShape(16.dp),
        color = colorFondo,
        border = BorderStroke(1.dp, colorBorde),
        shadowElevation = 2.dp,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = titulo,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(12.dp))
            Icon(
                painter = painterResource(id = iconoId),
                contentDescription = titulo,
                modifier = Modifier.size(40.dp),
                tint = Color.Black
            )
        }
    }
}