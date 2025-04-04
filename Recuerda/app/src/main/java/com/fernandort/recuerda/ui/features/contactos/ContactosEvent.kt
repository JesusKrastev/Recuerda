package com.fernandort.recuerda.ui.features.contactos

interface ContactosEvent {
    data object OnCrearContacto: ContactosEvent
    data class OnEliminarContacto(val id: String): ContactosEvent
    data class OnEditarContacto(val id: String): ContactosEvent
    data object OnLlamarContacto: ContactosEvent
    data class OnNombreChange(val nombre: String): ContactosEvent
    data class OnTelefonoChange(val telefono: String): ContactosEvent
}