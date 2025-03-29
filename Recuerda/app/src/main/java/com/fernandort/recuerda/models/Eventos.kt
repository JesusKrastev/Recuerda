package com.fernandort.recuerda.models

import androidx.room.PrimaryKey
import java.time.LocalDateTime

data class Eventos(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val fecha: LocalDateTime
)