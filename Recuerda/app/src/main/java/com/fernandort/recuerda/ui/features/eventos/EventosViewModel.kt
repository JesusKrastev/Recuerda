package com.fernandort.recuerda.ui.features.eventos

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fernandort.recuerda.R
import com.fernandort.recuerda.data.EventosRepository
import com.tweener.alarmee.Alarmee
import com.tweener.alarmee.AlarmeeSchedulerAndroid
import com.tweener.alarmee.AndroidNotificationConfiguration
import com.tweener.alarmee.channel.AlarmeeNotificationChannel
import com.tweener.alarmee.configuration.AlarmeeAndroidPlatformConfiguration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import java.time.Month
import javax.inject.Inject

@HiltViewModel
class EventosViewModel @Inject constructor(
    private val eventosRepository: EventosRepository,
    private val validadorEvento: ValidadorEvento
) : ViewModel() {
    var eventosState = mutableStateListOf<EventoUiState>()
        private set
    var eventoState by mutableStateOf(EventoUiState())
        private set
    var tipoBottomSheetState by mutableStateOf(BottomSheetType.Oculto)
        private set
    var editando by mutableStateOf(false)
        private set
    var mostrarSelectorFechaState by mutableStateOf(false)
        private set
    var mostrarSelectorHoraState by mutableStateOf(false)
        private set
    var validacionEventoState by mutableStateOf(ValidacionEventoUiState())
        private set

    init {
        cargarEventos()
    }

    private fun cargarEventos() {
        viewModelScope.launch {
            eventosRepository.get().collect { eventos ->
                eventosState.clear()
                eventosState.addAll(eventos.map { it.toEventoUiState() })
            }
        }
    }

    private fun cancelarRecordatorio(
        evento: EventoUiState,
        context: Context,
    ) {
        AlarmeeSchedulerAndroid(
            context = context,
            configuration = AlarmeeAndroidPlatformConfiguration(
                notificationIconResId = R.drawable.ic_logo,
                notificationIconColor = Color.Transparent,
                notificationChannels = listOf(
                    AlarmeeNotificationChannel(
                        id = "recordatoriosCanalId",
                        name = "Recordatorios",
                    )
                )
            )
        ).cancel(evento.id)
    }

    private fun crearRecordatorio(
        evento: EventoUiState,
        context: Context,
    ) {
        AlarmeeSchedulerAndroid(
            context = context,
            configuration = AlarmeeAndroidPlatformConfiguration(
                notificationIconResId = R.drawable.ic_logo,
                notificationIconColor = Color.Transparent,
                notificationChannels = listOf(
                    AlarmeeNotificationChannel(
                        id = "recordatoriosCanalId",
                        name = "Recordatorios",
                    )
                )
            )
        ).schedule(
            alarmee = Alarmee(
                uuid = evento.id,
                timeZone = kotlinx.datetime.TimeZone.of("Europe/Madrid"),
                notificationTitle = evento.titulo,
                notificationBody = evento.descripcion,
                scheduledDateTime = LocalDateTime(
                    year = evento.fecha.year,
                    month = evento.fecha.month,
                    dayOfMonth = evento.fecha.dayOfMonth,
                    hour = evento.hora.hour,
                    minute = evento.hora.minute,
                ),
                androidNotificationConfiguration = AndroidNotificationConfiguration(
                    channelId = "recordatoriosCanalId",
                ),
            )
        )
    }

    fun onEventosEvent(event: EventosEvent) {
        when (event) {
            is EventosEvent.OnCambiarDescripcion -> {
                eventoState = eventoState.copy(
                    descripcion = event.descripcion
                )
                validacionEventoState = validacionEventoState.copy(
                    validacionDescripcion = validadorEvento.validadorDescripcion.valida(event.descripcion)
                )
            }
            is EventosEvent.OnCambiarTitulo -> {
                eventoState = eventoState.copy(
                    titulo = event.titulo
                )
                validacionEventoState = validacionEventoState.copy(
                    validacionTitulo = validadorEvento.validadorTitulo.valida(event.titulo)
                )
            }
            is EventosEvent.OnCambiarFecha -> {
                eventoState = eventoState.copy(
                    fecha = event.fecha
                )
            }
            EventosEvent.OnCerrarBottomSheet -> {
                tipoBottomSheetState = BottomSheetType.Oculto
                eventoState = EventoUiState()
            }
            EventosEvent.OnCrearEvento -> {
                eventoState = EventoUiState()
                validacionEventoState = ValidacionEventoUiState()
                tipoBottomSheetState = BottomSheetType.Crear
                editando = false
            }
            is EventosEvent.OnEditarEvento -> {
                tipoBottomSheetState = BottomSheetType.Editar
                eventoState = event.eventoState
                editando = true
            }
            is EventosEvent.OnEliminarEvento -> {
                val evento = event.evento

                viewModelScope.launch {
                    cancelarRecordatorio(
                        evento = evento,
                        context = event.context
                    )
                    eventosRepository.delete(evento.toEvento())
                }
            }
            is EventosEvent.OnGuardarEvento -> {
                validacionEventoState = validadorEvento.valida(eventoState)
                if(!validacionEventoState.hayError) {
                    viewModelScope.launch {
                        if(editando) {
                            cancelarRecordatorio(
                                evento = eventoState,
                                context = event.context
                            )
                            crearRecordatorio(
                                context = event.context,
                                evento = eventoState
                            )
                            eventosRepository.update(eventoState.toEvento())
                        } else {
                            crearRecordatorio(
                                context = event.context,
                                evento = eventoState
                            )
                            eventosRepository.insert(eventoState.toEvento())
                        }
                    }
                    tipoBottomSheetState = BottomSheetType.Oculto
                }
            }
            EventosEvent.OnMostrarSelectorFecha -> {
                mostrarSelectorFechaState = true
            }
            EventosEvent.OnOcultarSelectorFecha -> {
                mostrarSelectorFechaState = false
            }
            EventosEvent.OnMostrarSelectorHora -> {
                mostrarSelectorHoraState = true
            }
            EventosEvent.OnOcultarSelectorHora -> {
                mostrarSelectorHoraState = false
            }
            is EventosEvent.OnCambiarHora -> {
                eventoState = eventoState.copy(
                    hora = event.hora
                )
            }
            is EventosEvent.OnCompletarEvento -> {
                viewModelScope.launch {
                    val evento = event.evento
                    val nuevoEvento = evento.copy(
                        completado = event.completado
                    )
                    eventosRepository.update(nuevoEvento.toEvento())
                }
            }
        }
    }
}