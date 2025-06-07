package com.fernandort.recuerda.data

import com.fernandort.recuerda.data.room.eventos.EventosDao
import com.fernandort.recuerda.models.Evento
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EventosRepository @Inject constructor(
    private val eventosDao: EventosDao
) {
    suspend fun insert(evento: Evento) = withContext(Dispatchers.IO) {
        eventosDao.insert(evento.toEventoEntity())
    }

    suspend fun update(evento: Evento) = withContext(Dispatchers.IO) {
        eventosDao.update(evento.toEventoEntity())
    }

    suspend fun delete(evento: Evento) = withContext(Dispatchers.IO) {
        eventosDao.delete(evento.toEventoEntity())
    }

    suspend fun get(): Flow<List<Evento>> = withContext(Dispatchers.IO) {
        eventosDao.get().map { eventos ->
            eventos.map { it.toEvento() }
        }
    }
}