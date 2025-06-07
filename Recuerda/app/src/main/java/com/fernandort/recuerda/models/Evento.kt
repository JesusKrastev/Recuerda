package com.fernandort.recuerda.models

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class Evento(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val fecha: LocalDate,
    val hora: LocalTime,
    val completado: Boolean,
)