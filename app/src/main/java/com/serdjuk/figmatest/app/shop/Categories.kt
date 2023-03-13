package com.serdjuk.figmatest.app

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serdjuk.figmatest.R
import com.serdjuk.figmatest.ui.theme.CircleColor

@Composable
fun Categories() {
    val scrollState = remember {
        ScrollState(0)
    }
    LazyRow(
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        item() {

            IconLabel(iconId = R.drawable.phones, text = "Phones")
            IconLabel(iconId = R.drawable.headphones, text = "Headphones")
            IconLabel(iconId = R.drawable.games, text = "Games")
            IconLabel(iconId = R.drawable.cars, text = "Cars")
            IconLabel(iconId = R.drawable.furniture, text = "Furniture")
            IconLabel(iconId = R.drawable.kids, text = "Kids")
            IconLabel(iconId = R.drawable.kids, text = "Kids")
            IconLabel(iconId = R.drawable.kids, text = "Kids")
            IconLabel(iconId = R.drawable.kids, text = "Kids")
            IconLabel(iconId = R.drawable.kids, text = "Kids")
            IconLabel(iconId = R.drawable.kids, text = "Kids")
            IconLabel(iconId = R.drawable.kids, text = "Kids")
            IconLabel(iconId = R.drawable.kids, text = "Kids")
            IconLabel(iconId = R.drawable.kids, text = "Kids")
        }
    }
}

@Composable
private fun IconLabel(iconId: Int, text: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(50f.dp)
    ) {
        Box() {
            Icon(
                modifier = Modifier.align(
                    Alignment.Center
                ).clickable {

                },
                painter = painterResource(id = R.drawable.ellipse_4),
                contentDescription = null,
                tint = CircleColor
            )
            Icon(
                modifier = Modifier.align(
                    Alignment.Center
                ), painter = painterResource(id = iconId), contentDescription = null
            )

        }
        Text(
            text = text,
            style = MaterialTheme.typography.h2.copy(fontSize = 8f.sp),
            textAlign = TextAlign.Center
        )
    }
}