package com.fernandort.recuerda.ui.features.contactos

interface ContactosEvent {
    data object OnCrearContacto: ContactosEvent
    data class OnEliminarContacto(val contactoUiState: ContactoUiState): ContactosEvent
    data class OnEditarContacto(val contactoUiState: ContactoUiState): ContactosEvent
    data object OnGuardarContacto: ContactosEvent
    data object OnLlamarContacto: ContactosEvent
    data class OnNombreChange(val nombre: String): ContactosEvent
    data class OnApellidoChange(val apellido: String): ContactosEvent
    data class OnTelefonoChange(val telefono: String): ContactosEvent
    data object OnCerrarBottomSheet: ContactosEvent
    data class OnCambiarBusqueda(val busqueda: String): ContactosEvent
}