package com.fernandort.recuerda.ui.features.contactos

import com.fernandort.recuerda.models.Contacto

data class ContactoUiState(
    val id: String,
    val nombre: String,
    val telefono: String
)

fun ContactoUiState.toContacto(): Contacto =
    Contacto(
        id = id,
        nombre = nombre,
        telefono = telefono
    )

fun Contacto.toContactoUiState(): ContactoUiState =
    ContactoUiState(
        id = id,
        nombre = nombre,
        telefono = telefono
    )