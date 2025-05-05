package com.fernandort.recuerda.ui.features.formnota

interface FormNotaEvent {
    data class OnCambiarTitulo(val titulo: String): FormNotaEvent
    data class OnCambiarDescripcion(val descripcion: String): FormNotaEvent
    data class OnGuardarNota(val onNavigateAtras: () -> Unit): FormNotaEvent
    data class OnEliminarNota(val onNavigateAtras: () -> Unit): FormNotaEvent
}