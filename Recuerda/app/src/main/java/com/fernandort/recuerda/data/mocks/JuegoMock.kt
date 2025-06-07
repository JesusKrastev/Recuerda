package com.fernandort.recuerda.data.mocks

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class JuegoMock(
    val id: String,
    val icono: ImageVector,
    val nombre: String,
    val descripcion: String,
    val instrucciones: String,
    val fondo: List<Color>,
)