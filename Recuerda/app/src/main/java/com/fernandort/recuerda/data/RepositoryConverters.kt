package com.fernandort.recuerda.data

import com.fernandort.recuerda.data.room.contactos.ContactosEntity
import com.fernandort.recuerda.data.room.eventos.EventosEntity
import com.fernandort.recuerda.data.room.notas.NotasEntity
import com.fernandort.recuerda.models.Contacto
import com.fernandort.recuerda.models.Eventos
import com.fernandort.recuerda.models.Notas
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

fun Contacto.toContactoEntity(): ContactosEntity =
    ContactosEntity(
        id = id,
        nombre = nombre,
        telefono = telefono
    )

fun ContactosEntity.toContacto(): Contacto =
    Contacto(
        id = id,
        nombre = nombre,
        telefono = telefono
    )

fun Notas.toNotasEntity(): NotasEntity =
    NotasEntity(
        id = id,
        titulo = titulo,
        descripcion = descripcion,
        fecha = fecha
    )

fun NotasEntity.toNotas(): Notas =
    Notas(
        id = id,
        titulo = titulo,
        descripcion = descripcion,
        fecha = fecha
    )