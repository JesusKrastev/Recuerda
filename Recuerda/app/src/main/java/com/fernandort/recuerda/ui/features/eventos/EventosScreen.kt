package com.fernandort.recuerda.ui.features.eventos

import android.Manifest
import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fernandort.recuerda.R
import com.fernandort.recuerda.ui.composables.OutlinedTextFieldWithErrorState
import com.fernandort.recuerda.ui.composables.SnackbarCommon
import com.fernandort.recuerda.ui.composables.TextoCuerpoLargo
import com.fernandort.recuerda.ui.composables.TextoTituloLargo
import com.fernandort.recuerda.ui.features.components.Boton
import com.fernandort.recuerda.ui.features.components.NavBar
import com.fernandort.recuerda.ui.features.eventos.components.Evento
import com.fernandort.recuerda.utilities.dispositivo.notificationPermissionLauncher
import com.fernandort.recuerda.utilities.manejo_errores.InformacionEstadoUiState
import com.fernandort.recuerda.utilities.validacion.Validacion
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.ExperimentalCalendarApi
import com.tweener.alarmee.Alarmee
import com.tweener.alarmee.AlarmeeScheduler
import com.tweener.alarmee.AlarmeeSchedulerAndroid
import com.tweener.alarmee.AndroidNotificationConfiguration
import com.tweener.alarmee.AndroidNotificationPriority
import com.tweener.alarmee.RepeatInterval
import com.tweener.alarmee.channel.AlarmeeNotificationChannel
import com.tweener.alarmee.configuration.AlarmeeAndroidPlatformConfiguration
import kotlinx.datetime.LocalDateTime
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.Month
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import kotlin.Int

@Composable
fun EventoItem(
    modifier: Modifier = Modifier,
    evento: EventoUiState,
    onEditar: () -> Unit,
    onEliminar: () -> Unit,
    onCompletar: (Boolean) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
    ) {
        EventoDropDownMenu(
            onEditar = onEditar,
            onEliminar = onEliminar,
            expanded = expanded,
            onDismissRequest = { expanded = false }
        )
        Evento(
            evento = evento,
            onClickOpciones = { expanded = true },
            onCompletar = onCompletar
        )
    }
}

@Composable
fun ListaEventos(
    modifier: Modifier = Modifier,
    eventosState: List<EventoUiState>,
    onEditar: (EventoUiState) -> Unit,
    onEliminar: (EventoUiState) -> Unit,
    onCompletar: (EventoUiState, Boolean) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(
            items = eventosState,
            key = { it.id }
        ) { evento ->
            EventoItem(
                evento = evento,
                onEditar = { onEditar(evento) },
                onEliminar = { onEliminar(evento) },
                onCompletar = { onCompletar(evento, it) }
            )
        }
    }
}

@Composable
fun EventosVacio(
    modifier: Modifier = Modifier,
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
                imageVector = Icons.Filled.Celebration,
                contentDescription = "Sin eventos",
                tint = MaterialTheme.colorScheme.tertiary
            )
            TextoCuerpoLargo(
                text = "No hay eventos",
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

@Composable
fun Dia(
    modifier: Modifier = Modifier,
    dia: CalendarDay,
    tieneEvento: Boolean
) {
    Column(
        modifier = modifier
            .aspectRatio(1f),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = dia.date.dayOfMonth.toString())
        if(tieneEvento)
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            )
    }
}

@Composable
fun DiasSemana(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("es", "ES")).replaceFirstChar { it.titlecase() },
            )
        }
    }
}

@Composable
fun Mes(
    modifier: Modifier = Modifier,
    mes: CalendarMonth
) {
    val locale = Locale("es", "ES")
    val nombreMes = mes.yearMonth.month.getDisplayName(TextStyle.FULL, locale)
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale) else it.toString() }
    val anyo = mes.yearMonth.year

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextoTituloLargo(
            modifier = modifier,
            text = "$nombreMes $anyo",
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Composable
fun Calendario(
    modifier: Modifier = Modifier,
    state: CalendarState,
    eventosState: List<EventoUiState>,
) {
    HorizontalCalendar(
        modifier = modifier,
        state = state,
        dayContent = { dia ->
            Dia(
                dia = dia,
                tieneEvento = eventosState.any { it.fecha == dia.date }
            )
        },
        monthHeader = { mes ->
            val daysOfWeek = mes.weekDays.first().map { it.date.dayOfWeek }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Mes(mes = mes)
                DiasSemana(daysOfWeek = daysOfWeek)
            }
        }
    )
}

