package com.fernandort.recuerda.utilities.validacion.validadores

import com.fernandort.recuerda.utilities.validacion.Validacion
import com.fernandort.recuerda.utilities.validacion.Validador

class ValidadorLongitudMaximaTexto(
    val tamañoMaximo: Int,
    val error: String = "El texto debe ser inferior o igual a ${tamañoMaximo}"
) : Validador<String> {
    override fun valida(texto: String): Validacion {
        return object : Validacion {
            override val hayError: Boolean
                get() = texto.length > tamañoMaximo
            override val mensajeError: String
                get() = error
        }
    }
}