package com.fernandort.recuerda.data

import com.fernandort.recuerda.data.mocks.eventos.EventosDaoMock
import com.fernandort.recuerda.data.room.eventos.EventosDao
import com.fernandort.recuerda.models.Eventos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EventosRepository @Inject constructor(
    private val eventosDao: EventosDao
) {
    suspend fun insert(evento: Eventos) = withContext(Dispatchers.IO) {
        eventosDao.insert(evento.toEventosEntity())
    }

    suspend fun get(): List<Eventos> = withContext(Dispatchers.IO) {
        eventosDao.get().map { it.toEventos() }
    }
}