package com.serdjuk.figmatest.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serdjuk.figmatest.data.latest

@Composable
fun LatestDisplay(screenSize: MutableState<IntSize>) {

    val scale = 0.33f

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "Latest",
            style = MaterialTheme.typography.h1,
            fontSize = 15f.sp,
            modifier = Modifier.padding(start = 8.dp)
        )

        LazyRow {
            items(latest.size) {
                ProductBox(scale = scale, product = latest[it], screenSize)
            }
        }

    }

}