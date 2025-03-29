package com.fernandort.recuerda.data.room.contactos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ContactosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contacto : ContactosEntity)

    @Delete
    suspend fun delete(contacto : ContactosEntity)

    @Update
    suspend fun update(contacto: ContactosEntity)

    @Query("SELECT * FROM contactos WHERE id = :id")
    suspend fun get(id: String): ContactosEntity

    @Query("SELECT * FROM contactos")
    suspend fun get(): List<ContactosEntity>
}