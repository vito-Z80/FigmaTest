package com.serdjuk.figmatest.app.shop

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.serdjuk.figmatest.ui.theme.fonts
import kotlin.math.min

@Composable
fun MultiplyColoredText(words: List<String>, colors: List<Color>, fontSize: TextUnit = 16.sp) {
    Text(buildAnnotatedString {
        words.forEachIndexed { index, str ->
            val color = colors[min(index, colors.size - 1)]
            withStyle(style = SpanStyle(color = color, fontSize = fontSize, fontFamily = fonts)) {
                append(str)
            }
        }
    })
}