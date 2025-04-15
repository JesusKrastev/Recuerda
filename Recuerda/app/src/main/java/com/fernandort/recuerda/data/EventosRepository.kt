package com.fernandort.recuerda.data

import com.fernandort.recuerda.data.room.eventos.EventosDao
import com.fernandort.recuerda.models.Evento
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EventosRepository @Inject constructor(
    private val eventosDao: EventosDao
) {
    suspend fun insert(evento: Evento) = withContext(Dispatchers.IO) {
        eventosDao.insert(evento.toEventoEntity())
    }

    suspend fun get(): List<Evento> = withContext(Dispatchers.IO) {
        eventosDao.get().map { it.toEvento() }
    }
}