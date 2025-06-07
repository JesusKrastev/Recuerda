package com.fernandort.recuerda.ui.features.asistente

interface AsistenteIAEvent {
    data object OnMensajeEnviado: AsistenteIAEvent
    data class OnMensajeCambia(val mensaje: String): AsistenteIAEvent
}