package com.fernandort.recuerda.ui.features.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun NavBar(
    selectedPage: Int,
    onNavigateToNotas: () -> Unit,
    onNavigateToContactos: () -> Unit,
) {
    @Immutable
    data class NavOption(
        val icon: ImageVector,
        val description: String? = null,
        val title: String,
        val onClick: () -> Unit
    )

    val listNavOptions: List<NavOption> = listOf(
        NavOption(
            icon = Icons.Filled.Article,
            description = "Notas",
            title = "Notas",
            onClick = onNavigateToNotas
        ),
        NavOption(
            icon = Icons.Filled.Contacts,
            description = "Contactos",
            title = "Contactos",
            onClick = onNavigateToContactos
        ),
    )

    NavigationBar(
        modifier = Modifier.drawBehind {
            drawLine(
                color = Color.Gray.copy(0.5f),
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = 1.dp.toPx()
            )
        },
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listNavOptions.forEachIndexed { index, button ->
                val isSelected: Boolean = selectedPage == index

                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = button.icon,
                            contentDescription = button.description,
                            tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.5f
                            )
                        )
                    },
                    label = {
                        Text(
                            text = button.title,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.5f
                            )
                        )
                    },
                    colors =
                    NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent,
                        selectedIconColor = MaterialTheme.colorScheme.onSurface,
                        selectedTextColor = MaterialTheme.colorScheme.onSurface,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurface
                    ),
                    selected = isSelected,
                    onClick = button.onClick
                )
            }
        }
    }
}