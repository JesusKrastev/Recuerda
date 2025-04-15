package com.fernandort.recuerda.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fernandort.recuerda.utilities.manejo_errores.InformacionEstadoUiState

@Composable
fun CorrutinaGestionSnackBar(
    informacionEstado : InformacionEstadoUiState,
    snackbarHostState: SnackbarHostState
) {
    LaunchedEffect(
        key1 = informacionEstado,
        block = {
            when (informacionEstado) {
                is InformacionEstadoUiState.Informacion -> snackbarHostState.showSnackbar(
                    message = informacionEstado.mensaje,
                    duration = SnackbarDuration.Indefinite
                )

                is InformacionEstadoUiState.Error -> snackbarHostState.showSnackbar(
                    message = informacionEstado.mensaje,
                    duration = SnackbarDuration.Indefinite
                )

                is InformacionEstadoUiState.Oculta -> snackbarHostState.currentSnackbarData?.dismiss()
            }
        }
    )
}

@Composable
fun SnackbarError(
    mensajeError: String,
    onDismissError: () -> Unit = {}
) {
    Snackbar(
        modifier = Modifier.padding(bottom = 16.dp),
        containerColor = MaterialTheme.colorScheme.onErrorContainer,
        contentColor = MaterialTheme.colorScheme.onError,
        dismissAction = {
            IconButton(
                onClick = onDismissError,
            ) {
                Icon(
                    imageVector = Icons.Default.Cancel,
                    contentDescription = "Cancelar",
                    modifier = Modifier.size(ButtonDefaults.IconSize),
                )
            }
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                imageVector = Icons.Default.Error,
                contentDescription = "Error",
                modifier = Modifier.size(ButtonDefaults.IconSize),
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = mensajeError)
        }
    }
}

@Composable
fun SnackbarInfo(
    mensajeInfo: String,
    muestraProgreso: Boolean = false,
) {
    Snackbar(
        modifier = Modifier.padding(bottom = 16.dp),
        containerColor = MaterialTheme.colorScheme.onSecondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondary,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Información",
                modifier = Modifier.size(ButtonDefaults.IconSize),
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = mensajeInfo)
            if (muestraProgreso) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onSecondary,
                        trackColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }
    }
}

@Composable
fun  SnackbarCommon(
    informacionEstado: InformacionEstadoUiState
) {
    when(informacionEstado) {
        is InformacionEstadoUiState.Informacion -> {
            SnackbarInfo(
                mensajeInfo = informacionEstado.mensaje,
                muestraProgreso = informacionEstado.muestraProgreso
            )
        }
        is InformacionEstadoUiState.Error -> {
            SnackbarError(
                mensajeError = informacionEstado.mensaje,
                onDismissError = informacionEstado.onDismiss
            )
        }
        is InformacionEstadoUiState.Oculta -> {
            // No hay Snackbar en el SnackbarHost del Scaffold
        }
    }
}


@Preview(
    showBackground = true,
)
@Composable
fun SnackBarErrorPreview() {
    SnackbarError(mensajeError = "Error")
}

@Preview(
    showBackground = true,
)
@Composable
fun SnackBarInfoPreview() {
    SnackbarInfo(mensajeInfo = "Cargado...", muestraProgreso = true)
}