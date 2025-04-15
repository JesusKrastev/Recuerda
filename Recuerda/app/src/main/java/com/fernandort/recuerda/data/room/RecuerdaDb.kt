package com.fernandort.recuerda.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fernandort.recuerda.data.room.contactos.ContactosDao
import com.fernandort.recuerda.data.room.contactos.ContactoEntity
import com.fernandort.recuerda.data.room.eventos.EventosDao
import com.fernandort.recuerda.data.room.eventos.EventoEntity
import com.fernandort.recuerda.data.room.notas.NotasDao
import com.fernandort.recuerda.data.room.notas.NotaEntity

@Database(
    entities = [ContactoEntity::class, EventoEntity::class, NotaEntity::class],
    version = 1
)
@TypeConverters(RoomConverters::class)
abstract class RecuerdaDb: RoomDatabase() {
    abstract fun contactosDao() : ContactosDao
    abstract fun eventosDao() : EventosDao
    abstract fun notasDao() : NotasDao

    companion object {
        fun getDatabase(
            context: Context
        ) = Room.databaseBuilder(
            context,
            RecuerdaDb::class.java, "recuerda"
        )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()
    }
}