package com.fernandort.recuerda.data.room.eventos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface EventosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(eventos : EventosEntity)

    @Delete
    suspend fun delete(eventos : EventosEntity)

    @Update
    suspend fun update(eventos: EventosEntity)

    @Query("SELECT * FROM eventos WHERE id = :id")
    suspend fun get(id: String): EventosEntity

    @Query("SELECT * FROM eventos")
    suspend fun get(): List<EventosEntity>
}