package com.fernandort.recuerda.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import com.fernandort.recuerda.ui.features.contactos.ContactosScreen
import com.fernandort.recuerda.ui.features.contactos.ContactosViewModel
import com.fernandort.recuerda.ui.theme.RecuerdaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val vm = hiltViewModel<ContactosViewModel>()

            RecuerdaTheme {
                ContactosScreen(
                    contactosState = vm.contactosFiltradoState,
                    onContactosEvent = vm::onContactosEvent,
                    tipoBottomSheetState = vm.tipoBottomSheetState,
                    contactoState = vm.contactoState,
                    buscadorState = vm.busquedaState,
                    validacionContactoState = vm.validacionContactoState,
                    informacionEstado = vm.informacionEstadoState
                )
            }
        }
    }
}