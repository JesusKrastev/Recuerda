package com.fernandort.recuerda.utilities.manejo_errores

sealed class InformacionEstadoUiState(val visible: Boolean, val mensaje: String = "") {
    class Oculta :
        InformacionEstadoUiState(
            visible = false
        )
    class Informacion(mensaje: String, val muestraProgreso: Boolean = false) :
        InformacionEstadoUiState(
            visible = true,
            mensaje = mensaje
        )
    class Error(mensaje: String, val onDismiss: () -> Unit) :
        InformacionEstadoUiState(
            visible = true,
            mensaje = mensaje
        )
}
