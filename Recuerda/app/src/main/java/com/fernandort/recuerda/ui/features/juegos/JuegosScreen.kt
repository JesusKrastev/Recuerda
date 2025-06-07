package com.fernandort.recuerda.ui.features.juegos

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fernandort.recuerda.ui.composables.CorrutinaGestionSnackBar
import com.fernandort.recuerda.ui.composables.GradientBrush
import com.fernandort.recuerda.ui.composables.SnackbarCommon
import com.fernandort.recuerda.ui.composables.TextoCuerpoLargo
import com.fernandort.recuerda.ui.composables.TextoTituloLargo
import com.fernandort.recuerda.ui.features.components.NavBar
import com.fernandort.recuerda.utilities.manejo_errores.InformacionEstadoUiState

@Composable
fun Juego(
    modifier: Modifier = Modifier,
    juegoState: JuegoUiState,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = GradientBrush(
                    colors = juegoState.fondo,
                    isVerticalGradient = true
                )
            )
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = Color.White
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = juegoState.icono,
                contentDescription = juegoState.nombre
            )
            TextoCuerpoLargo(
                text = juegoState.nombre,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            TextoCuerpoLargo(
                text = juegoState.descripcion,
                color = Color.White
            )
        }
    }
}

@Composable
fun ListaJuegos(
    modifier: Modifier = Modifier,
    juegosState: List<JuegoUiState>,
    onClick: (String) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(juegosState) { juego ->
            Juego(
                juegoState = juego,
                onClick = { onClick(juego.id) }
            )
        }
    }
}

@Composable
fun Juegos(
    modifier: Modifier = Modifier,
    juegosState: List<JuegoUiState>,
    onNavigateToBot: (String) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        TextoTituloLargo(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "Explora\nlos juegos",
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )
        ListaJuegos(
            juegosState = juegosState,
            onClick = onNavigateToBot
        )
    }
}

@Composable
fun JuegosScreen(
    modifier: Modifier = Modifier,
    juegosState: List<JuegoUiState>,
    onNavigateToNotas: () -> Unit,
    onNavigateToEventos: () -> Unit,
    onNavigateToContactos: () -> Unit,
    onNavigateToBot: (String) -> Unit,
    onNavigateToAsistenteIA: () -> Unit,
    informacionEstado: InformacionEstadoUiState,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    CorrutinaGestionSnackBar(
        snackbarHostState = snackbarHostState,
        informacionEstado = informacionEstado
    )

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        bottomBar = {
            NavBar(
                selectedPage = 3,
                onNavigateToNotas = onNavigateToNotas,
                onNavigateToContactos = onNavigateToContactos,
                onNavigateToAsistenteIA = onNavigateToAsistenteIA,
                onNavigateToJuegos = { },
                onNavigateToEventos = onNavigateToEventos,
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) {
                SnackbarCommon(informacionEstado = informacionEstado)
            }
        }
    ) { paddingValues ->
        Juegos(
            modifier = Modifier.padding(paddingValues),
            juegosState = juegosState,
            onNavigateToBot = onNavigateToBot
        )
    }
}