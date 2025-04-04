package com.fernandort.recuerda.models

import java.time.LocalDateTime

data class Notas(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val fecha: LocalDateTime
)
