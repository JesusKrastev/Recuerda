package com.fernandort.recuerda.ui.features.contactos

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fernandort.recuerda.ui.components.Boton
import com.fernandort.recuerda.ui.components.TopBar
import com.fernandort.recuerda.ui.composables.CorrutinaGestionSnackBar
import com.fernandort.recuerda.ui.composables.OutlinedTextFieldWithErrorState
import com.fernandort.recuerda.ui.composables.SnackbarCommon
import com.fernandort.recuerda.ui.composables.TextoCuerpoLargo
import com.fernandort.recuerda.ui.composables.TextoTituloLargo
import com.fernandort.recuerda.ui.features.contactos.components.Contacto
import com.fernandort.recuerda.ui.theme.RecuerdaTheme
import com.fernandort.recuerda.utilities.dispositivo.registroLlamarPorTelefonoIntent
import com.fernandort.recuerda.utilities.manejo_errores.InformacionEstadoUiState
import com.fernandort.recuerda.utilities.validacion.Validacion

@Composable
fun Buscador(
    modifier: Modifier = Modifier,
    buscadorState: String,
    onCambiarValor: (String) -> Unit
) {
    OutlinedTextFieldWithErrorState(
        modifier = modifier.fillMaxWidth(),
        textState = buscadorState,
        hintText = "Buscar contacto",
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                tint = MaterialTheme.colorScheme.tertiary,
                contentDescription = "Buscar contacto"
            )
        },
        trailingIcon = {
            IconButton(
                modifier = modifier,
                onClick = { onCambiarValor("") }
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    tint = MaterialTheme.colorScheme.onSecondary,
                    contentDescription = "Eliminar busqueda"
                )
            }
        },
        validacionState = object : Validacion { },
        onValueChange = onCambiarValor
    )
}

@Composable
fun ContactoItem(
    modifier: Modifier = Modifier,
    contacto: ContactoUiState,
    onEditar: () -> Unit,
    onEliminar: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val llamar = registroLlamarPorTelefonoIntent(telefono = contacto.telefono)

    Box(
        modifier = modifier
    ) {
        Contacto(
            contacto = contacto,
            onLlamar = {
                llamar.launch(android.Manifest.permission.CALL_PHONE)
            },
            onEditar = {
                expanded = true
            }
        )
        ContactoDropDownMenu(
            expanded = expanded,
            onEditar = onEditar,
            onEliminar = onEliminar,
            onDismissRequest = { expanded = false }
        )
    }
}

@Composable
fun ListaContactos(
    modifier: Modifier = Modifier,
    contactosState: List<ContactoUiState>,
    onContactosEvent: (ContactosEvent) -> Unit
) {
    LazyColumn(
        modifier = modifier.animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(contactosState) { contacto ->
            ContactoItem(
                contacto = contacto,
                onEditar = {
                    onContactosEvent(ContactosEvent.OnEditarContacto(contacto))
                },
                onEliminar = {
                    onContactosEvent(ContactosEvent.OnEliminarContacto(contacto))
                }
            )
        }
    }
}

