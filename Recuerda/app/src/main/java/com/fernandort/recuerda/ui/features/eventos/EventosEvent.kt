package com.fernandort.recuerda.ui.features.eventos

import android.content.Context
import com.fernandort.recuerda.models.Evento
import java.time.LocalDate
import java.time.LocalTime

sealed interface EventosEvent {
    data object OnCrearEvento: EventosEvent
    data class OnCambiarFecha(val fecha: LocalDate): EventosEvent
    data class OnCambiarHora(val hora: LocalTime): EventosEvent
    data class OnEliminarEvento(val evento: EventoUiState, val context: Context): EventosEvent
    data class OnEditarEvento(val eventoState: EventoUiState): EventosEvent
    data class OnGuardarEvento(val context: Context): EventosEvent
    data object OnCerrarBottomSheet: EventosEvent
    data class OnCambiarTitulo(val titulo: String): EventosEvent
    data class OnCambiarDescripcion(val descripcion: String): EventosEvent
    data object OnMostrarSelectorFecha: EventosEvent
    data object OnOcultarSelectorFecha: EventosEvent
    data object OnMostrarSelectorHora: EventosEvent
    data object OnOcultarSelectorHora: EventosEvent
    data class OnCompletarEvento(val evento: EventoUiState, val completado: Boolean): EventosEvent
}