package com.fernandort.recuerda.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.fernandort.recuerda.data.room.notas.NotasDao
import com.fernandort.recuerda.data.room.notas.NotasEntity
import com.fernandort.recuerda.models.Notas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NotasRepository @Inject constructor(
    private val notasDao: NotasDao
) {
    suspend fun insert(notas: Notas) = withContext(Dispatchers.IO) {
        notasDao.insert(notas.toNotasEntity())
    }

    suspend fun delete(notas: Notas) = withContext(Dispatchers.IO) {
        notasDao.delete(notas.toNotasEntity())
    }

    suspend fun update(notas: Notas) = withContext(Dispatchers.IO) {
        notasDao.update(notas.toNotasEntity())
    }

    suspend fun get(): List<Notas> = withContext(Dispatchers.IO) {
        notasDao.get().map { it.toNotas() }
    }

    suspend fun get(id: String): Notas = withContext(Dispatchers.IO) {
        notasDao.get(id).toNotas()
    }
}