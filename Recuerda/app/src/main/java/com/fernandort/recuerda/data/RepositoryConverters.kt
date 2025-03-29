package com.fernandort.recuerda.data

import com.fernandort.recuerda.data.room.eventos.EventosEntity
import com.fernandort.recuerda.models.Eventos
import java.time.LocalDateTime

fun EventosEntity.toEventos(): Eventos =
    Eventos(
        id = id,
        titulo = titulo,
        descripcion = descripcion,
        fecha = fecha
    )

fun Eventos.toEventosEntity(): EventosEntity =
    EventosEntity(
        id = id,
        titulo = titulo,
        descripcion = descripcion,
        fecha = fecha
    )