package com.serdjuk.figmatest.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val LightColorPalette = lightColors(
    background = Color(0xFFCDCBC7),
    onBackground = Color(0xFF000000),
    surface = Color(0xFFCDCBC8),
    onSurface = Color(0xFF000001),
    primary = Color(0xFFCDCBC9),
    primaryVariant = Color(0xFFCDCBCA),
    onPrimary = Color(0xFF000002),
    secondary = Color(0xFFCDCBCB),
    secondaryVariant = Color(0xFFCDCBCC),
    onSecondary = Color(0xFF000003),
    error = Color(0xFFCDCBCD),
    onError = Color(0xFF000004)
)



@Composable
fun FigmaTestTheme( content: @Composable () -> Unit) {

    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}