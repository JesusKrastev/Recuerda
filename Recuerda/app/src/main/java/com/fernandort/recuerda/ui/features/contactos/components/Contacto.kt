package com.fernandort.recuerda.ui.features.contactos.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fernandort.recuerda.ui.composables.TextoCuerpoLargo
import com.fernandort.recuerda.ui.features.contactos.ContactoUiState
import com.fernandort.recuerda.ui.theme.RecuerdaTheme

@Composable
fun ImageContacto(
    modifier: Modifier = Modifier,
    nombre: String,
    apellido: String
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        TextoCuerpoLargo(
            modifier = Modifier.padding(16.dp),
            text = "$nombre $apellido"
                .split(" ")
                .filter { it.isNotBlank() }
                .joinToString("") { it.first().uppercase() },
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Composable
fun IdentidadContacto(
    modifier: Modifier = Modifier,
    nombre: String,
    apellido: String
) {
    TextoCuerpoLargo(
        modifier = modifier,
        fontWeight = FontWeight.Medium,
        text = "$nombre $apellido"
    )
}

@Composable
fun TelefonoContacto(
    modifier: Modifier = Modifier,
    telefono: String
) {
    TextoCuerpoLargo(
        modifier = modifier,
        color = MaterialTheme.colorScheme.tertiary,
        text = telefono
    )
}

@Composable
fun BotonLlamar(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Outlined.Call,
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = "Llamar"
        )
    }
}

@Composable
fun InformacionContacto(
    modifier: Modifier = Modifier,
    nombre: String,
    apellido: String,
    telefono: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        IdentidadContacto(
            nombre = nombre,
            apellido = apellido
        )
        TelefonoContacto(
            telefono = telefono
        )
    }
}

@Composable
fun Contacto(
    modifier: Modifier = Modifier,
    contacto: ContactoUiState,
    onLlamar: () -> Unit,
    onEditar: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { onEditar() }
                )
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ImageContacto(
            nombre = contacto.nombre,
            apellido = contacto.apellidos
        )
        InformacionContacto(
            nombre = contacto.nombre,
            telefono = contacto.telefono,
            apellido = contacto.apellidos
        )
        Box(
            modifier = Modifier.weight(1f)
        )
        BotonLlamar(
            onClick = onLlamar
        )
    }
}

@Preview
@Composable
fun ContactoPreview() {
    RecuerdaTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(
                modifier = Modifier.padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Contacto(
                    contacto = ContactoUiState(
                        id = "1",
                        nombre = "Juan Perez",
                        telefono = "5555555555"
                    ),
                    onLlamar = {},
                    onEditar = {}
                )
            }
        }
    }
}