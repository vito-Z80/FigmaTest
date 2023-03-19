package com.serdjuk.figmatest.app.display.profileScreen

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.serdjuk.figmatest.R
import com.serdjuk.figmatest.ui.theme.BorderColor

@Composable
fun Avatar(uri: MutableState<Bitmap?>, size: Dp) {
    Image(
        painter = rememberAsyncImagePainter(
            uri.value ?: R.drawable.avatar
        ),
        contentDescription = null,
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .border(2.dp, BorderColor, CircleShape),
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop,
    )
}