package com.fernandort.recuerda.ui.features.eventos

import com.fernandort.recuerda.utilities.validacion.Validacion
import com.fernandort.recuerda.utilities.validacion.ValidacionCompuesta

data class ValidacionEventoUiState(
    val validacionTitulo: Validacion = object : Validacion {},
    val validacionDescripcion: Validacion = object : Validacion {},
) : Validacion {
    private var validacionCompuesta: ValidacionCompuesta? = null

    private fun componerValidacion(): ValidacionCompuesta {
        validacionCompuesta = ValidacionCompuesta()
            .add(validacionTitulo)
            .add(validacionDescripcion)
        return validacionCompuesta!!
    }

    override val hayError: Boolean
        get() = validacionCompuesta?.hayError ?: componerValidacion().hayError
    override val mensajeError: String?
        get() = validacionCompuesta?.mensajeError ?: componerValidacion().mensajeError
}
