package com.fernandort.recuerda.ui.features

data class ChatUiState(
    val mensajes: List<MessageUiState> = mutableListOf(),
    var mensaje: String = "",
    val estaPensado: Boolean = false
)