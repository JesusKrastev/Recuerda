package com.fernandort.recuerda.data

import com.fernandort.recuerda.data.room.contactos.ContactosDao
import com.fernandort.recuerda.models.Contacto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContactosRepository @Inject constructor(
    private val contactosDao: ContactosDao
) {
    suspend fun insert(contacto: Contacto) = withContext(Dispatchers.IO) {
        contactosDao.insert(contacto.toContactoEntity())
    }

    suspend fun get(): Flow<List<Contacto>> = withContext(Dispatchers.IO) {
        contactosDao.get().map { contactos ->
            contactos.map { it.toContacto() }
        }
    }

    suspend fun update(contacto: Contacto) = withContext(Dispatchers.IO) {
        contactosDao.update(contacto.toContactoEntity())
    }

    suspend fun get(id: String): Contacto = withContext(Dispatchers.IO) {
        contactosDao.get(id).toContacto()
    }

    suspend fun delete(contacto: Contacto) = withContext(Dispatchers.IO) {
        contactosDao.delete(contacto.toContactoEntity())
    }
}