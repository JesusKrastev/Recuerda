package com.fernandort.recuerda.utilities.validacion

open class ValidacionCompuesta : Validacion {
    private val validaciones = mutableListOf<Validacion>()

    fun add(validacion: Validacion): ValidacionCompuesta {
        validaciones.add(validacion)
        return this
    }

    override val hayError: Boolean
        get() = validaciones.any { it.hayError }

    override val mensajeError: String?
        get() = validaciones.firstOrNull { it.hayError }?.mensajeError
}