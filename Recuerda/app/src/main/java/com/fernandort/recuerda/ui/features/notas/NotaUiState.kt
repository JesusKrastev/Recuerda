package com.fernandort.recuerda.ui.features.notas

import com.fernandort.recuerda.models.Nota
import java.time.LocalDateTime
import java.util.UUID

data class NotaUiState(
    val id: String = UUID.randomUUID().toString(),
    val titulo: String = "",
    val descripcion: String = "",
    val fecha: LocalDateTime = LocalDateTime.now()
)

fun Nota.toNotaUiState(): NotaUiState =
    NotaUiState(
        id = id,
        titulo = titulo,
        descripcion = descripcion,
        fecha = fecha
    )

fun NotaUiState.toNota(): Nota =
    Nota(
        id = id,
        titulo = titulo,
        descripcion = descripcion,
        fecha = LocalDateTime.now()
    )