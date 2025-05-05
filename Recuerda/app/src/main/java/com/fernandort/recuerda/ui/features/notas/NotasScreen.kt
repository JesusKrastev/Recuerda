package com.fernandort.recuerda.ui.features.notas

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.fernandort.recuerda.ui.features.components.NavBar
import com.fernandort.recuerda.ui.features.components.TopBar
import com.fernandort.recuerda.ui.composables.TextoCuerpoLargo
import com.fernandort.recuerda.ui.features.notas.components.Nota

@Composable
fun NotaDropDownMenu(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onEliminar: () -> Unit,
    onEditar: () -> Unit,
    onDismissRequest: () -> Unit
) {
    @Immutable
    data class Option(
        val text: String,
        val icon: ImageVector,
        val onClick: () -> Unit
    )

    val options = listOf(
        Option(
            text = "Editar",
            icon = Icons.Outlined.Edit,
            onClick = onEditar
        ),
        Option(
            text = "Eliminar",
            icon = Icons.Outlined.Delete,
            onClick = onEliminar
        ),
    )

    DropdownMenu(
        modifier = modifier,
        expanded = expanded,
        containerColor = MaterialTheme.colorScheme.secondary,
        onDismissRequest = onDismissRequest
    ) {
        options.forEach { option ->
            DropdownMenuItem(
                text = {
                    Text(option.text)
                },
                onClick = {
                    option.onClick()
                    onDismissRequest()
                },
                leadingIcon = {
                    Icon(
                        imageVector = option.icon,
                        contentDescription = option.text,
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }
            )
        }
    }
}

@Composable
fun NotaItem(
    modifier: Modifier = Modifier,
    notaState: NotaUiState,
    onEliminar: () -> Unit,
    onEditar: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
    ) {
        Nota(
            modifier = Modifier.fillMaxWidth(),
            notaState = notaState,
            onClick = onEditar,
            onClickOpciones = {
                expanded = !expanded
            }
        )
        NotaDropDownMenu(
            expanded = expanded,
            onEliminar = onEliminar,
            onDismissRequest = { expanded = false },
            onEditar = onEditar
        )
    }
}

@Composable
fun ListaNotas(
    modifier: Modifier = Modifier,
    notasState: List<NotaUiState>,
    onNotasEvent: (NotasEvent) -> Unit,
    onNavigateToEditarNota: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .animateContentSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(notasState) { nota ->
            NotaItem(
                notaState = nota,
                onEditar = { onNavigateToEditarNota(nota.id) },
                onEliminar = { onNotasEvent(NotasEvent.OnEliminarNota(nota)) }
            )
        }
    }
}

@Composable
fun SinNotas(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(46.dp),
                imageVector = Icons.Filled.Book,
                contentDescription = "Sin notas",
                tint = MaterialTheme.colorScheme.tertiary
            )
            TextoCuerpoLargo(
                text = "No hay notas",
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

@Composable
fun Notas(
    modifier: Modifier = Modifier,
    notasState: List<NotaUiState>,
    onNavigateToEditarNota: (String) -> Unit,
    onNotasEvent: (NotasEvent) -> Unit,
) {
    when {
        notasState.isNotEmpty() -> {
            ListaNotas(
                modifier = modifier,
                notasState = notasState,
                onNotasEvent = onNotasEvent,
                onNavigateToEditarNota = onNavigateToEditarNota
            )
        }
        else ->
            SinNotas(
                modifier = modifier
            )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotasScreen(
    modifier: Modifier = Modifier,
    notasState: List<NotaUiState>,
    onNavigateToContactos: () -> Unit,
    onNotasEvent: (NotasEvent) -> Unit,
    onNavigateToCrearNota: () -> Unit,
    onNavigateToEditarNota: (String) -> Unit,
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            TopBar(
                title = "Notas"
            )
        },
        bottomBar = {
            NavBar(
                selectedPage = 0,
                onNavigateToNotas = { },
                onNavigateToContactos = onNavigateToContactos
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onNavigateToCrearNota,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = "Crear nota"
                    )
                    TextoCuerpoLargo(
                        text = "Crear nota",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    ) { paddingValues ->
        Notas(
            modifier = Modifier.padding(paddingValues),
            notasState = notasState,
            onNavigateToEditarNota = onNavigateToEditarNota,
            onNotasEvent = onNotasEvent
        )
    }
}