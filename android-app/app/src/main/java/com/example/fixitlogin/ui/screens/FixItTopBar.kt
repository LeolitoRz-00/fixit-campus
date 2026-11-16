package com.example.fixitlogin.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
fun FixItTopBar(
    onNavigateHome: () -> Unit,
    onNavigateRegister: () -> Unit,
    onNavigateMisIncidencias: () -> Unit,
    onLogout: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val colorFixIt = Color(0xFF009688)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        androidx.compose.foundation.Image(
            painter = painterResource(id = R.drawable.logo_fixit),
            contentDescription = "Logo",
            modifier = Modifier.height(30.dp)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_campana),
                contentDescription = "Notificaciones",
                tint = colorFixIt,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))

            Box {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color.Gray, shape = CircleShape)
                        .clickable { expanded = true }
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(Color.White)
                ) {
                    DropdownMenuItem(
                        text = { Text("Inicio", color = colorFixIt, fontWeight = FontWeight.Bold) },
                        onClick = { expanded = false; onNavigateHome() }
                    )
                    DropdownMenuItem(
                        text = { Text("Registrar Incidencia", color = colorFixIt, fontWeight = FontWeight.Bold) },
                        onClick = { expanded = false; onNavigateRegister() }
                    )
                    DropdownMenuItem(
                        text = { Text("Mis Incidencias", color = colorFixIt, fontWeight = FontWeight.Bold) },
                        onClick = { expanded = false; onNavigateMisIncidencias() }
                    )
                    DropdownMenuItem(
                        text = { Text("Cerrar Sesión", color = colorFixIt, fontWeight = FontWeight.Bold) },
                        onClick = { expanded = false; onLogout() }
                    )
                }
            }
        }
    }
}