package com.fernandort.recuerda.ui.features.juegos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.fernandort.recuerda.data.JuegosRepository
import com.fernandort.recuerda.utilities.manejo_errores.InformacionEstadoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JuegosViewModel @Inject constructor(
    private val juegosRepository: JuegosRepository
): ViewModel() {
    var juegosState: List<JuegoUiState> by mutableStateOf(emptyList())
        private set
    var informacionEstadoState: InformacionEstadoUiState by mutableStateOf(InformacionEstadoUiState.Oculta())
        private set

    init {
        cargarJuegos()
    }

    fun cargarJuegos() {
        juegosState = juegosRepository.get().map { it.toJuegoUiState() }
    }
}