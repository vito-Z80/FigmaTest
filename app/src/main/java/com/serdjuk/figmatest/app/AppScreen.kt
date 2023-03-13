package com.serdjuk.figmatest.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.serdjuk.figmatest.app.bars.BottomNavigationBar
import com.serdjuk.figmatest.app.profile.Profile
import com.serdjuk.figmatest.app.shop.ShopPage
import com.serdjuk.figmatest.data.Navigate
import com.serdjuk.figmatest.screen


val bottomBarShow = mutableStateOf(false)

@Composable
fun AppScreen() {
    when (screen.value) {
        Navigate.SIGN_IN -> SignIn()
        Navigate.LOG_IN -> Login()
        Navigate.SHOP -> ShopPage()
        Navigate.PROFILE -> Profile()
        Navigate.HOME -> TODO()
        Navigate.BACK -> TODO()
    }

    if (bottomBarShow.value) {
        BottomNavigationBar()
    }

}