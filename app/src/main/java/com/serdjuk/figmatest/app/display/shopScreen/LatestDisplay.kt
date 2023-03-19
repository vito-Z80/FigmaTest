package com.serdjuk.figmatest.app

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serdjuk.figmatest.app.data.productLatest
import com.serdjuk.figmatest.app.display.boxes.ProductBox
import com.serdjuk.figmatest.ui.theme.BlackColor
import com.serdjuk.figmatest.ui.theme.MutedColor

@Composable
fun LatestDisplay(screenSize: MutableState<IntSize>) {

    val scale = 0.33f

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Latest",
                style = MaterialTheme.typography.body2,
                fontSize = 15f.sp,
                fontWeight = FontWeight.Medium,
                color = BlackColor,
            )
            Text(
                text = "View all",
                style = MaterialTheme.typography.body2,
                fontSize = 9.sp,
                fontWeight = FontWeight.Medium,
                color = MutedColor,
                modifier = Modifier.clickable { }
            )
        }

        LazyRow(modifier = Modifier.padding(bottom = 16.dp)) {
            productLatest.value?.let { product ->
                items(product.size) {
                    ProductBox(scale = scale, product = product[it], screenSize = screenSize)
                }
            }
        }
    }

}