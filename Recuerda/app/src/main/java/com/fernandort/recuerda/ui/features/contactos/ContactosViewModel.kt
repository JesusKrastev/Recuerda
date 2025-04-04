package com.fernandort.recuerda.ui.features.contactos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fernandort.recuerda.data.ContactosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactosViewModel @Inject constructor(
    private val contactosRepository: ContactosRepository
): ViewModel() {
    var contactosState = mutableStateListOf<ContactoUiState>()
        private set

    init {
        cargarContactos()
    }

    private fun cargarContactos() {
        viewModelScope.launch {
            contactosState = contactosRepository.get().map { contacto -> contacto.toContactoUiState() }.toMutableStateList()
        }
    }
}