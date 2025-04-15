package com.fernandort.recuerda.utilities.validacion

open class ValidadorCompuesto<T> : Validador<T> {
    private val validadores = mutableListOf<Validador<T>>()

    fun add(validador: Validador<T>): ValidadorCompuesto<T> {
        validadores.add(validador)
        return this
    }

    override fun valida(datos: T): Validacion =
        validadores
            .map { it.valida(datos) }
            .firstOrNull { it.hayError }
            ?: object : Validacion {}
}
