package com.fernandort.recuerda.ui.navigation

import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.fernandort.recuerda.ui.features.formnota.FormNotaScreen
import com.fernandort.recuerda.ui.features.formnota.FormNotaViewModel
import kotlinx.serialization.Serializable

@Serializable
data class FormNotaRoute(
    val id: String? = null
)

fun NavController.navigateToFormNota(
    id: String,
    navOptions: NavOptions? = null
) {
    this.navigate(FormNotaRoute(id = id), navOptions)
}

fun NavController.navigateToFormNota(navOptions: NavOptions? = null) {
    this.navigate(FormNotaRoute(), navOptions)
}

fun NavGraphBuilder.formNotaScreen(
    onNavigateAtras: () -> Unit
) {
    composable<FormNotaRoute> { backStackEntry ->
        val vm: FormNotaViewModel = hiltViewModel()
        val args: FormNotaRoute = backStackEntry.toRoute()
        val id: String? = args.id

        if (
            id != null &&
            vm.notaState.id != id
        ) vm.setNotaState(id = id)

        FormNotaScreen(
            notaState = vm.notaState,
            editando = vm.editando,
            onFormNotaEvent = vm::onFormNotaEvent,
            onNavigateAtras = onNavigateAtras
        )
    }
}