@Composable
fun ContenidoEventos(
    modifier: Modifier = Modifier,
    eventosState: List<EventoUiState>,
    onEventosEvent: (EventosEvent) -> Unit,
) {
    val context = LocalContext.current

    when {
        eventosState.isNotEmpty() ->
            ListaEventos(
                modifier = modifier,
                eventosState = eventosState,
                onEditar = {
                    onEventosEvent(EventosEvent.OnEditarEvento(it))
                },
                onEliminar = {
                    onEventosEvent(EventosEvent.OnEliminarEvento(evento = it, context = context))
                },
                onCompletar = { evento, completado ->
                    onEventosEvent(EventosEvent.OnCompletarEvento(evento = evento, completado = completado))
                }
            )
        else ->
            EventosVacio(
                modifier = modifier
            )
    }
}

@OptIn(ExperimentalCalendarApi::class)
@Composable
fun PanelEventos(
    modifier: Modifier = Modifier,
    eventosState: List<EventoUiState>,
    onEventosEvent: (EventosEvent) -> Unit,
) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) }
    val endMonth = remember { currentMonth.plusMonths(100) }
    val firstDayOfWeek = remember { DayOfWeek.MONDAY }
    val calendarState = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek
    )
    val eventos by remember {
        derivedStateOf { eventosState.filter { it.fecha.month == calendarState.firstVisibleMonth.yearMonth.month } }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Calendario(
            state = calendarState,
            eventosState = eventosState,
        )
        ContenidoEventos(
            eventosState = eventos,
            onEventosEvent = onEventosEvent
        )
    }
}

