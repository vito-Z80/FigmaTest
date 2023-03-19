package com.serdjuk.figmatest.app.display.boxes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.serdjuk.figmatest.R
import com.serdjuk.figmatest.app.data.CURRENCY
import com.serdjuk.figmatest.app.data.ProductApp
import com.serdjuk.figmatest.app.display.ScreenName
import com.serdjuk.figmatest.app.display.setScreen
import com.serdjuk.figmatest.ui.theme.*


@Composable
fun ProductBox(scale: Float, product: ProductApp, screenSize: MutableState<IntSize>) {


    Box(
        modifier = Modifier
            .width((screenSize.value.width * scale).dp)
            .height((screenSize.value.width * 1.3f * scale).dp)
            .padding(horizontal = 8.dp)
            .background(
                color = Color(0x80808080), AbsoluteRoundedCornerShape(64f * scale)
            )
            .clickable {
                setScreen(ScreenName.PRODUCT)
            }

    ) {

        AsyncImage(
            modifier = Modifier
                .clip(RoundedCornerShape(64f * scale)),
            model = product.image_url,
            contentDescription = null,
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
        )

        if (!product.discount.isNullOrBlank()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.End
            ) {
                Box(
                    modifier = Modifier
                        .background(color = DiscountColor, RoundedCornerShape(15f))
                ) {
                    Text(
                        text = "${product.discount}% off",
                        style = MaterialTheme.typography.body2,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 8.dp),
                        color = WhiteColor
                    )
                }
            }
        }

        Row(modifier = Modifier.fillMaxSize()) {
            Column(
                Modifier
                    .fillMaxHeight()
                    .weight(2f / 3f, fill = true)
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
//                        .background(color = Color.Green)
                        .weight(1f / 2f, fill = true)
                ) {}
                Box(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f / 2f)
                        .padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.Start
                    ) {
                        ExtendedFloatingActionButton(

                            text = {
                                Text(
                                    text = product.category,
                                    style = MaterialTheme.typography.body2,
                                    fontSize = MaterialTheme.typography.body2.fontSize * scale * 1.8f,
                                    color = BlackColor,
                                    fontWeight = FontWeight.SemiBold
                                )
                            },
                            modifier = Modifier.height((40 * scale).dp),
                            onClick = { /*TODO*/ },
                            backgroundColor = CategoryButtonColor,
//                            contentColor = Color.Green,
                        )
                        // TODO need text outline
                        Text(
                            modifier = Modifier.fillMaxWidth(0.75f),
                            text = product.name,
                            style = MaterialTheme.typography.body2,
                            fontSize = MaterialTheme.typography.body2.fontSize * scale * 3f,
                            maxLines = 2,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.SemiBold,
                            color = WhiteColor
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Price label
                            Text(
                                text = "$CURRENCY${
                                    String.format(
                                        "%.2f",
                                        product.price.toDouble()
                                    )
                                }",
                                style = MaterialTheme.typography.body2.copy(

                                ),
                                fontSize = MaterialTheme.typography.body2.fontSize * scale * 2,
                                fontWeight = FontWeight.SemiBold,
                                color = WhiteColor,

                                )
                        }
                    }

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.End
                    ) {
                        Row(
//                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (!product.discount.isNullOrEmpty()) {
                                // like button
                                CircleButton(size = (24f * scale).dp, iconId = R.drawable.like)
                            }
                            // Plus button
                            CircleButton(size = (40f * scale).dp, iconId = R.drawable.plus)
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun CircleButton(size: Dp, iconId: Int) {
    Box(
        modifier = Modifier
            .padding(start = 4.dp)
            .background(color = Ellipse19Color, shape = CircleShape)
            .clickable { }
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .size(size),
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = PlusLikeColor
        )
    }
}

