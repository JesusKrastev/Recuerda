package com.fernandort.recuerda.ui.features.contactos

import com.fernandort.recuerda.models.Contacto
import java.util.UUID

data class ContactoUiState(
    val id: String = UUID.randomUUID().toString(),
    val nombre: String = "",
    val apellidos: String = "",
    val telefono: String = ""
)

fun ContactoUiState.toContacto(): Contacto =
    Contacto(
        id = id,
        nombre = nombre,
        apellidos = apellidos,
        telefono = telefono
    )

fun Contacto.toContactoUiState(): ContactoUiState =
    ContactoUiState(
        id = id,
        nombre = nombre,
        apellidos = apellidos,
        telefono = telefono
    )