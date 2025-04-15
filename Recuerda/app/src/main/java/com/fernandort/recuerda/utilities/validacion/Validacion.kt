package com.fernandort.recuerda.utilities.validacion

interface Validacion {
    val hayError: Boolean
        get() = false
    val mensajeError: String?
        get() = null
}