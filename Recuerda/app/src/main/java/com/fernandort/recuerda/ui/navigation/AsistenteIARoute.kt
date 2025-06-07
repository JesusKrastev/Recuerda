package com.fernandort.recuerda.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fernandort.recuerda.ui.features.asistente.AsistenteIAScreen
import com.fernandort.recuerda.ui.features.asistente.AsistenteIAViewModel
import com.fernandort.recuerda.utilities.manejo_errores.InformacionEstadoUiState
import kotlinx.serialization.Serializable

@Serializable
object AsistenteIARoute

fun NavController.navigateToAsistenteIA(navOptions: NavOptions? = null) {
    this.navigate(AsistenteIARoute, navOptions)
}

fun NavGraphBuilder.asistenteIAScreen(
    onNavigateAtras: () -> Unit
) {
    composable<AsistenteIARoute> {
        val vm: AsistenteIAViewModel = hiltViewModel()

        AsistenteIAScreen(
            informacionEstado = InformacionEstadoUiState.Oculta(),
            chatState = vm.chatState,
            onAsistenteIAEvent = vm::onAsistenteIAEvent,
            onNavigateAtras = onNavigateAtras
        )
    }
}