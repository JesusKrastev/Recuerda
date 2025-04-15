package com.fernandort.recuerda.data.room.notas

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotasDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notas : NotaEntity)

    @Delete
    suspend fun delete(notas : NotaEntity)

    @Update
    suspend fun update(notas: NotaEntity)

    @Query("SELECT * FROM notas WHERE id = :id")
    suspend fun get(id: String): NotaEntity

    @Query("SELECT * FROM notas")
    suspend fun get(): List<NotaEntity>
}