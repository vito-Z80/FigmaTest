package com.serdjuk.figmatest.data

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serdjuk.figmatest.R
import com.serdjuk.figmatest.app.display.profileScreen.Avatar
import com.serdjuk.figmatest.app.display.shopScreen.MultiplyColoredText
import com.serdjuk.figmatest.avatarBitmap
import com.serdjuk.figmatest.ui.theme.BlackColor

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
            IconButton(onClick = { }) {
                Icon(painter = painterResource(id = R.drawable.menu), contentDescription = null)
            }
            MultiplyColoredText(
                words = listOf("Trade by ", "data"),
                colors = listOf(BlackColor, Color(0xFF4E55D7)),
                fontSize = 20.sp
            )
            Avatar(avatarBitmap, 32.dp)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Location", style = MaterialTheme.typography.body2,
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal
            )
            Icon(
                painter = painterResource(id = R.drawable.downarrow),
                contentDescription = null
            )
        }
    }
}