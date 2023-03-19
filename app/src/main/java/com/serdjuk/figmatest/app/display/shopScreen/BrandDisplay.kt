package com.serdjuk.figmatest.app.display.shopScreen

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
import com.serdjuk.figmatest.app.data.brands
import com.serdjuk.figmatest.app.display.BrandBox
import com.serdjuk.figmatest.ui.theme.BlackColor

@Composable
fun BrandDisplay(screenSize: MutableState<IntSize>) {
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
                text = "Brands",
                style = MaterialTheme.typography.body2,
                fontSize = 15f.sp,
                fontWeight = FontWeight.Medium,
                color = BlackColor,
            )
        }

        LazyRow(modifier = Modifier.padding(bottom = 16.dp)) {
            brands.value?.let { brand ->
                items(brand.size) {
                    BrandBox(scale = scale, brand = brand[it], screenSize = screenSize)
                }
            }
        }
    }
}