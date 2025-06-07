package com.fernandort.recuerda.di

import android.content.Context
import com.fernandort.recuerda.BuildConfig
import com.fernandort.recuerda.data.room.RecuerdaDb
import com.fernandort.recuerda.data.room.contactos.ContactosDao
import com.fernandort.recuerda.data.room.eventos.EventosDao
import com.fernandort.recuerda.data.room.notas.NotasDao
import com.google.ai.client.generativeai.GenerativeModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideGemini(): GenerativeModel =
        GenerativeModel(
            modelName = "gemini-2.0-flash",
            apiKey = BuildConfig.GEMINI_API_KEY
        )

    @Provides
    @Singleton
    fun proveeBaseDatos(
        @ApplicationContext context: Context
    ): RecuerdaDb = RecuerdaDb.getDatabase(context)

    @Provides
    @Singleton
    fun proveeEventosDao(
        recuerdaDB: RecuerdaDb
    ): EventosDao = recuerdaDB.eventosDao()

    @Provides
    @Singleton
    fun proveeContactosDao(
        recuerdaDB: RecuerdaDb
    ): ContactosDao = recuerdaDB.contactosDao()

    @Provides
    @Singleton
    fun proveeNotasDao(
        recuerdaDB: RecuerdaDb
    ): NotasDao = recuerdaDB.notasDao()
}