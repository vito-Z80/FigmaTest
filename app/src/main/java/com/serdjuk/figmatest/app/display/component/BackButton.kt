package com.serdjuk.figmatest.app.display.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.serdjuk.figmatest.R
import com.serdjuk.figmatest.backCommand
import com.serdjuk.figmatest.ui.theme.BlackColor

@Composable
fun BackButton(modifier: Modifier) {
    IconButton(
        onClick = { backCommand.value = true },
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.arrow_left),
            contentDescription = null,
            tint = BlackColor
        )
    }
}