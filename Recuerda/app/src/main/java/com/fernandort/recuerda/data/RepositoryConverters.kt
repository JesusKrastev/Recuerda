package com.fernandort.recuerda.data

import com.fernandort.recuerda.data.mocks.JuegoMock
import com.fernandort.recuerda.data.room.contactos.ContactoEntity
import com.fernandort.recuerda.data.room.eventos.EventoEntity
import com.fernandort.recuerda.data.room.notas.NotaEntity
import com.fernandort.recuerda.models.Contacto
import com.fernandort.recuerda.models.Evento
import com.fernandort.recuerda.models.Juego
import com.fernandort.recuerda.models.Nota

fun JuegoMock.toJuego(): Juego =
    Juego(
        icono = icono,
        nombre = nombre,
        descripcion = descripcion,
        instrucciones = instrucciones,
        fondo = fondo,
        id = id,
    )

fun EventoEntity.toEvento(): Evento =
    Evento(
        id = id,
        titulo = titulo,
        descripcion = descripcion,
        fecha = fecha,
        hora = hora,
        completado = completado,
    )

fun Evento.toEventoEntity(): EventoEntity =
    EventoEntity(
        id = id,
        titulo = titulo,
        descripcion = descripcion,
        fecha = fecha,
        hora = hora,
        completado = completado,
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