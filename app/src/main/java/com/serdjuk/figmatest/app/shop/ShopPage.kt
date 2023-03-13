package com.serdjuk.figmatest.app.shop

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import com.serdjuk.figmatest.app.Categories
import com.serdjuk.figmatest.app.FlashSaleDisplay
import com.serdjuk.figmatest.app.LatestDisplay
import com.serdjuk.figmatest.app.Search
import com.serdjuk.figmatest.data.TopNavigationBar
import com.serdjuk.figmatest.ui.theme.FigmaTestTheme

@Composable
fun ShopPage() {
    val screenSize = remember { mutableStateOf(IntSize(0, 0)) }
    val density = Density(LocalContext.current).density

    FigmaTestTheme {
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.onGloballyPositioned {
                screenSize.value = IntSize(
                    (it.size.width.toFloat() / density).toInt(),
                    (it.size.height.toFloat() / density).toInt()
                )
            }
        ) {



            Column() {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f / 3f)
                ) {
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        TopNavigationBar()
                        Search()
                        Categories()
                    }
                }

                Box(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f / 3f * 2f)
                ) {

                LazyColumn() {
                    item {
                        LatestDisplay(screenSize)
                    }
                    item {
                        FlashSaleDisplay(screenSize)
                    }
                    item {
                        LatestDisplay(screenSize)
                    }
                }
                }

            }
        }
    }
}