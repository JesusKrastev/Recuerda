package com.fernandort.recuerda.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.fernandort.recuerda.utilities.validacion.Validacion

@Composable
fun OutlinedTextFieldWithErrorState(
    modifier: Modifier = Modifier,
    textState: String,
    hintText: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    keyboardActions: KeyboardActions = KeyboardActions(),
    onValueChange: (String) -> Unit,
    validacionState: Validacion,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.secondary,
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
            focusedTextColor = MaterialTheme.colorScheme.onSecondary,
            unfocusedTextColor = MaterialTheme.colorScheme.onSecondary,
            focusedLeadingIconColor = MaterialTheme.colorScheme.tertiary,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.tertiary
        ),
        supportingText = {
            if (validacionState.hayError) {
                TextoCuerpoLargo(
                    text = validacionState.mensajeError!!,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        isError = validacionState.hayError,
        trailingIcon = trailingIcon,
        value = textState,
        onValueChange = onValueChange,
        singleLine = singleLine,
        leadingIcon = leadingIcon,
        visualTransformation = visualTransformation,
        placeholder = {
            TextoCuerpoLargo(
                text = hintText,
                color = MaterialTheme.colorScheme.tertiary
            )
        },
        readOnly = readOnly,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    )
}