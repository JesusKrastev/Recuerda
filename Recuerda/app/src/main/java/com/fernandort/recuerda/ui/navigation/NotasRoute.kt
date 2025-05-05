package com.fernandort.recuerda.ui.navigation

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
    vm: NotasViewModel,
    onNavigateToContactos: () -> Unit,
    onNavigateToCrearNota: () -> Unit,
    onNavigateToEditarNota: (String) -> Unit,
) {
    composable<NotasRoute> {
        NotasScreen(
            notasState = vm.notasState,
            onNavigateToCrearNota = onNavigateToCrearNota,
            onNavigateToEditarNota = onNavigateToEditarNota,
            onNotasEvent = vm::onNotasEvent,
            onNavigateToContactos = onNavigateToContactos
        )
    }
}