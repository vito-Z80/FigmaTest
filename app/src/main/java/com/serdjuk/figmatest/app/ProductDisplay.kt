package com.serdjuk.figmatest.app

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.serdjuk.figmatest.R
import com.serdjuk.figmatest.data.ProductApp
import com.serdjuk.figmatest.ui.theme.CategoryButtonColor
import com.serdjuk.figmatest.ui.theme.Ellipse19Color
import com.serdjuk.figmatest.ui.theme.PlusLikeColor


@Composable
fun ProductBox(scale: Float, product: ProductApp, screenSize: MutableState<IntSize>) {

    val currency = "$"

    Box(
        modifier = Modifier
            .width((screenSize.value.width * scale).dp)
            .height((screenSize.value.width * 1.3f * scale).dp)
            .padding(horizontal = 8.dp)
            .background(
                color = Color(0x80808080), AbsoluteRoundedCornerShape(64f * scale)
            )

    ) {

        AsyncImage(
            modifier = Modifier
                .clip(RoundedCornerShape(64f * scale)),
            model = product.image_url,
            contentDescription = null,
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
        )

        if (product.discount != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.End
            ) {

                Text(text = "${product.discount}%", style = MaterialTheme.typography.h2)
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
                                    style = MaterialTheme.typography.h2,
                                    fontSize = MaterialTheme.typography.h2.fontSize * scale * 1.8f
                                )
                            },
                            modifier = Modifier.height((40 * scale).dp),
                            onClick = { /*TODO*/ },
                            backgroundColor = CategoryButtonColor,
                            contentColor = Color.Green,
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(0.75f),
                            text = product.name,
                            style = MaterialTheme.typography.h2,
                            fontSize = MaterialTheme.typography.h2.fontSize * scale * 3f,
                            maxLines = 2,
                            textAlign = TextAlign.Start
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
                                text = "$currency${String.format("%.2f", product.price)}",
                                style = MaterialTheme.typography.h2,
                                fontSize = MaterialTheme.typography.h2.fontSize * scale * 2
                            )
                        }
                    }
                    // Plus button
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Icon(
                            modifier = Modifier
                                .size((72f * scale).dp)
                                .clickable { },
                            painter = painterResource(id = R.drawable.ellipse_19),
                            contentDescription = null,
                            tint = Ellipse19Color
                        )
                        Icon(
                            modifier = Modifier
                                .size((72f * scale).dp),
//                                .align(Alignment.Center),
                            painter = painterResource(id = R.drawable.plus),
                            contentDescription = null,
                            tint = PlusLikeColor
                        )
                    }
                }
            }
        }
    }
}

