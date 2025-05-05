package com.fernandort.recuerda.ui.features.notas.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fernandort.recuerda.ui.composables.TextoCuerpoLargo
import com.fernandort.recuerda.ui.composables.TextoCuerpoMedio
import com.fernandort.recuerda.ui.features.notas.NotaUiState
import com.fernandort.recuerda.ui.theme.RecuerdaTheme
import java.time.LocalDateTime

@Composable
fun Nota(
    modifier: Modifier = Modifier,
    notaState: NotaUiState,
    onClickOpciones: () -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextoCuerpoLargo(
                text = notaState.titulo,
                fontWeight = FontWeight.Bold
            )
            TextoCuerpoMedio(
                text = notaState.descripcion,
                color = MaterialTheme.colorScheme.tertiary
            )
            TextoCuerpoMedio(
                text = "%02d:%02d".format(notaState.fecha.hour, notaState.fecha.minute),
                color = MaterialTheme.colorScheme.tertiary
            )
        }
        Icon(
            modifier = Modifier.clickable(onClick = onClickOpciones),
            imageVector = Icons.Filled.MoreHoriz,
            contentDescription = "Opciones",
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview
@Composable
fun NotaPreview() {
    RecuerdaTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(
                modifier = Modifier.padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Nota(
                    notaState = NotaUiState(
                        id = "1",
                        titulo = "Nota de prueba",
                        descripcion = "Esta es una nota de prueba",
                        fecha = LocalDateTime.now()
                    ),
                    onClick = {},
                    onClickOpciones = {}
                )
            }
        }
    }
}