package com.fernandort.recuerda.ui.features.formnota

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.fernandort.recuerda.ui.features.components.Boton
import com.fernandort.recuerda.ui.features.components.TopBar
import com.fernandort.recuerda.ui.composables.TextoCuerpoLargo
import com.fernandort.recuerda.ui.features.notas.NotaUiState
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CampoTitulo(
    modifier: Modifier = Modifier,
    tituloState: String,
    onCambiarValor: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = tituloState,
        onValueChange = onCambiarValor,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        ),
        placeholder = {
            TextoCuerpoLargo(
                text = "Título",
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    )
}

@Composable
fun CampoDescripcion(
    modifier: Modifier = Modifier,
    descripcionState: String,
    onCambiarValor: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier.fillMaxSize(),
        value = descripcionState,
        onValueChange = onCambiarValor,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        ),
        placeholder = {
            TextoCuerpoLargo(
                text = "Empieza a escribir",
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    )
}

@Composable
fun InformacionNota(
    modifier: Modifier = Modifier,
    fechaState: LocalDateTime,
    caracteresState: Int
) {
    val mes = fechaState.month.getDisplayName(TextStyle.FULL, Locale("es"))
    val hora = "%02d:%02d".format(fechaState.hour, fechaState.minute)

    TextoCuerpoLargo(
        modifier = modifier.padding(start = 16.dp),
        text = "${fechaState.dayOfMonth} de $mes $hora | $caracteresState caracteres",
        color = MaterialTheme.colorScheme.tertiary
    )
}


@Composable
fun FormNota(
    modifier: Modifier = Modifier,
    notaState: NotaUiState,
    onFormNotaEvent: (FormNotaEvent) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        CampoTitulo(
            tituloState = notaState.titulo,
            onCambiarValor = {
                onFormNotaEvent(FormNotaEvent.OnCambiarTitulo(it))
            }
        )
        InformacionNota(
            fechaState = notaState.fecha,
            caracteresState = notaState.descripcion.length
        )
        CampoDescripcion(
            descripcionState = notaState.descripcion,
            onCambiarValor = {
                onFormNotaEvent(FormNotaEvent.OnCambiarDescripcion(it))
            }
        )
    }
}

@Composable
fun NotaDropDownMenu(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onEliminar: () -> Unit,
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
            text = "Eliminar",
            icon = Icons.Outlined.Delete,
            onClick = onEliminar
        )
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
fun BotonOpciones(
    modifier: Modifier = Modifier,
    onEliminar: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
    ) {
        IconButton(
            onClick = { expanded = !expanded }
        ) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = "Opciones",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
        NotaDropDownMenu(
            expanded = expanded,
            onEliminar = onEliminar,
            onDismissRequest = { expanded = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormNotaScreen(
    modifier: Modifier = Modifier,
    notaState: NotaUiState,
    editando: Boolean,
    onNavigateAtras: () -> Unit,
    onFormNotaEvent: (FormNotaEvent) -> Unit,
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            TopBar(
                title = if (editando) "Editar nota" else "Crea nota",
                actions = {
                    if(editando)
                        BotonOpciones(
                            onEliminar = {
                                onFormNotaEvent(FormNotaEvent.OnEliminarNota(onNavigateAtras))
                            }
                        )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateAtras
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Atras",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        },
        bottomBar = {
            Boton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = "Guardar",
                onClick = {
                    onFormNotaEvent(FormNotaEvent.OnGuardarNota(onNavigateAtras))
                }
            )
        }
    ) { paddingValues ->
        FormNota(
            modifier = Modifier.padding(paddingValues),
            notaState = notaState,
            onFormNotaEvent = onFormNotaEvent
        )
    }
}

