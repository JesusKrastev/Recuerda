package com.fernandort.recuerda.ui.features.juegos.bot

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fernandort.recuerda.data.JuegosRepository
import com.fernandort.recuerda.ui.features.ChatUiState
import com.fernandort.recuerda.ui.features.MessageUiState
import com.fernandort.recuerda.ui.features.asistente.AsistenteIAEvent
import com.fernandort.recuerda.ui.features.juegos.JuegoUiState
import com.fernandort.recuerda.ui.features.juegos.toJuegoUiState
import com.fernandort.recuerda.utilities.manejo_errores.InformacionEstadoUiState
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BotViewModel @Inject constructor(
    private val gemini: GenerativeModel,
    private val juegosRepository: JuegosRepository,
): ViewModel() {
    var chatState: ChatUiState by mutableStateOf(ChatUiState())
        private set
    var juegoState: JuegoUiState by mutableStateOf(JuegoUiState())
        private set
    var informacionEstado: InformacionEstadoUiState by mutableStateOf(InformacionEstadoUiState.Oculta())
        private set

    fun establecerJuegoState(juegoId: String) {
        juegoState = juegosRepository.getById(id = juegoId)?.toJuegoUiState() ?: JuegoUiState()
        viewModelScope.launch {
            obtenerRespuesta(
                mensaje =
                    crearConsulta(
                        juego = juegoState,
                        mensajes = emptyList(),
                        mensajeUsuario = "Quiero que empieces tú el juego. Y no digas que lo has comprendido ni nada."
                    )
            )
        }
    }

    private fun anyadirMensaje(
        mensaje: String
    ) {
        chatState = chatState.copy(
            mensajes = chatState.mensajes + MessageUiState(
                texto = mensaje,
                esDelUsuario = true
            ),
            mensaje = "",
            estaPensado = true
        )
    }

    private fun anyadirRespuesta(
        respuesta: String
    ) {
        chatState = chatState.copy(
            mensajes = chatState.mensajes + MessageUiState(
                texto = respuesta,
                esDelUsuario = false
            ),
            estaPensado = false
        )
    }

    private suspend fun obtenerRespuesta(
        mensaje: String
    ) = withContext(Dispatchers.IO) {
        gemini
            .generateContent(
                content {
                    val newPrompt =
                        crearConsulta(
                            mensajes = chatState.mensajes,
                            mensajeUsuario = mensaje,
                            juego = juegoState
                        )
                    text(newPrompt)
                }
            )
            .text?.let { respuesta ->
                anyadirRespuesta(respuesta)
            }
    }

    private fun enviarMensaje(
        mensaje: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                anyadirMensaje(mensaje)
                obtenerRespuesta(
                    mensaje =
                        crearConsulta(
                            mensajes = chatState.mensajes,
                            mensajeUsuario = mensaje,
                            juego = juegoState
                        )
                )
            } catch (e: Exception) {
                chatState = chatState.copy(
                    estaPensado = false
                )
                informacionEstado = InformacionEstadoUiState.Error(
                    onDismiss = { informacionEstado = InformacionEstadoUiState.Oculta() },
                    mensaje = "Error al enviar el mensaje."
                )
            }
        }
    }

    private fun crearConsulta(
        juego: JuegoUiState,
        mensajes: List<MessageUiState>,
        mensajeUsuario: String
    ): String {
        val rol: String = "Rol: ${juego.instrucciones}."
        val instruccion: String = "Respond concisely and precisely. Reply in the user's language."
        val mensaje: String = "Prompt: $mensajeUsuario."
        val mensajesPrevios: String? = mensajes.dropLast(1).takeLast(5)
            .takeIf { it.isNotEmpty() }?.joinToString { it.texto }
            ?.let { "Previous messages: $it." }

        return StringBuilder().apply {
            append(rol)
            append(instruccion)
            mensajesPrevios?.let { append(mensajesPrevios) }
            append(mensaje)
        }.toString()
    }

    fun onBotEvent(event: BotEvent) {
        when(event) {
            is BotEvent.OnMensajeEnviado -> {
                enviarMensaje(chatState.mensaje)
            }

            is BotEvent.OnMensajeCambia -> {
                chatState = chatState.copy(mensaje = event.mensaje)
            }
        }
    }
}