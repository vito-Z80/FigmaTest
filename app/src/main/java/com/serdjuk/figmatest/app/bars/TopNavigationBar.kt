package com.serdjuk.figmatest.data

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.serdjuk.figmatest.R
import com.serdjuk.figmatest.app.bottomBarShow
import com.serdjuk.figmatest.app.shop.MultiplyColoredText
import com.serdjuk.figmatest.screen
import com.serdjuk.figmatest.ui.theme.BlackColor
import com.serdjuk.figmatest.ui.theme.BorderColor

@Composable
fun TopNavigationBar() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .systemBarsPadding()
            .padding(horizontal = 15f.dp),
        horizontalAlignment = Alignment.End
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { bottomBarShow.value = !bottomBarShow.value }) {
                Icon(painter = painterResource(id = R.drawable.menu), contentDescription = null)
            }
            MultiplyColoredText(
                words = listOf("Trade by ", "data"),
                colors = listOf(BlackColor, Color(0xFF4E55D7)),
                fontSize = 20.sp
            )
//            Text(text = "Trade by data", style = MaterialTheme.typography.h1, fontSize = 20f.sp)
            AsyncImage(
                modifier = Modifier
                    .size(32f.dp)
                    .clip(CircleShape)
                    .border(2.dp, BorderColor, CircleShape),
                model = UserData.avatar.value.ifEmpty { DEFAULT_USER_AVATAR },
                contentDescription = null,
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
            )
//            Image(
//                painter = painterResource(id = R.drawable.menu),
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .size(32f.dp)
//                    .clip(CircleShape)
//                    .border(2.dp, BorderColor, CircleShape)
//            )

        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Location", style = MaterialTheme.typography.h2.copy(fontSize = 10f.sp),
                modifier = Modifier.clickable {
                    screen.value = Navigate.PROFILE
                }
            )
            Icon(
                painter = painterResource(id = R.drawable.downarrow),
                contentDescription = null,
                modifier = Modifier.clickable { }
            )
        }
    }
}