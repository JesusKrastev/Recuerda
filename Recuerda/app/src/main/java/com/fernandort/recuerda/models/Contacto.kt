package com.fernandort.recuerda.models

import androidx.room.PrimaryKey

data class Contacto(
    val id: String,
    val nombre: String,
    val telefono: String
)
