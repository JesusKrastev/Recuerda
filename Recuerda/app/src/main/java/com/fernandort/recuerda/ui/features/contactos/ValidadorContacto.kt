package com.fernandort.recuerda.ui.features.contactos

import com.fernandort.recuerda.utilities.validacion.Validador
import com.fernandort.recuerda.utilities.validacion.ValidadorCompuesto
import com.fernandort.recuerda.utilities.validacion.validadores.ValidadorLongitudMaximaTexto
import com.fernandort.recuerda.utilities.validacion.validadores.ValidadorLongitudMinimaTexto
import com.fernandort.recuerda.utilities.validacion.validadores.ValidadorTelefono
import com.fernandort.recuerda.utilities.validacion.validadores.ValidadorTextoNoVacio
import jakarta.inject.Inject

class ValidadorContacto @Inject constructor() : Validador<ContactoUiState> {
    val validadorNombre = ValidadorCompuesto<String>()
        .add(ValidadorTextoNoVacio("El nombre puede estar vacío"))
        .add(ValidadorLongitudMaximaTexto(20))
    val validadorApellidos = ValidadorCompuesto<String>()
        .add(ValidadorTextoNoVacio("Los apellidos pueden estar vacíos"))
        .add(ValidadorLongitudMaximaTexto(20))
    val validadorTelefono = ValidadorCompuesto<String>()
        .add(ValidadorTextoNoVacio("El teléfono puede estar vacío"))
        .add(ValidadorLongitudMinimaTexto(9, "El teléfono debe tener 9 caracteres"))
        .add(ValidadorLongitudMaximaTexto(18, "El teléfono debe tener 18 caracteres"))
        .add(ValidadorTelefono("El teléfono no es válido"))

    override fun valida(contatoState : ContactoUiState): ValidacionContactoUiState {
        val validacionNombre = validadorNombre.valida(contatoState.nombre)
        val validacionApellidos = validadorApellidos.valida(contatoState.apellidos)
        val validacionTelefono = validadorTelefono.valida(contatoState.telefono)

        return ValidacionContactoUiState(
            validacionNombre = validacionNombre,
            validacionApellidos = validacionApellidos,
            validacionTelefono = validacionTelefono
        )
    }
}