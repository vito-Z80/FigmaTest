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
import com.serdjuk.figmatest.data.sale

@Composable
fun FlashSaleDisplay(screenSize: MutableState<IntSize>) {

    val scale = 0.5f

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Flash sale",
            style = MaterialTheme.typography.h1,
            fontSize = 15f.sp,
            modifier = Modifier.padding(8.dp)
        )

        LazyRow {
            items(sale.size) {
                ProductBox(scale = scale, product = sale[it], screenSize = screenSize)
            }
        }
    }

}