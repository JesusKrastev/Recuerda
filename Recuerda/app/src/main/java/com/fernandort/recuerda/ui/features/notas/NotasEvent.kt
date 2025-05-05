package com.fernandort.recuerda.ui.features.notas

interface NotasEvent {
    data class OnEliminarNota(val nota: NotaUiState): NotasEvent
}