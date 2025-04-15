package com.fernandort.recuerda.ui.features.contactos

import com.fernandort.recuerda.utilities.validacion.Validacion
import com.fernandort.recuerda.utilities.validacion.ValidacionCompuesta

data class ValidacionContactoUiState(
    val validacionNombre: Validacion = object : Validacion {},
    val validacionApellidos: Validacion = object : Validacion {},
    val validacionTelefono: Validacion = object : Validacion {}
) : Validacion {
    private var validacionCompuesta: ValidacionCompuesta? = null

    private fun componerValidacion(): ValidacionCompuesta {
        validacionCompuesta = ValidacionCompuesta()
            .add(validacionNombre)
            .add(validacionApellidos)
            .add(validacionTelefono)
        return validacionCompuesta!!
    }

    override val hayError: Boolean
        get() = validacionCompuesta?.hayError ?: componerValidacion().hayError
    override val mensajeError: String?
        get() = validacionCompuesta?.mensajeError ?: componerValidacion().mensajeError
}
