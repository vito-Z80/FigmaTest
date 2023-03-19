package com.serdjuk.figmatest.app.display.shopScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import com.serdjuk.figmatest.app.AppSearch
import com.serdjuk.figmatest.app.display.component.Categories
import com.serdjuk.figmatest.app.LatestDisplay
import com.serdjuk.figmatest.app.data.Api
import com.serdjuk.figmatest.app.data.brands
import com.serdjuk.figmatest.app.data.productLatest
import com.serdjuk.figmatest.app.data.productSale
import com.serdjuk.figmatest.data.TopNavigationBar
import kotlinx.coroutines.delay


@Composable
fun ShopPage() {

    val screenSize = remember { mutableStateOf(IntSize(0, 0)) }
    val density = Density(LocalContext.current).density


    LaunchedEffect(true) {
        if (brands.value == null) {
            Api.getBrands()
        }
        delay(1000)
        if (productLatest.value == null || productSale.value == null) {
            Api.getProducts()
        }

    }

    Surface(
        modifier = Modifier.onGloballyPositioned {
            screenSize.value = IntSize(
                (it.size.width.toFloat() / density).toInt(),
                (it.size.height.toFloat() / density).toInt()
            )
        }
    ) {

        Column {
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
                    AppSearch()
                    Categories()
                }
            }

            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f / 3f * 2f)
            ) {
                LazyColumn(modifier = Modifier.navigationBarsPadding()) {
                    if (productLatest.value != null && productSale.value != null) {
                        item {
                            LatestDisplay(screenSize)
                        }
                        item {
                            FlashSaleDisplay(screenSize)
                        }
                    }
                    item {
                        BrandDisplay(screenSize = screenSize)
                    }
                }
            }
        }
    }
}