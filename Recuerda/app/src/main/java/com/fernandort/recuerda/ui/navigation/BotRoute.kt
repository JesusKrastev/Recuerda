package com.fernandort.recuerda.ui.navigation

import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.fernandort.recuerda.ui.features.formnota.FormNotaScreen
import com.fernandort.recuerda.ui.features.juegos.bot.BotScreen
import com.fernandort.recuerda.ui.features.juegos.bot.BotViewModel
import kotlinx.serialization.Serializable

@Serializable
data class BotRoute(
    val juegoId: String? = null
)

fun NavController.navigateToBot(
    juegoId: String,
    navOptions: NavOptions? = null
) {
    this.navigate(BotRoute(juegoId = juegoId), navOptions)
}

fun NavGraphBuilder.botScreen(
    onNavigateAtras: () -> Unit
) {
    composable<BotRoute> { backStackEntry ->
        val vm: BotViewModel = hiltViewModel()
        val args: BotRoute = backStackEntry.toRoute()
        val juegoId: String? = args.juegoId

        if (
            juegoId != null &&
            vm.juegoState.id != juegoId
        ) vm.establecerJuegoState(juegoId = juegoId)

        BotScreen(
            informacionEstado = vm.informacionEstado,
            onBotEvent = vm::onBotEvent,
            chatState = vm.chatState,
            onNavigateAtras = onNavigateAtras,
            juegoState = vm.juegoState
        )
    }
}