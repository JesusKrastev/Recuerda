package com.fernandort.recuerda.data.room.contactos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contactos")
data class ContactoEntity(
    @PrimaryKey val id: String,
    val nombre: String,
    val apellidos: String,
    val telefono: String
)