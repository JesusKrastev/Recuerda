package com.fernandort.recuerda.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fernandort.recuerda.ui.features.notas.NotasScreen
import com.fernandort.recuerda.ui.features.notas.NotasViewModel
import kotlinx.serialization.Serializable

@Serializable
object NotasRoute

fun NavController.navigateToNotas(navOptions: NavOptions? = null) {
    this.navigate(NotasRoute, navOptions)
}

fun NavGraphBuilder.notasScreen(
    onNavigateToContactos: () -> Unit,
    onNavigateToCrearNota: () -> Unit,
    onNavigateToJuegos: () -> Unit,
    onNavigateToEditarNota: (String) -> Unit,
    onNavigateToEventos: () -> Unit,
    onNavigateToAsistenteIA: () -> Unit,
) {
    composable<NotasRoute> {
        val vm: NotasViewModel = hiltViewModel()

        NotasScreen(
            notasState = vm.notasState,
            onNavigateToCrearNota = onNavigateToCrearNota,
            onNavigateToEditarNota = onNavigateToEditarNota,
            onNotasEvent = vm::onNotasEvent,
            onNavigateToContactos = onNavigateToContactos,
            onNavigateToAsistenteIA = onNavigateToAsistenteIA,
            onNavigateToJuegos = onNavigateToJuegos,
            onNavigateToEventos = onNavigateToEventos,
        )
    }
}