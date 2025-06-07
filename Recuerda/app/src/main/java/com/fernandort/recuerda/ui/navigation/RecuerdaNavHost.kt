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

    NavHost(
        navController = navController,
        startDestination = EventosRoute
    ) {
        notasScreen(
            onNavigateToCrearNota = {
                navController.navigateToFormNota()
            },
            onNavigateToEditarNota = { id ->
                navController.navigateToFormNota(id = id)
            },
            onNavigateToContactos = {
                navController.navigateToContactos()
            },
            onNavigateToAsistenteIA = {
                navController.navigateToAsistenteIA()
            },
            onNavigateToJuegos = {
                navController.navigateToJuegos()
            },
            onNavigateToEventos = {
                navController.navigateToEventos()
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
            },
            onNavigateToAsistenteIA = {
                navController.navigateToAsistenteIA()
            },
            onNavigateToJuegos = {
                navController.navigateToJuegos()
            },
            onNavigateToEventos = {
                navController.navigateToEventos()
            },
        )
        asistenteIAScreen(
            onNavigateAtras = {
                navController.navigateUp()
            }
        )
        juegosScreen(
            onNavigateToContactos = {
                navController.navigateToContactos()
            },
            onNavigateToNotas = {
                navController.navigateToNotas()
            },
            onNavigateToAsistenteIA = {
                navController.navigateToAsistenteIA()
            },
            onNavigateToBot = {
                navController.navigateToBot(juegoId = it)
            },
            onNavigateToEventos = {
                navController.navigateToEventos()
            },
        )
        botScreen(
            onNavigateAtras = {
                navController.navigateUp()
            }
        )
        eventosScreen(
            onNavigateToContactos = {
                navController.navigateToContactos()
            },
            onNavigateToJuegos = {
                navController.navigateToJuegos()
            },
            onNavigateToNotas = {
                navController.navigateToNotas()
            },
            onNavigateToAsistenteIA = {
                navController.navigateToAsistenteIA()
            },
        )
    }
}