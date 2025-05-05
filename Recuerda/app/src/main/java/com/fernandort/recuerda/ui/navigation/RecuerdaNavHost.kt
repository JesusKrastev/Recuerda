package com.fernandort.recuerda.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.fernandort.recuerda.ui.features.notas.NotasViewModel

@Composable
fun RecuerdaNavHost() {
    val navController: NavHostController = rememberNavController()
    val vmNotas: NotasViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = NotasRoute
    ) {
        notasScreen(
            vm = vmNotas,
            onNavigateToCrearNota = {
                navController.navigateToFormNota()
            },
            onNavigateToEditarNota = { id ->
                navController.navigateToFormNota(id = id)
            },
            onNavigateToContactos = {
                navController.navigateToContactos()
            },
        )
        formNotaScreen(
            onNavigateAtras = {
                navController.navigateUp()
            }
        )
        contactosScreen(
            onNavigateToNotas = {
                navController.navigateToNotas()
            }
        )
    }
}