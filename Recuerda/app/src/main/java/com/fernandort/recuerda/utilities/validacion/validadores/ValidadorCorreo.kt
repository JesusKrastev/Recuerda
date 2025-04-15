package com.fernandort.recuerda.utilities.validacion.validadores

import com.fernandort.recuerda.utilities.validacion.Validacion
import com.fernandort.recuerda.utilities.validacion.Validador

class ValidadorCorreo(
    val error: String = "Correo no válido"
) : Validador<String> {
    override fun valida(texto: String): Validacion {
        return object : Validacion {
            override val hayError: Boolean
                get() = !Regex("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})$").matches(texto)
            override val mensajeError: String
                get() = error
        }
    }
}