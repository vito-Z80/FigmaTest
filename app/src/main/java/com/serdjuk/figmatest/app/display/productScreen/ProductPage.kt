package com.serdjuk.figmatest.app.display

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.serdjuk.figmatest.R
import com.serdjuk.figmatest.app.data.Api
import com.serdjuk.figmatest.app.data.CURRENCY
import com.serdjuk.figmatest.app.data.EMPTY_STRING
import com.serdjuk.figmatest.app.data.product
import com.serdjuk.figmatest.app.display.component.BackButton
import com.serdjuk.figmatest.ui.theme.*


private val selectedImage = mutableStateOf<String?>(null)
private val OFFSET_Y = 24.dp

@Composable
fun ProductPage() {

    LaunchedEffect(Unit) {
        if (product.value == null) {
            Api.getOneProduct()
        }

    }
    selectedImage.value = product.value?.images?.get(0)


    Surface {

        Column(
            modifier = Modifier
                .statusBarsPadding()
                .systemBarsPadding()
                .fillMaxSize()
//                .imePadding()
        ) {
            MainImage()
            AllImages()
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = OFFSET_Y),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                item {
                    NamePrice()
                }
                item {
                    ProductDescription()
                }
                item {
                    Rate()
                }
                item {
                    ProductColor()
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentAlignment = Alignment.BottomCenter
            ) {
                BuyBar()
            }
        }
    }

}

@Composable
private fun BuyBar() {

    val productCount = remember { mutableStateOf(1) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .background(
                color = BuyBarColor,
                shape = RoundedCornerShape(topStart = 48f, topEnd = 48f)
            )
            .padding(top = 16.dp),
        contentAlignment = Alignment.TopCenter
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = OFFSET_Y),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {

            Column(verticalArrangement = Arrangement.Top) {
                Text(
                    text = "Quantity: ",
                    style = MaterialTheme.typography.body2,
                    fontSize = 9.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Medium,
                    color = MutedColor,
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    FloatingActionButton(
                        onClick = { if (productCount.value > 1) productCount.value-- },
                        backgroundColor = SignButtonColor,
                        contentColor = WhiteColor,
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .width(40.dp)
                            .height(24.dp)
                    ) {
                        Text(text = "-")
                    }
                    FloatingActionButton(
                        onClick = { productCount.value++ },
                        backgroundColor = SignButtonColor,
                        contentColor = WhiteColor,
                        modifier = Modifier
                            .width(40.dp)
                            .height(24.dp),
                    ) {
                        Text(text = "+")
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(40.dp)
                    .background(color = SignButtonColor, shape = RoundedCornerShape(15f)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = "$CURRENCY ${productCount.value.toDouble() * (product.value?.price ?: 0.0)}",
                    style = MaterialTheme.typography.body2,
                    fontSize = 8.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    color = BuyPriceColor,
                )
                Text(
                    text = "ADD TO CART",
                    style = MaterialTheme.typography.body2,
                    fontSize = 8.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    color = WhiteColor,
                    modifier = Modifier.clickable { }
                )
            }
        }
    }
}

@Composable
private fun ProductColor() {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        Text(
            text = "Color:",
            style = MaterialTheme.typography.body2,
            fontSize = 10.sp,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.SemiBold,
            color = MutedColor,
        )
        product.value?.colors?.let { colors ->
            LazyRow(modifier = Modifier) {
                items(colors.size) {
                    val color = Color(java.lang.Long.decode("0x${colors[it]}"))
                    println(color.value)
                    Box(
                        modifier = Modifier
                            .padding(end = 4.dp, top = 8.dp)
                            .width(48.dp)
                            .height(32.dp)
                            .background(color = color, shape = RoundedCornerShape(15f))
                            .border(
                                width = 1.dp,
                                color = MutedColor,
                                shape = RoundedCornerShape(15f)
                            )
                            .clickable { }
                    )
                }
            }
        }
    }
}

@Composable
private fun Rate() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            painter = painterResource(id = R.drawable.star), contentDescription = null,
            tint = StarColor
        )
        Text(
            text = product.value?.rate.toString(),
            style = MaterialTheme.typography.body2,
            fontSize = 9.sp,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.SemiBold,
            color = BlackColor,
            modifier = Modifier.padding(start = 4.dp)
        )
        Text(
            text = "(${product.value?.reviews ?: 0} reviews)",
            style = MaterialTheme.typography.body2,
            fontSize = 9.sp,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Normal,
            color = MutedColor,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

@Composable
private fun NamePrice() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = OFFSET_Y),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            modifier = Modifier.fillMaxWidth(0.5f),
            text = product.value?.name ?: EMPTY_STRING,
            style = MaterialTheme.typography.body2,
            fontSize = 17.sp,
            maxLines = 2,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.SemiBold,
            color = BlackColor
        )

        Text(
            text = "$CURRENCY ${product.value?.price?.toString() ?: 0.0}",
            style = MaterialTheme.typography.body2,
            fontSize = 13.sp,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.SemiBold,
            color = BlackColor
        )
    }
}

@Composable
private fun ProductDescription() {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {

        Text(
            text = product.value?.description ?: EMPTY_STRING,
            modifier = Modifier
                .fillMaxWidth(0.65f),
            style = MaterialTheme.typography.body2,
            fontSize = 9.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Start,
            color = MutedColor
        )
    }
}

@Composable
private fun AllImages() {

    // TODO размеры брать относительно размеров экрана
    val width = 128f
    val height = 64f
    val scaleMax = 1f
    val scaleMin = 0.8f

    fun getModifier(image: String) = if (image == selectedImage.value)
        Modifier
            .width((width * scaleMax).dp)
            .height((height * scaleMax).dp)
    else {
        Modifier
            .width((width * scaleMin).dp)
            .height((height * scaleMin).dp)
    }
    product.value?.images?.let { images ->
        LazyRow(
            modifier = Modifier
                .padding(top = OFFSET_Y * 2)
                .fillMaxWidth(1f)
                .height(height.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            items(images.size) {
                AsyncImage(
                    model = images[it], contentDescription = null,
                    modifier = getModifier(images[it])
                        .padding(horizontal = 2.dp)
                        .clickable { selectedImage.value = images[it] }
                        .clip(RoundedCornerShape(15f))
                        .border(1.dp, BorderColor, RoundedCornerShape(15f)),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}

@Composable
private fun MainImage() {

    Box {
        AsyncImage(
            model = selectedImage.value, contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .offset(x = (-32).dp, y = OFFSET_Y)
                .clip(RoundedCornerShape(25f)),
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop
        )
        BackButton(Modifier.padding(start = 20.dp))
    }

}