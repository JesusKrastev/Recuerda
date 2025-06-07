package com.fernandort.recuerda.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fernandort.recuerda.ui.features.juegos.JuegosScreen
import com.fernandort.recuerda.ui.features.juegos.JuegosViewModel
import kotlinx.serialization.Serializable

@Serializable
object JuegosRoute

fun NavController.navigateToJuegos(navOptions: NavOptions? = null) {
    this.navigate(JuegosRoute, navOptions)
}

fun NavGraphBuilder.juegosScreen(
    onNavigateToContactos: () -> Unit,
    onNavigateToNotas: () -> Unit,
    onNavigateToAsistenteIA: () -> Unit,
    onNavigateToBot: (String) -> Unit,
    onNavigateToEventos: () -> Unit,
) {
    composable<JuegosRoute> {
        val vm: JuegosViewModel = hiltViewModel()

        JuegosScreen(
            juegosState = vm.juegosState,
            informacionEstado = vm.informacionEstadoState,
            onNavigateToContactos = onNavigateToContactos,
            onNavigateToNotas = onNavigateToNotas,
            onNavigateToAsistenteIA = onNavigateToAsistenteIA,
            onNavigateToBot = onNavigateToBot,
            onNavigateToEventos = onNavigateToEventos,
        )
    }
}