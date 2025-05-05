package com.fernandort.recuerda.ui.features.formnota

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fernandort.recuerda.data.NotasRepository
import com.fernandort.recuerda.ui.features.notas.NotaUiState
import com.fernandort.recuerda.ui.features.notas.toNota
import com.fernandort.recuerda.ui.features.notas.toNotaUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormNotaViewModel @Inject constructor(
    private val notasRepository: NotasRepository
): ViewModel() {
    var notaState by mutableStateOf(NotaUiState())
        private set
    var editando by mutableStateOf(false)
        private set

    fun setNotaState(id: String) {
        viewModelScope.launch {
            editando = true
            notaState = notasRepository.get(id).toNotaUiState()
        }
    }

    fun onFormNotaEvent(event: FormNotaEvent) {
        when(event) {
            is FormNotaEvent.OnCambiarTitulo -> {
                notaState = notaState.copy(titulo = event.titulo)
            }
            is FormNotaEvent.OnCambiarDescripcion -> {
                notaState = notaState.copy(descripcion = event.descripcion)
            }
            is FormNotaEvent.OnGuardarNota -> {
                viewModelScope.launch {
                    if(editando)
                        notasRepository.update(notaState.toNota())
                    else
                        notasRepository.insert(notaState.toNota())
                    event.onNavigateAtras()
                }
            }
            is FormNotaEvent.OnEliminarNota -> {
                viewModelScope.launch {
                    notasRepository.delete(notaState.toNota())
                    event.onNavigateAtras()
                }
            }
        }
    }
}