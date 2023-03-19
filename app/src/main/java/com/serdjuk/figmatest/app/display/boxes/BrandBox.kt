package com.serdjuk.figmatest.app.display

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.serdjuk.figmatest.app.data.Brand
import com.serdjuk.figmatest.ui.theme.WhiteColor

@Composable
fun BrandBox(scale: Float, brand: Brand, screenSize: MutableState<IntSize>) {
    Box(
        modifier = Modifier
            .width((screenSize.value.width * scale).dp)
            .height((screenSize.value.width * 1.3f * scale).dp)
            .padding(horizontal = 8.dp)
            .background(
                color = Color(0x80808080), AbsoluteRoundedCornerShape(64f * scale)
            ).clickable {  }

    ) {
        AsyncImage(
            modifier = Modifier
                .clip(RoundedCornerShape(64f * scale)),
            model = brand.image_url,
            contentDescription = null,
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text(
                text = brand.name,
                style = MaterialTheme.typography.body2,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = WhiteColor,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }


    }
}