@Composable
fun SinContactos(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(46.dp),
                imageVector = Icons.Filled.Person,
                contentDescription = "Sin contactos",
                tint = MaterialTheme.colorScheme.tertiary
            )
            TextoCuerpoLargo(
                text = "No hay contactos",
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

@Composable
fun BusquedaSinContactos(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(46.dp),
                imageVector = Icons.Filled.Search,
                contentDescription = "Sin contactos",
                tint = MaterialTheme.colorScheme.tertiary
            )
            TextoCuerpoLargo(
                text = "Sin resultados",
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

@Composable
fun Contactos(
    modifier: Modifier = Modifier,
    contactosState: List<ContactoUiState>,
    buscadorState: String,
    onContactosEvent: (ContactosEvent) -> Unit
) {
    when {
        contactosState.isNotEmpty() ->
            ListaContactos(
                modifier = modifier,
                contactosState = contactosState,
                onContactosEvent = onContactosEvent
            )
        buscadorState.isNotEmpty() ->
            BusquedaSinContactos(
                modifier = modifier
            )
        else ->
            SinContactos(
                modifier = modifier
            )
    }
}

@Composable
fun PanelContactos(
    modifier: Modifier = Modifier,
    contactosState: List<ContactoUiState>,
    buscadorState: String,
    onContactosEvent: (ContactosEvent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Buscador(
            buscadorState = buscadorState,
            onCambiarValor = {
                onContactosEvent(ContactosEvent.OnCambiarBusqueda(it))
            }
        )
        Contactos(
            contactosState = contactosState,
            buscadorState = buscadorState,
            onContactosEvent = onContactosEvent
        )
    }
}

@Composable
fun BotonAñadirContacto(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ExtendedFloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = "Añadir contacto",
                tint = MaterialTheme.colorScheme.onPrimary
            )
            TextoCuerpoLargo(
                text = "Añadir contacto",
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun EditarContactoBottomSheet(
    modifier: Modifier = Modifier,
    contactoState: ContactoUiState,
    validacionContactoState: ValidacionContactoUiState,
    onCambiarNombre: (String) -> Unit,
    onCambiarApellido: (String) -> Unit,
    onCambiarTelefono: (String) -> Unit,
    onGuardar: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextoTituloLargo(
            text = "Editar contacto",
            color = MaterialTheme.colorScheme.onSecondary,
            fontWeight = FontWeight.SemiBold
        )
        CampoNombre(
            nombreState = contactoState.nombre,
            validacionState = validacionContactoState.validacionNombre,
            onCambiarValor = onCambiarNombre
        )
        CampoApellidos(
            apellidosState = contactoState.apellidos,
            validacionState = validacionContactoState.validacionApellidos,
            onCambiarValor = onCambiarApellido
        )
        CampoTelefono(
            telefonoState = contactoState.telefono,
            validacionState = validacionContactoState.validacionTelefono,
            onCambiarValor = onCambiarTelefono
        )
        BotonGuardar(
            onClick = onGuardar
        )
    }
}

@Composable
fun CampoNombre(
    modifier: Modifier = Modifier,
    nombreState: String,
    validacionState: Validacion,
    onCambiarValor: (String) -> Unit
) {
    OutlinedTextFieldWithErrorState(
        modifier = modifier.fillMaxWidth(),
        textState = nombreState,
        hintText = "Nombre",
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Edit,
                tint = MaterialTheme.colorScheme.tertiary,
                contentDescription = "Nombre"
            )
        },
        validacionState = validacionState,
        onValueChange = onCambiarValor
    )
}

@Composable
fun CampoApellidos(
    modifier: Modifier = Modifier,
    apellidosState: String,
    validacionState: Validacion,
    onCambiarValor: (String) -> Unit
) {
    OutlinedTextFieldWithErrorState(
        modifier = modifier.fillMaxWidth(),
        textState = apellidosState,
        hintText = "Apellidos",
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Edit,
                tint = MaterialTheme.colorScheme.tertiary,
                contentDescription = "Apellidos"
            )
        },
        validacionState = validacionState,
        onValueChange = onCambiarValor
    )
}

@Composable
fun CampoTelefono(
    modifier: Modifier = Modifier,
    telefonoState: String,
    validacionState: Validacion,
    onCambiarValor: (String) -> Unit
) {
    OutlinedTextFieldWithErrorState(
        modifier = modifier.fillMaxWidth(),
        textState = telefonoState,
        hintText = "Teléfono",
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Call,
                tint = MaterialTheme.colorScheme.tertiary,
                contentDescription = "Telefono"
            )
        },
        validacionState = validacionState,
        onValueChange = onCambiarValor
    )
}

@Composable
fun BotonGuardar(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Boton(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        text = "Guardar"
    )
}

