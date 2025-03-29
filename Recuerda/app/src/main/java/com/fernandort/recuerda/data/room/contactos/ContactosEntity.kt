package com.fernandort.recuerda.data.room.contactos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contactos")
data class ContactosEntity(
    @PrimaryKey val id: String,
    val nombre: String,
    val telefono: String
)