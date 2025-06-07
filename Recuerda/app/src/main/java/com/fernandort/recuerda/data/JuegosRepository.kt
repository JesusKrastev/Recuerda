package com.fernandort.recuerda.data

import com.fernandort.recuerda.data.mocks.JuegosDaoMock
import com.fernandort.recuerda.models.Juego
import javax.inject.Inject

class JuegosRepository @Inject constructor(
    private val juegosDaoMock: JuegosDaoMock
) {
    fun get(): List<Juego> = juegosDaoMock.get().map { it.toJuego() }
    fun getById(id: String): Juego? = juegosDaoMock.getById(id)?.toJuego()
}