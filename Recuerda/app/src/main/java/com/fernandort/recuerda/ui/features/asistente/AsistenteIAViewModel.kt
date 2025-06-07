package com.fernandort.recuerda.ui.features.asistente

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fernandort.recuerda.ui.features.ChatUiState
import com.fernandort.recuerda.ui.features.MessageUiState
import com.fernandort.recuerda.utilities.manejo_errores.InformacionEstadoUiState
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AsistenteIAViewModel @Inject constructor(
    private val gemini: GenerativeModel,
): ViewModel() {
    var chatState: ChatUiState by mutableStateOf(ChatUiState())
        private set
    var informacionEstado: InformacionEstadoUiState by mutableStateOf(InformacionEstadoUiState.Oculta())
        private set

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
                            mensajeUsuario = mensaje
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
                            mensajeUsuario = mensaje
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
        mensajes: List<MessageUiState>,
        mensajeUsuario: String
    ): String {
        val instruccion: String = "Respond concisely and precisely. Reply in the user's language."
        val mensaje: String = "Prompt: $mensajeUsuario."
        val mensajesPrevios: String? = mensajes.dropLast(1).takeLast(5)
            .takeIf { it.isNotEmpty() }?.joinToString { it.texto }
            ?.let { "Previous messages: $it." }

        return StringBuilder().apply {
            append(instruccion)
            mensajesPrevios?.let { append(mensajesPrevios) }
            append(mensaje)
        }.toString()
    }

    fun onAsistenteIAEvent(event: AsistenteIAEvent) {
        when(event) {
            is AsistenteIAEvent.OnMensajeEnviado -> {
                enviarMensaje(chatState.mensaje)
            }

            is AsistenteIAEvent.OnMensajeCambia -> {
                chatState = chatState.copy(mensaje = event.mensaje)
            }
        }
    }
}