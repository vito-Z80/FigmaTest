package com.serdjuk.figmatest

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.view.WindowCompat
import com.serdjuk.figmatest.app.AppScreen
import com.serdjuk.figmatest.app.bottomBarShow
import com.serdjuk.figmatest.data.*
import com.serdjuk.figmatest.ui.theme.FigmaTestTheme

/*

    https://docs.google.com/document/d/1X92XL_aIOJzmk87Tg2rqqE836jHXjR-M/edit
    https://www.figma.com/file/H0SE8wvK5kIhQlZVxp0BNj/Online-Shop-Satria-Adhi-Pradana-(Community)?node-id=1%3A573&t=YjXhhISVru6ian3p-0
    https://inloop.github.io/svg2android/

    https://proandroiddev.com/implement-bottom-bar-navigation-in-jetpack-compose-b530b1cd9ee2
 */

val screen = mutableStateOf(Navigate.SIGN_IN)
val stack = java.util.Stack<Navigate>()
lateinit var dbSql: DbSql

class MainActivity : ComponentActivity() {
    lateinit var dbHelper: FeedReaderDbHelper

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        testJson()

        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.Transparent.value.toInt()
        window.navigationBarColor = Color.Transparent.value.toInt()

//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//        )

        setContent {
            dbHelper = FeedReaderDbHelper(LocalContext.current)
            dbSql = DbSql(dbHelper = dbHelper)


//            println(windowManager.currentWindowMetrics.bounds.height())

            FigmaTestTheme {

                LaunchedEffect(screen.value) {
                    stack.push(screen.value)
                    println("_______________________________________________________________________")
                    println(stack.joinToString())
                }
//                val systemUiController = rememberSystemUiController()
//                SideEffect {
//                    systemUiController.setStatusBarColor(color = Color.Transparent)
//                    systemUiController.setNavigationBarColor(color = Color.Transparent)
//                }
                // A surface container using the 'background' color from the theme


                Scaffold(modifier = Modifier, topBar = { }, bottomBar = {}) {
                    val walk = remember {
                        mutableStateOf(true)
                    }
                    Surface(
                        modifier = Modifier
                            .onSizeChanged { size ->
                                println(size.width)
                                println(size.height)
                            }
                            .fillMaxSize()
                            .background(
                                color = MaterialTheme.colors.background,
                            )
                            .navigationBarsPadding()
//                        .padding(10.dp),
                    ) {
                        BackHandler(enabled = walk.value) {
                            if (stack.isEmpty()) {
                                walk.value = false
                            } else {
                                stack.pop()
                                bottomBarShow.value = false
                                if (stack.isEmpty()) {
                                    walk.value = false
                                    Toast.makeText(
                                        window.context,
                                        "Tap Back again for exit",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    screen.value = stack.pop()
                                }
                            }
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

@Composable
fun BottomNavigationScreens() {
    BottomNavigation() {
        BottomNavigationItem(selected = false, onClick = { /*TODO*/ }, icon = {
            Icon(
                painter = painterResource(id = R.drawable.home),
                contentDescription = null
            )
        }, unselectedContentColor = Color.Transparent, alwaysShowLabel = false)
        BottomNavigationItem(selected = false, onClick = { /*TODO*/ }, icon = {
            Icon(
                painter = painterResource(id = R.drawable.like),
                contentDescription = null
            )
        }, unselectedContentColor = Color.Transparent, alwaysShowLabel = false)
        BottomNavigationItem(selected = false, onClick = { /*TODO*/ }, icon = {
            Icon(
                painter = painterResource(id = R.drawable.shopping_cart),
                contentDescription = null
            )
        }, unselectedContentColor = Color.Transparent, alwaysShowLabel = false)
        BottomNavigationItem(selected = false, onClick = { /*TODO*/ }, icon = {
            Icon(
                painter = painterResource(id = R.drawable.message),
                contentDescription = null
            )
        }, unselectedContentColor = Color.Transparent, alwaysShowLabel = false)
    }
}