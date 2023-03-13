package com.serdjuk.figmatest.app.bars

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.serdjuk.figmatest.R
import com.serdjuk.figmatest.data.BOTTOM_BAR_WEIGHT
import com.serdjuk.figmatest.data.CONTENT_WEIGHT
import com.serdjuk.figmatest.ui.theme.BottomNavigationIconsColor
import com.serdjuk.figmatest.ui.theme.WhiteColor

@Composable
fun BottomNavigationBar() {


    Column() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(CONTENT_WEIGHT)
        ) {}
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(BOTTOM_BAR_WEIGHT)
                .background(
                    color = WhiteColor,
                    shape = RoundedCornerShape(topStart = 45f, topEnd = 45f)
                ), contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.home),
                        contentDescription = null,
                        tint = BottomNavigationIconsColor
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.like),
                        contentDescription = null,
                        tint = BottomNavigationIconsColor
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.shopping_cart),
                        contentDescription = null,
                        tint = BottomNavigationIconsColor
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.message),
                        contentDescription = null,
                        tint = BottomNavigationIconsColor
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = null,
                        tint = BottomNavigationIconsColor
                    )
                }
            }


        }

    }


    // navigation bar padding
//    Box(
//        modifier = Modifier
//            .navigationBarsPadding()
//            .systemBarsPadding()
//    ) {
//    }

}