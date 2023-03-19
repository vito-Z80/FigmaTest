package com.serdjuk.figmatest.app.display

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import com.serdjuk.figmatest.app.Login
import com.serdjuk.figmatest.app.SignIn
import com.serdjuk.figmatest.app.display.profileScreen.Profile
import com.serdjuk.figmatest.app.display.shopScreen.ShopPage
import com.serdjuk.figmatest.screen

enum class ScreenName {
    SIGN_IN,
    LOG_IN,
    SHOP,
    HOME,
    BACK,
    PROFILE,
    PRODUCT
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun AppScreen() {
    when (screen.value) {
        ScreenName.SIGN_IN -> SignIn()
        ScreenName.LOG_IN -> Login()
        ScreenName.SHOP -> ShopPage()
        ScreenName.PROFILE -> Profile()
        ScreenName.PRODUCT -> ProductPage()
        ScreenName.HOME -> TODO()
        ScreenName.BACK -> TODO()
    }
}

fun setScreen(scr: ScreenName) {
    screen.value = scr
}