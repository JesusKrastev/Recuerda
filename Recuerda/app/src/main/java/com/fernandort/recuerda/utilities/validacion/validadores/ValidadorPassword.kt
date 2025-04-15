package com.fernandort.recuerda.utilities.validacion.validadores

import com.fernandort.recuerda.utilities.validacion.Validacion
import com.fernandort.recuerda.utilities.validacion.Validador

class ValidadorPassword(
    val error: String = "Password débil (mínimo 8 caracteres, 1 mayúscula, 1 minúscula, 1 número y 1 carácter especial)"
) : Validador<String> {
    override fun valida(texto: String): Validacion {
        return object : Validacion {
            override val hayError: Boolean
                get() = !Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=_])(?=\\S+$).{8,}$").matches(texto)

            override val mensajeError: String
                get() = error
        }
    }
}