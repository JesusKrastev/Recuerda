package com.fernandort.recuerda.data

import com.fernandort.recuerda.data.room.contactos.ContactoEntity
import com.fernandort.recuerda.data.room.eventos.EventoEntity
import com.fernandort.recuerda.data.room.notas.NotaEntity
import com.fernandort.recuerda.models.Contacto
import com.fernandort.recuerda.models.Evento
import com.fernandort.recuerda.models.Nota

fun EventoEntity.toEvento(): Evento =
    Evento(
        id = id,
        titulo = titulo,
        descripcion = descripcion,
        fecha = fecha
    )

fun Evento.toEventoEntity(): EventoEntity =
    EventoEntity(
        id = id,
        titulo = titulo,
        descripcion = descripcion,
        fecha = fecha
    )

fun Contacto.toContactoEntity(): ContactoEntity =
    ContactoEntity(
        id = id,
        nombre = nombre,
        apellidos = apellidos,
        telefono = telefono
    )

fun ContactoEntity.toContacto(): Contacto =
    Contacto(
        id = id,
        nombre = nombre,
        apellidos = apellidos,
        telefono = telefono
    )

fun Nota.toNotaEntity(): NotaEntity =
    NotaEntity(
        id = id,
        titulo = titulo,
        descripcion = descripcion,
        fecha = fecha
    )

fun NotaEntity.toNota(): Nota =
    Nota(
        id = id,
        titulo = titulo,
        descripcion = descripcion,
        fecha = fecha
    )