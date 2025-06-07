package com.fernandort.recuerda.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fernandort.recuerda.ui.features.eventos.EventoUiState
import com.fernandort.recuerda.ui.features.eventos.EventosScreen
import com.fernandort.recuerda.ui.features.eventos.EventosViewModel
import com.fernandort.recuerda.utilities.manejo_errores.InformacionEstadoUiState
import kotlinx.serialization.Serializable

@Serializable
object EventosRoute

fun NavController.navigateToEventos(navOptions: NavOptions? = null) {
    this.navigate(EventosRoute, navOptions)
}

fun NavGraphBuilder.eventosScreen(
    onNavigateToContactos: () -> Unit,
    onNavigateToJuegos: () -> Unit,
    onNavigateToNotas: () -> Unit,
    onNavigateToAsistenteIA: () -> Unit,
) {
    composable<EventosRoute> {
        val vm: EventosViewModel = hiltViewModel()

        EventosScreen(
            eventosState = vm.eventosState,
            informacionEstado = InformacionEstadoUiState.Oculta(),
            onNavigateToContactos = onNavigateToContactos,
            onNavigateToJuegos = onNavigateToJuegos,
            onNavigateToNotas = onNavigateToNotas,
            onNavigateToAsistenteIA = onNavigateToAsistenteIA,
            editando = vm.editando,
            onEventosEvent = vm::onEventosEvent,
            eventoState = vm.eventoState,
            tipoBottomSheetState = vm.tipoBottomSheetState,
            mostrarSelectorFechaState = vm.mostrarSelectorFechaState,
            mostrarSelectorHoraState = vm.mostrarSelectorHoraState,
            validacionEventoState = vm.validacionEventoState,
        )
    }
}