package com.fernandort.recuerda.data.room.notas

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.fernandort.recuerda.data.room.eventos.EventosEntity

@Dao
interface NotasDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notas : NotasEntity)

    @Delete
    suspend fun delete(notas : NotasEntity)

    @Update
    suspend fun update(notas: NotasEntity)

    @Query("SELECT * FROM notas WHERE id = :id")
    suspend fun get(id: String): NotasEntity

    @Query("SELECT * FROM notas")
    suspend fun get(): List<NotasEntity>
}