package com.serdjuk.figmatest

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.serdjuk.figmatest.app.data.*
import com.serdjuk.figmatest.app.display.AppScreen
import com.serdjuk.figmatest.app.display.ScreenName
import com.serdjuk.figmatest.app.display.component.TabBar
import com.serdjuk.figmatest.app.domain.Utils
import com.serdjuk.figmatest.app.domain.getAvatarBitmap
import com.serdjuk.figmatest.data.*
import com.serdjuk.figmatest.ui.theme.FigmaTestTheme
import java.io.File
import java.util.*

/*
    https://docs.google.com/document/d/1X92XL_aIOJzmk87Tg2rqqE836jHXjR-M/edit
    https://www.figma.com/file/H0SE8wvK5kIhQlZVxp0BNj/Online-Shop-Satria-Adhi-Pradana-(Community)?node-id=1%3A573&t=YjXhhISVru6ian3p-0
 */

val screen = mutableStateOf(ScreenName.SIGN_IN)
val backCommand = mutableStateOf(false)
val systemControllerReturn = mutableStateOf(false)
val avatarBitmap: MutableState<Bitmap?> = mutableStateOf(null)

// TODO
//  product name, price - outlined
//  page 2 ?


class MainActivity : ComponentActivity() {
    private lateinit var dbHelper: FeedReaderDbHelper
    private val stack = java.util.Stack<ScreenName>()
    private val walkBack = mutableStateOf(true)

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val context = LocalContext.current

            getAvatarBitmap(context = context, File(context.filesDir, AVATAR_FILE).toUri())
            dbHelper = FeedReaderDbHelper(context = context)
            dbSql = DbSql(dbHelper = dbHelper)

            // https://semicolonspace.com/status-bar-jetpack-compose/
            val systemUiController = rememberSystemUiController()
            systemUiController.setSystemBarsColor(color = Color.Transparent)
            systemUiController.navigationBarDarkContentEnabled = false
            systemUiController.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

            FigmaTestTheme {


                LaunchedEffect(screen.value) {
                    stack.push(screen.value)
                }

                if (backCommand.value) {
                    LaunchedEffect(backCommand.value) {
                        backCommand.value = false
                        Utils.navigation(stack = stack, walk = walkBack, context = window.context)
                    }
                }
                Scaffold(topBar = { }, bottomBar = {
                    // принудительный вызов для мест которые влияют на systemUiController.isNavigationBarVisible
                    systemControllerReturn.value
                    when (screen.value) {
                        ScreenName.SHOP,
                        ScreenName.HOME,
                        ScreenName.PROFILE,
                        ScreenName.PRODUCT,
                        -> {
                            systemUiController.isNavigationBarVisible = false
                            TabBar()
                        }
                        else -> {
                            systemUiController.isNavigationBarVisible = true
                        }
                    }

                }) {

                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .navigationBarsPadding()
                    ) {
                        BackHandler(enabled = walkBack.value) {
                            Utils.navigation(
                                stack = stack,
                                walk = walkBack,
                                context = window.context
                            )
                        }
                        AppScreen()
                    }
                    it.calculateBottomPadding()
                }
            }
        }
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}