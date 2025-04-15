package com.fernandort.recuerda.data.room.contactos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contacto : ContactoEntity)

    @Delete
    suspend fun delete(contacto : ContactoEntity)

    @Update
    suspend fun update(contacto: ContactoEntity)

    @Query("SELECT * FROM contactos WHERE id = :id")
    suspend fun get(id: String): ContactoEntity

    @Query("SELECT * FROM contactos")
    fun get(): Flow<List<ContactoEntity>>
}