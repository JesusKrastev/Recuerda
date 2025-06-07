package com.fernandort.recuerda.data.room.eventos

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Entity(tableName = "eventos")
data class EventoEntity(
    @PrimaryKey val id: String,
    val titulo: String,
    val descripcion: String,
    val fecha: LocalDate,
    val hora: LocalTime,
    val completado: Boolean,
)