package com.fernandort.recuerda.ui.features.eventos.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fernandort.recuerda.ui.composables.TextoCuerpoLargo
import com.fernandort.recuerda.ui.composables.TextoCuerpoMedio
import com.fernandort.recuerda.ui.features.eventos.EventoUiState
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun Evento(
    modifier: Modifier = Modifier,
    evento: EventoUiState,
    onClickOpciones: () -> Unit,
    onCompletar: (Boolean) -> Unit,
) {
    val formatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", Locale("es", "ES"))

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            modifier = Modifier,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                uncheckedColor = MaterialTheme.colorScheme.onBackground
            ),
            checked = evento.completado,
            onCheckedChange = onCompletar
        )
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp),
                horizontalAlignment = Alignment.Start
            ) {
                TextoCuerpoLargo(
                    text = evento.titulo,
                    fontWeight = FontWeight.Bold,
                )
                TextoCuerpoMedio(
                    text = evento.descripcion,
                    color = MaterialTheme.colorScheme.tertiary,
                )
                TextoCuerpoMedio(
                    text = evento.fecha.format(formatter),
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = onClickOpciones
            ) {
                Icon(
                    imageVector = Icons.Default.MoreHoriz,
                    contentDescription = "Opciones"
                )
            }
        }
    }
}