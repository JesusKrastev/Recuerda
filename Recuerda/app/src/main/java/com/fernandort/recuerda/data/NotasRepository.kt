package com.fernandort.recuerda.data

import com.fernandort.recuerda.data.room.notas.NotasDao
import com.fernandort.recuerda.models.Nota
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NotasRepository @Inject constructor(
    private val notasDao: NotasDao
) {
    suspend fun insert(notas: Nota) = withContext(Dispatchers.IO) {
        notasDao.insert(notas.toNotaEntity())
    }

    suspend fun delete(notas: Nota) = withContext(Dispatchers.IO) {
        notasDao.delete(notas.toNotaEntity())
    }

    suspend fun update(notas: Nota) = withContext(Dispatchers.IO) {
        notasDao.update(notas.toNotaEntity())
    }

    suspend fun get(): List<Nota> = withContext(Dispatchers.IO) {
        notasDao.get().map { it.toNota() }
    }

    suspend fun get(id: String): Nota = withContext(Dispatchers.IO) {
        notasDao.get(id).toNota()
    }
}