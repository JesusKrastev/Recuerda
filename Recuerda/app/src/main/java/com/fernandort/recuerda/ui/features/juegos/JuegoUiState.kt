package com.fernandort.recuerda.ui.features.juegos

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.fernandort.recuerda.models.Juego

data class JuegoUiState(
    val id: String = "",
    val icono: ImageVector = Icons.Filled.Close,
    val nombre: String = "",
    val descripcion: String = "",
    val instrucciones: String = "",
    val fondo: List<Color> = emptyList(),
)

fun Juego.toJuegoUiState(): JuegoUiState =
    JuegoUiState(
        id = id,
        icono = icono,
        nombre = nombre,
        descripcion = descripcion,
        instrucciones = instrucciones,
        fondo = fondo,
    )