package com.fernandort.recuerda.data

import com.fernandort.recuerda.data.room.contactos.ContactosDao
import javax.inject.Inject

class ContactosRepository @Inject constructor(
    private val contactosDao: ContactosDao
) {
}