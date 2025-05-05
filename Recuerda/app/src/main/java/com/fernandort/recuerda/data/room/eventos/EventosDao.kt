package com.fernandort.recuerda.data.room.eventos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface EventosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(eventos : EventoEntity)

    @Delete
    suspend fun delete(eventos : EventoEntity)

    @Update
    suspend fun update(eventos: EventoEntity)

    @Query("SELECT * FROM eventos WHERE id = :id")
    suspend fun get(id: String): EventoEntity

    @Query("SELECT * FROM eventos")
    fun get(): Flow<List<EventoEntity>>
}