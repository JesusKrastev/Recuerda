package com.fernandort.recuerda.ui.features.juegos.bot

interface BotEvent {
    data object OnMensajeEnviado: BotEvent
    data class OnMensajeCambia(val mensaje: String): BotEvent
}