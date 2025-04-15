package com.fernandort.recuerda.models

import java.time.LocalDateTime

data class Nota(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val fecha: LocalDateTime
)
