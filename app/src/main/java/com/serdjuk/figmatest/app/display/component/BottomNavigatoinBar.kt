@file:OptIn(ExperimentalAnimationApi::class)

package com.serdjuk.figmatest.app.display.component

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.serdjuk.figmatest.R
import com.serdjuk.figmatest.app.display.ScreenName
import com.serdjuk.figmatest.app.display.setScreen
import com.serdjuk.figmatest.ui.theme.BottomNavigationIconsColor
import com.serdjuk.figmatest.ui.theme.WhiteColor


@Composable
fun TabBar() {
    val showBar = remember { mutableStateOf(true) }
    Box(
        modifier = Modifier
            .background(
                color = Color(0xFFCCCCCC),
                shape = RoundedCornerShape(topStart = 48f, topEnd = 48f)
            )
            .border(
                width = 1.dp,
                color = WhiteColor,
                shape = RoundedCornerShape(topStart = 48f, topEnd = 48f)
            ),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            BottomNavigationBar(showBar)
            Icon(
                painter = painterResource(id = R.drawable.line),
                contentDescription = null,
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .padding(8.dp)
                    .clickable { showBar.value = !showBar.value }
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}


@Composable
fun BottomNavigationBar(showBar: MutableState<Boolean>) {
    AnimatedVisibility(visible = showBar.value, enter = scaleIn(), exit = scaleOut()) {

        Box(modifier = Modifier
            .clickable(enabled = false){}
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(onClick = {
//                    if (screen.value != ScreenApp.SHOP)
                        setScreen(ScreenName.SHOP)
                }) {
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
                IconButton(onClick = {
//                    if (screen.value != ScreenApp.PROFILE)
                        setScreen(ScreenName.PROFILE)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = null,
                        tint = BottomNavigationIconsColor
                    )
                }


            }
        }
    }

//    if (!showBar.value) return


    // navigation bar padding
//    Box(
//        modifier = Modifier
//            .navigationBarsPadding()
//            .systemBarsPadding()
//    ) {
//    }

}