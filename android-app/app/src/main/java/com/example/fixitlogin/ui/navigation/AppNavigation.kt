package com.example.fixitlogin.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fixitlogin.ui.screens.HomeScreen
import com.example.fixitlogin.ui.screens.IncidentDetailScreen
import com.example.fixitlogin.ui.screens.LoginScreenUI
import com.example.fixitlogin.ui.screens.MisIncidenciasScreen
import com.example.fixitlogin.ui.screens.RegisterIncidentScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {

        // 1. Login
        composable("login") {
            LoginScreenUI(
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        // 2. Menú Principal (Home)
        composable("home") {
            HomeScreen(
                onNavigateHome = { navController.navigate("home") },
                onNavigateRegister = { navController.navigate("registro") },
                onNavigateMisIncidencias = { navController.navigate("mis_incidencias") },
                onLogout = {
                    navController.navigate("login") { popUpTo(0) { inclusive = true } }
                }
            )
        }

        // 3. Registro de Incidencia
        composable("registro") {
            RegisterIncidentScreen(
                onNavigateHome = { navController.navigate("home") },
                onNavigateRegister = { navController.navigate("registro") },
                onNavigateMisIncidencias = { navController.navigate("mis_incidencias") },
                onLogout = {
                    navController.navigate("login") { popUpTo(0) { inclusive = true } }
                },
                onBackToHome = { navController.popBackStack() }
            )
        }

        // 4. Mis Incidencias
        composable("mis_incidencias") {
            MisIncidenciasScreen(
                onNavigateHome = { navController.navigate("home") },
                onNavigateRegister = { navController.navigate("registro") },
                onNavigateMisIncidencias = { navController.navigate("mis_incidencias") },
                onLogout = {
                    navController.navigate("login") { popUpTo(0) { inclusive = true } }
                },
                onIncidentClick = {
                    navController.navigate("incident_detail") // <--- ¡Viajamos al detalle al hacer clic en la tarjeta!
                }
            )
        }

        // 5. Pantalla de Detalles de Incidencia
        composable("incident_detail") {
            IncidentDetailScreen(
                onNavigateHome = { navController.navigate("home") },
                onNavigateRegister = { navController.navigate("registro") },
                onNavigateMisIncidencias = { navController.navigate("mis_incidencias") },
                onLogout = {
                    navController.navigate("login") { popUpTo(0) { inclusive = true } }
                },
                onBack = { navController.popBackStack() } // Vuelve a Mis Incidencias
            )
        }
    }
}