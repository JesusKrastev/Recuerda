package com.fernandort.recuerda.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.fernandort.recuerda.data.room.contactos.ContactosDao
import com.fernandort.recuerda.data.room.contactos.ContactosEntity
import com.fernandort.recuerda.models.Contacto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContactosRepository @Inject constructor(
    private val contactosDao: ContactosDao
) {
    suspend fun insert(contacto: Contacto)  = withContext(Dispatchers.IO) {
        contactosDao.insert(contacto.toContactoEntity())
    }

    suspend fun get(): List<Contacto> = withContext(Dispatchers.IO) {
        contactosDao.get().map { it.toContacto() }
    }

    suspend fun get(id: String): Contacto = withContext(Dispatchers.IO) {
        contactosDao.get(id).toContacto()
    }

    suspend fun delete(contacto: Contacto) = withContext(Dispatchers.IO) {
        contactosDao.delete(contacto.toContactoEntity())
    }
}