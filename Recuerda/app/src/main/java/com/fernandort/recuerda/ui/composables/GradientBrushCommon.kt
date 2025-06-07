package com.fernandort.recuerda.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun GradientBrush(
    isVerticalGradient: Boolean,
    colors: List<Color>
): Brush {
    val endOffset: Offset = if (isVerticalGradient) Offset(
        0f,
        Float.POSITIVE_INFINITY
    ) else Offset(Float.POSITIVE_INFINITY, 0f)

    return Brush.linearGradient(
        colors = colors,
        start = Offset.Zero,
        end = endOffset
    )
}