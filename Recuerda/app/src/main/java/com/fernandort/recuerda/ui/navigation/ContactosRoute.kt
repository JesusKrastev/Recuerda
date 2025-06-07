package com.fernandort.recuerda.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fernandort.recuerda.ui.features.contactos.ContactosScreen
import com.fernandort.recuerda.ui.features.contactos.ContactosViewModel
import kotlinx.serialization.Serializable

@Serializable
object ContactosRoute

fun NavController.navigateToContactos(navOptions: NavOptions? = null) {
    this.navigate(ContactosRoute, navOptions)
}

fun NavGraphBuilder.contactosScreen(
    onNavigateToNotas: () -> Unit,
    onNavigateToAsistenteIA: () -> Unit,
    onNavigateToJuegos: () -> Unit,
    onNavigateToEventos: () -> Unit,
) {
    composable<ContactosRoute> {
        val vm: ContactosViewModel = hiltViewModel()

        ContactosScreen(
            buscadorState = vm.busquedaState,
            onNavigateToNotas = onNavigateToNotas,
            validacionContactoState = vm.validacionContactoState,
            tipoBottomSheetState = vm.tipoBottomSheetState,
            informacionEstado = vm.informacionEstadoState,
            contactosState = vm.contactosFiltradoState,
            contactoState = vm.contactoState,
            onContactosEvent = vm::onContactosEvent,
            onNavigateToAsistenteIA = onNavigateToAsistenteIA,
            onNavigateToJuegos = onNavigateToJuegos,
            onNavigateToEventos = onNavigateToEventos,
        )
    }
}