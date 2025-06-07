package com.fernandort.recuerda.ui.features.asistente

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.fernandort.recuerda.ui.composables.CorrutinaGestionSnackBar
import com.fernandort.recuerda.ui.composables.SnackbarCommon
import com.fernandort.recuerda.ui.composables.TextoCuerpoLargo
import com.fernandort.recuerda.ui.features.ChatUiState
import com.fernandort.recuerda.ui.features.components.TopBar
import com.fernandort.recuerda.utilities.manejo_errores.InformacionEstadoUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun String.toMarkdown(): AnnotatedString {
    val lines = this.split("\n")

    return buildAnnotatedString {
        lines.forEachIndexed { index, line ->
            when {
                line.startsWith("**") && line.endsWith("**") -> {
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(line.removeSurrounding("**").trim())
                    }
                }

                line.contains("**") -> {
                    var position = 0

                    while (position < line.length) {
                        val nextBoldStart = line.indexOf("**", position)
                        if (nextBoldStart != -1) {
                            append(line.substring(position, nextBoldStart).replace("*", "•"))
                            val nextBoldEnd = line.indexOf("**", nextBoldStart + 2)
                            if (nextBoldEnd != -1) {
                                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append(line.substring(nextBoldStart + 2, nextBoldEnd))
                                }
                                position = nextBoldEnd + 2
                            }
                        } else {
                            append(line.substring(position))
                            break
                        }
                    }
                }

                line.startsWith("*") -> {
                    append("• ${line.removePrefix("*").trim()}")
                }

                else -> {
                    append(line)
                }
            }

            if (index < lines.size - 1) append("\n")
        }
    }
}

@Composable
fun MensajeCargando(
    modifier: Modifier = Modifier
) {
    var animacion by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        while (true) {
            delay(500)
            animacion = when (animacion) {
                "" -> "."
                "." -> ".."
                ".." -> "..."
                else -> ""
            }
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 16.dp
                    )
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.secondary
                )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                TextoCuerpoLargo(
                    text = animacion,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }
}

@Composable
fun MensajeAsistente(
    modifier: Modifier = Modifier,
    mensaje: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 16.dp
                    )
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.secondary
                )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                TextoCuerpoLargo(
                    text = mensaje.toMarkdown(),
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }
}

@Composable
fun MensajeUsuario(
    modifier: Modifier = Modifier,
    mensaje: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        Card(
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = 16.dp,
                bottomEnd = 0.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                TextoCuerpoLargo(
                    text = mensaje,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
fun ListaChat(
    modifier: Modifier = Modifier,
    chatState: ChatUiState,
    listState: LazyListState
) {
    LaunchedEffect(chatState.mensajes.size) {
        if (chatState.mensajes.isNotEmpty()) {
            listState.animateScrollToItem(chatState.mensajes.lastIndex + 1)
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        state = listState,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        itemsIndexed(chatState.mensajes) { index, mensaje ->
            when {
                mensaje.esDelUsuario ->
                    MensajeUsuario(
                        mensaje = mensaje.texto
                    )
                else ->
                    MensajeAsistente(
                        mensaje = mensaje.texto
                    )
            }
        }
        item {
            if (chatState.estaPensado) MensajeCargando()
        }
    }
}

@Composable
fun ChatVacio(
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
                imageVector = Icons.Filled.Chat,
                contentDescription = "Sin mensajes",
                tint = MaterialTheme.colorScheme.tertiary
            )
            TextoCuerpoLargo(
                text = "No hay mensajes",
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

@Composable
fun Chat(
    modifier: Modifier = Modifier,
    chatState: ChatUiState,
    listState: LazyListState
) {
    when {
        chatState.mensajes.isNotEmpty() -> {
            ListaChat(
                modifier = modifier,
                chatState = chatState,
                listState = listState
            )
        }

        else -> {
            ChatVacio(
                modifier = modifier
            )
        }
    }
}

@Composable
fun BotonAbajo(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.background)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.padding(8.dp),
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = "Ir abajo",
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun InputMensaje(
    modifier: Modifier = Modifier,
    mensajeState: String,
    onCambiaValor: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = mensajeState,
        onValueChange = { text ->
            if (text.length <= 2000) onCambiaValor(text)
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedLeadingIconColor = MaterialTheme.colorScheme.tertiary,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.tertiary,
            focusedTrailingIconColor = MaterialTheme.colorScheme.tertiary,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.tertiary,
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
            focusedBorderColor = MaterialTheme.colorScheme.secondary
        ),
        shape = RoundedCornerShape(16.dp),
        singleLine = true,
        trailingIcon = {
            if (mensajeState.isNotEmpty()) {
                IconButton(
                    onClick = { onCambiaValor("") }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        },
        placeholder = {
            TextoCuerpoLargo(
                text = "Preguntame algo...",
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    )
}

@Composable
fun BotonEnviar(
    modifier: Modifier = Modifier,
    habilitado: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(OutlinedTextFieldDefaults.MinHeight)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primary)
            .clickable(
                enabled = habilitado,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.padding(horizontal = 10.dp),
            imageVector = Icons.AutoMirrored.Filled.Send,
            contentDescription = "Enviar",
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun AccionesChat(
    modifier: Modifier = Modifier,
    estaPensando: Boolean,
    mensajeState: String,
    onCambiarMensaje: (String) -> Unit,
    onEnviarClick: () -> Unit
) {
    val focusManager: FocusManager = LocalFocusManager.current

    Row(
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        InputMensaje(
            modifier = Modifier.weight(1f),
            mensajeState = mensajeState,
            onCambiaValor = onCambiarMensaje
        )
        BotonEnviar(
            habilitado = mensajeState.isNotEmpty() && !estaPensando,
            onClick = {
                onEnviarClick()
                focusManager.clearFocus()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsistenteIAScreen(
    modifier: Modifier = Modifier,
    informacionEstado: InformacionEstadoUiState,
    chatState: ChatUiState,
    onNavigateAtras: () -> Unit,
    onAsistenteIAEvent: (AsistenteIAEvent) -> Unit,
) {
    val listState: LazyListState = rememberLazyListState()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val isScrolled by remember {
        derivedStateOf {
            val lastIndex = chatState.mensajes.lastIndex
            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == lastIndex
        }
    }
    val snackbarHostState = remember { SnackbarHostState() }

    CorrutinaGestionSnackBar(
        snackbarHostState = snackbarHostState,
        informacionEstado = informacionEstado
    )

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            TopBar(
                title = "Asistente IA",
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateAtras
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Atras",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            if (isScrolled) {
                BotonAbajo(
                    onClick = {
                        coroutineScope.launch {
                            listState.animateScrollToItem(chatState.mensajes.lastIndex + 1)
                        }
                    }
                )
            }
        },
        bottomBar = {
            BottomAppBar(
                modifier = modifier,
                tonalElevation = 0.dp,
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                AccionesChat(
                    mensajeState = chatState.mensaje,
                    onCambiarMensaje = {
                        onAsistenteIAEvent(AsistenteIAEvent.OnMensajeCambia(it))
                    },
                    onEnviarClick = {
                        onAsistenteIAEvent(AsistenteIAEvent.OnMensajeEnviado)
                    },
                    estaPensando = chatState.estaPensado
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) {
                SnackbarCommon(informacionEstado = informacionEstado)
            }
        }
    ) { paddingValues ->
        Chat(
            modifier = Modifier.padding(paddingValues),
            chatState = chatState,
            listState = listState
        )
    }
}