package com.fernandort.recuerda.ui.features.notas

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fernandort.recuerda.data.NotasRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotasViewModel @Inject constructor(
    private val notasRepository: NotasRepository
): ViewModel() {
    var notasState = mutableStateListOf<NotaUiState>()
        private set

    init {
        cargarNotas()
    }

    private fun cargarNotas() {
        viewModelScope.launch {
            notasRepository.get().collect { notas ->
                notasState.clear()
                notasState.addAll(notas.map { it.toNotaUiState() })
            }
        }
    }

    fun onNotasEvent(event: NotasEvent) {
        when(event) {
            is NotasEvent.OnEliminarNota -> {
                viewModelScope.launch {
                    notasRepository.delete(event.nota.toNota())
                }
            }
        }
    }
}