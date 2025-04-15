package com.fernandort.recuerda.ui.features.contactos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fernandort.recuerda.data.ContactosRepository
import com.fernandort.recuerda.utilities.manejo_errores.InformacionEstadoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactosViewModel @Inject constructor(
    private val contactosRepository: ContactosRepository,
    private val validadorContacto: ValidadorContacto
): ViewModel() {
    private var contactosState = mutableStateListOf<ContactoUiState>()
    var validacionContactoState by mutableStateOf(ValidacionContactoUiState())
        private set
    var contactosFiltradoState = mutableStateListOf<ContactoUiState>()
        private set
    var contactoState by mutableStateOf(ContactoUiState())
        private set
    var tipoBottomSheetState by mutableStateOf(BottomSheetType.Oculto)
        private set
    var editando by mutableStateOf(false)
        private set
    var busquedaState by mutableStateOf("")
        private set
    var informacionEstadoState: InformacionEstadoUiState by mutableStateOf(InformacionEstadoUiState.Oculta())
        private set

    init {
        cargarContactos()
    }

    private fun cargarContactos() {
        viewModelScope.launch {
            contactosRepository.get().collect() { contactos ->
                contactosState.clear()
                contactosState.addAll(contactos.map { it.toContactoUiState() }.toMutableStateList())
                contactosFiltradoState.clear()
                contactosFiltradoState.addAll(contactos.map { it.toContactoUiState() }.filtrarPorIdentidad().toMutableStateList())
            }
        }
    }

    private fun List<ContactoUiState>.filtrarPorIdentidad(): List<ContactoUiState> =
        if(busquedaState.isNotEmpty())
            this.filter { "${it.nombre} ${it.apellidos}".contains(busquedaState, ignoreCase = true) }
        else
            this

    private fun guardarContacto() {
        viewModelScope.launch {
            if(editando)
                contactosRepository.update(contacto = contactoState.toContacto())
            else
                contactosRepository.insert(contacto = contactoState.toContacto())
            tipoBottomSheetState = BottomSheetType.Oculto
        }
    }

    fun onContactosEvent(event: ContactosEvent) {
        when (event) {
            is ContactosEvent.OnCrearContacto -> {
                tipoBottomSheetState = BottomSheetType.Crear
                editando = false
            }
            is ContactosEvent.OnEliminarContacto -> {
                viewModelScope.launch {
                    contactosRepository.delete(event.contactoUiState.toContacto())
                }
            }
            is ContactosEvent.OnEditarContacto -> {
                tipoBottomSheetState = BottomSheetType.Editar
                contactoState = event.contactoUiState
                editando = true
            }
            is ContactosEvent.OnLlamarContacto -> {
            }
            is ContactosEvent.OnNombreChange -> {
                contactoState = contactoState.copy(
                    nombre = event.nombre
                )
                validacionContactoState = validacionContactoState.copy(
                    validacionNombre = validadorContacto.validadorNombre.valida(event.nombre)
                )
            }
            is ContactosEvent.OnApellidoChange -> {
                contactoState = contactoState.copy(
                    apellidos = event.apellido
                )
                validacionContactoState = validacionContactoState.copy(
                    validacionApellidos = validadorContacto.validadorApellidos.valida(event.apellido)
                )
            }
            is ContactosEvent.OnTelefonoChange -> {
                contactoState = contactoState.copy(
                    telefono = event.telefono
                )
                validacionContactoState = validacionContactoState.copy(
                    validacionTelefono = validadorContacto.validadorTelefono.valida(event.telefono)
                )
            }
            is ContactosEvent.OnGuardarContacto -> {
                validacionContactoState = validadorContacto.valida(contactoState)
                if(!validacionContactoState.hayError) guardarContacto()
            }
            is ContactosEvent.OnCambiarBusqueda -> {
                busquedaState = event.busqueda
                contactosFiltradoState.clear()
                contactosFiltradoState.addAll(contactosState.filtrarPorIdentidad().toMutableStateList())
            }
            is ContactosEvent.OnCerrarBottomSheet -> {
                contactoState = ContactoUiState()
                validacionContactoState = ValidacionContactoUiState()
                tipoBottomSheetState = BottomSheetType.Oculto
            }
        }
    }
}