@Composable
fun Eventos(
    modifier: Modifier = Modifier,
    eventosState: List<EventoUiState>,
    onEventosEvent: (EventosEvent) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        when {
            eventosState.isNotEmpty() ->
                PanelEventos(
                    eventosState = eventosState,
                    onEventosEvent = onEventosEvent
                )

            else ->
                EventosVacio(
                    modifier = Modifier
                )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventosBottomSheet(
    modifier: Modifier = Modifier,
    eventoState: EventoUiState,
    validacionEventoState: ValidacionEventoUiState,
    editando: Boolean,
    mostrarSelectorHoraState: Boolean,
    mostrarSelectorFechaState: Boolean,
    onEventosEvent: (EventosEvent) -> Unit,
    onDismissRequest: () -> Unit,
    sheetState: SheetState
) {
    ModalBottomSheet(
        modifier = modifier,
        tonalElevation = 0.dp,
        containerColor = MaterialTheme.colorScheme.background,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {
        EventoBottomSheet(
            eventoState = eventoState,
            onEventosEvent = onEventosEvent,
            mostrarSelectorFechaState = mostrarSelectorFechaState,
            mostrarSelectorHoraState = mostrarSelectorHoraState,
            editando = editando,
            validacionEventoState = validacionEventoState,
        )
    }
}

@Composable
fun BotonGuardar(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Boton(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        text = "Guardar"
    )
}

@Composable
fun CampoTitulo(
    modifier: Modifier = Modifier,
    nombreState: String,
    validacionState: Validacion,
    onCambiarValor: (String) -> Unit
) {
    OutlinedTextFieldWithErrorState(
        modifier = modifier.fillMaxWidth(),
        textState = nombreState,
        hintText = "Nombre",
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Edit,
                tint = MaterialTheme.colorScheme.tertiary,
                contentDescription = "Nombre"
            )
        },
        validacionState = validacionState,
        onValueChange = onCambiarValor
    )
}

@Composable
fun CampoDescripcion(
    modifier: Modifier = Modifier,
    descripcionState: String,
    validacionState: Validacion,
    onCambiarValor: (String) -> Unit
) {
    OutlinedTextFieldWithErrorState(
        modifier = modifier.fillMaxWidth(),
        textState = descripcionState,
        hintText = "Descripción",
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Edit,
                tint = MaterialTheme.colorScheme.tertiary,
                contentDescription = "Descripción"
            )
        },
        singleLine = false,
        validacionState = validacionState,
        onValueChange = onCambiarValor
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampoFecha(
    modifier: Modifier = Modifier,
    fechaState: LocalDate,
    validacionState: Validacion,
    mostrarSelectorFechaState: Boolean,
    onOcultarSelectorFecha: () -> Unit,
    onMostrarSelectorFecha: () -> Unit,
    onCambiarValor: (LocalDate) -> Unit
) {
    val formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val fechaFormateada = fechaState.format(formatoFecha)
    val selectorFechaState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis(),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis >= LocalDate.now()
                    .atStartOfDay(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli()
            }
        }
    )
    selectorFechaState.selectedDateMillis?.let { millis ->
        onCambiarValor(millis.toLocalDate() ?: LocalDate.now().plusDays(1))
    }

    if (mostrarSelectorFechaState) {
        SelectorFecha(
            onOcultar = onOcultarSelectorFecha,
            selectorFechaState = selectorFechaState
        )
    }
    OutlinedTextFieldWithErrorState(
        modifier = modifier.fillMaxWidth(),
        textState = fechaFormateada,
        hintText = "Fecha",
        trailingIcon = {
            IconButton(
                onClick = onMostrarSelectorFecha
            ) {
                Icon(
                    imageVector = Icons.Outlined.CalendarMonth,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "Fecha"
                )
            }
        },
        validacionState = validacionState,
        readOnly = true,
        onValueChange = {},
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampoHora(
    modifier: Modifier = Modifier,
    horaState: LocalTime,
    mostrarSelectorHoraState: Boolean,
    validacionState: Validacion,
    onOcultarSelectorHora: () -> Unit,
    onMostrarSelectorHora: () -> Unit,
    onCambiarValor: (LocalTime) -> Unit
) {
    val formatoHora = DateTimeFormatter.ofPattern("HH:mm")
    val horaFormateada = horaState.format(formatoHora)
    val selectorHoraState = rememberTimePickerState()

    if(mostrarSelectorHoraState) {
        SelectorHora(
            onOcultar = onOcultarSelectorHora,
            selectorHoraState = selectorHoraState,
            onCambiarValor = onCambiarValor
        )
    }
    OutlinedTextFieldWithErrorState(
        modifier = modifier.fillMaxWidth(),
        textState = horaFormateada,
        hintText = "Hora",
        trailingIcon = {
            IconButton(
                onClick = onMostrarSelectorHora
            ) {
                Icon(
                    imageVector = Icons.Outlined.Schedule,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "Hora"
                )
            }
        },
        validacionState = validacionState,
        readOnly = true,
        onValueChange = {}
    )
}

@Composable
fun EventoBottomSheet(
    modifier: Modifier = Modifier,
    editando: Boolean,
    validacionEventoState: ValidacionEventoUiState,
    mostrarSelectorHoraState: Boolean,
    mostrarSelectorFechaState: Boolean,
    eventoState: EventoUiState,
    onEventosEvent: (EventosEvent) -> Unit,
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextoTituloLargo(
            text = if (editando) "Editar Evento" else "Crear evento",
            color = MaterialTheme.colorScheme.onSecondary,
            fontWeight = FontWeight.SemiBold
        )
        Column {
            CampoTitulo(
                nombreState = eventoState.titulo,
                validacionState = validacionEventoState.validacionTitulo,
                onCambiarValor = {
                    onEventosEvent(EventosEvent.OnCambiarTitulo(it))
                }
            )
            CampoDescripcion(
                descripcionState = eventoState.descripcion,
                validacionState = validacionEventoState.validacionDescripcion,
                onCambiarValor = {
                    onEventosEvent(EventosEvent.OnCambiarDescripcion(it))
                }
            )
            CampoFecha(
                fechaState = eventoState.fecha,
                validacionState = object : Validacion {},
                onMostrarSelectorFecha = {
                    onEventosEvent(EventosEvent.OnMostrarSelectorFecha)
                },
                onOcultarSelectorFecha = {
                    onEventosEvent(EventosEvent.OnOcultarSelectorFecha)
                },
                onCambiarValor = {
                    onEventosEvent(EventosEvent.OnCambiarFecha(it))
                },
                mostrarSelectorFechaState = mostrarSelectorFechaState,
            )
            CampoHora(
                horaState = eventoState.hora,
                validacionState = object : Validacion {},
                onCambiarValor = {
                    onEventosEvent(EventosEvent.OnCambiarHora(it))
                },
                onMostrarSelectorHora = {
                    onEventosEvent(EventosEvent.OnMostrarSelectorHora)
                },
                onOcultarSelectorHora = {
                    onEventosEvent(EventosEvent.OnOcultarSelectorHora)
                },
                mostrarSelectorHoraState = mostrarSelectorHoraState,
            )
        }
        BotonGuardar(
            onClick = {
                onEventosEvent(EventosEvent.OnGuardarEvento(context))
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorHora(
    modifier: Modifier = Modifier,
    onOcultar: () -> Unit,
    selectorHoraState: TimePickerState,
    onCambiarValor: (LocalTime) -> Unit
) {
    DatePickerDialog(
        modifier = modifier,
        onDismissRequest = onOcultar,
        confirmButton = {
            Button(
                onClick = {
                    onCambiarValor(LocalTime.of(selectorHoraState.hour, selectorHoraState.minute))
                    onOcultar()
                }
            ) {
                TextoCuerpoLargo(
                    text = "Seleccionar",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        dismissButton = {
            OutlinedButton(
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary
                ),
                onClick = onOcultar
            ) {
                TextoCuerpoLargo(
                    text = "Cancelar",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    ) {
        TimePicker(
            modifier = Modifier.padding(16.dp),
            state = selectorHoraState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorFecha(
    modifier: Modifier = Modifier,
    onOcultar: () -> Unit,
    selectorFechaState: DatePickerState
) {
    DatePickerDialog(
        modifier = modifier,
        onDismissRequest = onOcultar,
        confirmButton = {
            Button(
                onClick = {
                    onOcultar()
                }
            ) {
                TextoCuerpoLargo(
                    text = "Seleccionar",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        dismissButton = {
            OutlinedButton(
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary
                ),
                onClick = onOcultar
            ) {
                TextoCuerpoLargo(
                    text = "Cancelar",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    ) {
        DatePicker(
            state = selectorFechaState,
            showModeToggle = true
        )
    }
}

fun Long?.toLocalDate(): LocalDate? {
    return this?.let { millis ->
        Instant.ofEpochMilli(millis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }
}

@Composable
fun EventoDropDownMenu(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onEditar: () -> Unit,
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
            icon = Icons.Outlined.Edit,
            text = "Editar",
            onClick = onEditar,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventosScreen(
    modifier: Modifier = Modifier,
    onNavigateToContactos: () -> Unit,
    editando: Boolean,
    mostrarSelectorFechaState: Boolean,
    mostrarSelectorHoraState: Boolean,
    onNavigateToJuegos: () -> Unit,
    validacionEventoState: ValidacionEventoUiState,
    onNavigateToNotas: () -> Unit,
    onNavigateToAsistenteIA: () -> Unit,
    informacionEstado: InformacionEstadoUiState,
    eventosState: List<EventoUiState>,
    onEventosEvent: (EventosEvent) -> Unit,
    eventoState: EventoUiState,
    tipoBottomSheetState: BottomSheetType,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val bottomSheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val notificationPermissionLauncher = notificationPermissionLauncher()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        bottomBar = {
            NavBar(
                selectedPage = 0,
                onNavigateToEventos = { },
                onNavigateToNotas = onNavigateToNotas,
                onNavigateToContactos = onNavigateToContactos,
                onNavigateToAsistenteIA = onNavigateToAsistenteIA,
                onNavigateToJuegos = onNavigateToJuegos,
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    onEventosEvent(EventosEvent.OnCrearEvento)
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Añadir evento"
                    )
                    TextoCuerpoLargo(
                        text = "Añadir evento",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) {
                SnackbarCommon(informacionEstado = informacionEstado)
            }
        },
    ) { paddingValues ->
        Eventos(
            modifier = Modifier.padding(paddingValues),
            eventosState = eventosState,
            onEventosEvent = onEventosEvent
        )
        if (tipoBottomSheetState != BottomSheetType.Oculto) {
            EventosBottomSheet(
                eventoState = eventoState,
                onDismissRequest = {
                    onEventosEvent(EventosEvent.OnCerrarBottomSheet)
                },
                sheetState = bottomSheetState,
                editando = editando,
                onEventosEvent = onEventosEvent,
                mostrarSelectorFechaState = mostrarSelectorFechaState,
                validacionEventoState = validacionEventoState,
                mostrarSelectorHoraState = mostrarSelectorHoraState,
            )
        }
    }
}