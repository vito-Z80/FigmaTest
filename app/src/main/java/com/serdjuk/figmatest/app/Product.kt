package com.serdjuk.figmatest.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.serdjuk.figmatest.ui.theme.ParagraphColor


@Composable
fun Product(title: String) {
    Column {
        Row(horizontalArrangement = Arrangement.SpaceAround) {
            Text(text = title, style = MaterialTheme.typography.h2.copy(color = ParagraphColor, fontSize = 15f.sp))
            Text(text = "View all", style = MaterialTheme.typography.h2.copy( fontSize = 8f.sp))

        }
        LazyRow() {

        }
    }

}