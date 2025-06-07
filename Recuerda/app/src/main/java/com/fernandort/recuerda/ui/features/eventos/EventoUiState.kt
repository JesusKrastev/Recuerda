package com.fernandort.recuerda.ui.features.eventos

import com.fernandort.recuerda.models.Evento
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.UUID

data class EventoUiState(
    val id: String = UUID.randomUUID().toString(),
    val titulo: String = "",
    val descripcion: String = "",
    val fecha: LocalDate = LocalDate.now().plusDays(1),
    val hora: LocalTime = LocalTime.now(),
    val completado: Boolean = false,
)

fun Evento.toEventoUiState(): EventoUiState =
    EventoUiState(
        id = id,
        titulo = titulo,
        descripcion = descripcion,
        fecha = fecha,
        hora = hora,
        completado = completado,
    )

fun EventoUiState.toEvento(): Evento =
    Evento(
        id = id,
        titulo = titulo,
        descripcion = descripcion,
        fecha = fecha,
        hora = hora,
        completado = completado,
    )