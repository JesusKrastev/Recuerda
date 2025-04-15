package com.fernandort.recuerda.utilities.validacion

interface Validador<T> {
    fun valida(datos: T): Validacion
}