@Composable
fun CrearContactoBottomSheet(
    modifier: Modifier = Modifier,
    validacionContactoState: ValidacionContactoUiState,
    contactoState: ContactoUiState,
    onCambiarNombre: (String) -> Unit,
    onCambiarApellido: (String) -> Unit,
    onCambiarTelefono: (String) -> Unit,
    onGuardar: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextoTituloLargo(
            text = "Crear contacto",
            color = MaterialTheme.colorScheme.onSecondary,
            fontWeight = FontWeight.SemiBold
        )
        CampoNombre(
            nombreState = contactoState.nombre,
            validacionState = validacionContactoState.validacionNombre,
            onCambiarValor = onCambiarNombre
        )
        CampoApellidos(
            apellidosState = contactoState.apellidos,
            validacionState = validacionContactoState.validacionApellidos,
            onCambiarValor = onCambiarApellido
        )
        CampoTelefono(
            telefonoState = contactoState.telefono,
            validacionState = validacionContactoState.validacionTelefono,
            onCambiarValor = onCambiarTelefono
        )
        BotonGuardar(
            onClick = onGuardar
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactosBottomSheet(
    modifier: Modifier = Modifier,
    contactoState: ContactoUiState,
    validacionContactoState: ValidacionContactoUiState,
    onCambiarNombre: (String) -> Unit,
    onCambiarTelefono: (String) -> Unit,
    onCambiarApellido: (String) -> Unit,
    onDismissRequest: () -> Unit,
    tipoBottomSheetState: BottomSheetType,
    onGuardar: () -> Unit,
    sheetState: SheetState
) {
    ModalBottomSheet(
        modifier = modifier,
        tonalElevation = 0.dp,
        containerColor = MaterialTheme.colorScheme.background,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {
        when (tipoBottomSheetState) {
            BottomSheetType.Editar -> {
                EditarContactoBottomSheet(
                    contactoState = contactoState,
                    onGuardar = onGuardar,
                    validacionContactoState = validacionContactoState,
                    onCambiarNombre = onCambiarNombre,
                    onCambiarTelefono = onCambiarTelefono,
                    onCambiarApellido = onCambiarApellido
                )
            }
            BottomSheetType.Crear -> {
                CrearContactoBottomSheet(
                    contactoState = contactoState,
                    onGuardar = onGuardar,
                    validacionContactoState = validacionContactoState,
                    onCambiarNombre = onCambiarNombre,
                    onCambiarTelefono = onCambiarTelefono,
                    onCambiarApellido = onCambiarApellido
                )
            }
            else -> { }
        }
    }
}

@Composable
fun ContactoDropDownMenu(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onEditar: () -> Unit,
    onEliminar: () -> Unit,
    onDismissRequest: () -> Unit
) {
    @Immutable
    data class Option(
        val text: String,
        val icon: ImageVector,
        val onClick: () -> Unit
    )

    val options = listOf(
        Option(
            icon = Icons.Outlined.Edit,
            text = "Editar",
            onClick = onEditar,
        ),
        Option(
            text = "Eliminar",
            icon = Icons.Outlined.Delete,
            onClick = onEliminar
        ),
    )

    DropdownMenu(
        modifier = modifier,
        expanded = expanded,
        containerColor = MaterialTheme.colorScheme.secondary,
        onDismissRequest = onDismissRequest
    ) {
        options.forEach { option ->
            DropdownMenuItem(
                text = {
                    Text(option.text)
                },
                onClick = {
                    option.onClick()
                    onDismissRequest()
                },
                leadingIcon = {
                    Icon(
                        imageVector = option.icon,
                        contentDescription = option.text,
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactosScreen(
    modifier: Modifier = Modifier,
    buscadorState: String,
    validacionContactoState: ValidacionContactoUiState,
    tipoBottomSheetState: BottomSheetType,
    informacionEstado: InformacionEstadoUiState,
    contactosState: List<ContactoUiState>,
    contactoState: ContactoUiState,
    onContactosEvent: (ContactosEvent) -> Unit
) {
    val bottomSheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val snackbarHostState = remember { SnackbarHostState() }

    CorrutinaGestionSnackBar(
        snackbarHostState = snackbarHostState,
        informacionEstado = informacionEstado
    )

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            TopBar(
                title = "Contactos"
            )
        },
        floatingActionButton = {
            BotonAñadirContacto(
                onClick = {
                    onContactosEvent(ContactosEvent.OnCrearContacto)
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) {
                SnackbarCommon(informacionEstado = informacionEstado)
            }
        }
    ) { paddingValues ->
        PanelContactos(
            modifier = Modifier.padding(paddingValues),
            contactosState = contactosState,
            onContactosEvent = onContactosEvent,
            buscadorState = buscadorState
        )
        if (tipoBottomSheetState != BottomSheetType.Oculto) {
            ContactosBottomSheet(
                onDismissRequest = {
                    onContactosEvent(ContactosEvent.OnCerrarBottomSheet)
                },
                onGuardar = {
                    onContactosEvent(ContactosEvent.OnGuardarContacto)
                },
                validacionContactoState = validacionContactoState,
                tipoBottomSheetState = tipoBottomSheetState,
                sheetState = bottomSheetState,
                contactoState = contactoState,
                onCambiarTelefono = {
                    onContactosEvent(ContactosEvent.OnTelefonoChange(it))
                },
                onCambiarNombre = {
                    onContactosEvent(ContactosEvent.OnNombreChange(it))
                },
                onCambiarApellido = {
                    onContactosEvent(ContactosEvent.OnApellidoChange(it))
                }
            )
        }
    }
}

@Preview
@Composable
fun ContactosScreenPreview() {
    RecuerdaTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ContactosScreen(
                contactosState = listOf(
                    ContactoUiState(
                        id = "1",
                        nombre = "Juan Perez",
                        telefono = "5555555555"
                    ),
                    ContactoUiState(
                        id = "2",
                        nombre = "Juan Perez",
                        telefono = "5555555555"
                    ),
                    ContactoUiState(
                        id = "3",
                        nombre = "Juan Perez",
                        telefono = "5555555555"
                    )
                ),
                tipoBottomSheetState = BottomSheetType.Oculto,
                onContactosEvent = {},
                contactoState = ContactoUiState(),
                buscadorState = "",
                validacionContactoState = ValidacionContactoUiState(),
                informacionEstado = InformacionEstadoUiState.Oculta()
            )
        }
    }
}