package com.example.fixitlogin.ui.screens

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fixitlogin.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterIncidentScreen(
    onNavigateHome: () -> Unit,
    onNavigateRegister: () -> Unit,
    onNavigateMisIncidencias: () -> Unit,
    onLogout: () -> Unit,
    onBackToHome: () -> Unit
) {
    var tipoIncidencia by remember { mutableStateOf("") }
    var espacio by remember { mutableStateOf("") }
    var riesgoPersonas by remember { mutableStateOf("") }
    var impideUso by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    var capturedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var showImageDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val colorFixIt = Color(0xFF009688)
    val scrollState = rememberScrollState()

    val listaTipos = listOf(
        "Accesibilidad y Movilidad", "Albañilería", "Carpintería", "Cerrajería",
        "Climatización", "Electricidad", "Fontanería y Sanitarios", "Gestión de Residuos",
        "Hardware", "Iluminación", "Redes y Conectividad", "Sistemas Audiovisuales",
        "Sistemas de Emergencia", "Zonas Verdes y Exteriores"
    )

    val listaEspacios = listOf(
        "Edificio A", "Edificio B", "Edificio Central", "Lab-Redes",
        "Taller-Electrónica", "Aula B-12", "Pasillo Principal", "Aula Magna"
    )

    val listaSiNo = listOf("Sí", "No")

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            capturedBitmap = resizeBitmap(bitmap, 800, 800)
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val originalBitmap = if (Build.VERSION.SDK_INT < 28) {
                @Suppress("DEPRECATION")
                MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                ImageDecoder.decodeBitmap(source)
            }
            capturedBitmap = resizeBitmap(originalBitmap, 800, 800)
        }
    }

    if (showImageDialog) {
        AlertDialog(
            onDismissRequest = { showImageDialog = false },
            title = { Text(text = "Adjuntar evidencia") },
            text = { Text(text = "¿De dónde deseas obtener la foto para la incidencia?") },
            confirmButton = {
                TextButton(onClick = {
                    showImageDialog = false
                    cameraLauncher.launch(null)
                }) {
                    Text(text = "Cámara", color = colorFixIt)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showImageDialog = false
                    galleryLauncher.launch("image/*")
                }) {
                    Text(text = "Galería", color = colorFixIt)
                }
            }
        )
    }

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
            Text(
                text = "Registro incidencia",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(24.dp))

            AppDropdownField(
                label = "Selecciona tipo incidencia...",
                options = listaTipos,
                selectedOption = tipoIncidencia,
                onOptionSelected = { tipoIncidencia = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            AppDropdownField(
                label = "Selecciona espacio...",
                options = listaEspacios,
                selectedOption = espacio,
                onOptionSelected = { espacio = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            AppDropdownField(
                label = "¿Hay riesgo para personas?",
                options = listaSiNo,
                selectedOption = riesgoPersonas,
                onOptionSelected = { riesgoPersonas = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            AppDropdownField(
                label = "¿Impide usar el espacio?",
                options = listaSiNo,
                selectedOption = impideUso,
                onOptionSelected = { impideUso = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                placeholder = { Text("Describa la incidencia...", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp),
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFE4F2F0),
                border = BorderStroke(2.dp, colorFixIt),
                onClick = { showImageDialog = true }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (capturedBitmap != null) {
                        Image(
                            bitmap = capturedBitmap!!.asImageBitmap(),
                            contentDescription = "Foto adjunta",
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_chat),
                                contentDescription = "Subir foto",
                                tint = colorFixIt,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "📷 Toca aquí para subir o tomar foto",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = colorFixIt
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { /* Enviar */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorFixIt),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Enviar", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = { onBackToHome() },
                modifier = Modifier.height(45.dp),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, colorFixIt),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = colorFixIt)
            ) {
                Text(text = "Volver a inicio", fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDropdownField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedOption.ifEmpty { label },
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = Color(0xFF009688),
                unfocusedBorderColor = Color(0xFFD0D0D0),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, enabled = true)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option, color = Color.Black) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

// --- FUNCIÓN DE REDIMENSIONAMIENTO (La que faltaba) ---
fun resizeBitmap(image: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
    var width = image.width
    var height = image.height
    val bitmapRatio = width.toFloat() / height.toFloat()
    if (bitmapRatio > 1) {
        width = maxWidth
        height = (width / bitmapRatio).toInt()
    } else {
        height = maxHeight
        width = (height * bitmapRatio).toInt()
    }
    return Bitmap.createScaledBitmap(image, width, height, true)
}