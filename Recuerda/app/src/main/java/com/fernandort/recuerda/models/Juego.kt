package com.fernandort.recuerda.models

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class Juego(
    val id: String,
    val icono: ImageVector,
    val nombre: String,
    val descripcion: String,
    val instrucciones: String,
    val fondo: List<Color>,
)