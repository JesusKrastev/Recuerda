package com.fernandort.recuerda.ui.features.eventos

import com.fernandort.recuerda.utilities.validacion.Validador
import com.fernandort.recuerda.utilities.validacion.ValidadorCompuesto
import com.fernandort.recuerda.utilities.validacion.validadores.ValidadorLongitudMaximaTexto
import com.fernandort.recuerda.utilities.validacion.validadores.ValidadorTextoNoVacio
import jakarta.inject.Inject

class ValidadorEvento @Inject constructor() : Validador<EventoUiState> {
    val validadorTitulo = ValidadorCompuesto<String>()
        .add(ValidadorTextoNoVacio("El título puede estar vacío"))
        .add(ValidadorLongitudMaximaTexto(20))
    val validadorDescripcion = ValidadorCompuesto<String>()
        .add(ValidadorTextoNoVacio("La descripción no puede estar vacía"))

    override fun valida(eventoState : EventoUiState): ValidacionEventoUiState {
        val validacionTitulo = validadorTitulo.valida(eventoState.titulo)
        val validacionDescripcion = validadorDescripcion.valida(eventoState.descripcion)

        return ValidacionEventoUiState(
            validacionTitulo = validacionTitulo,
            validacionDescripcion = validacionDescripcion,
        )
    }
}