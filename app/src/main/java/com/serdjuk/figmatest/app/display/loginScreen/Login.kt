package com.serdjuk.figmatest.app

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.serdjuk.figmatest.app.data.*
import com.serdjuk.figmatest.app.display.ScreenName
import com.serdjuk.figmatest.app.display.setScreen

@Composable
fun Login() {

    val pass = remember { mutableStateOf(true) }
    val context = LocalContext.current
    var click by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        UserRepository(context).fromPreference()
    }

    LaunchedEffect(click) {
        if (click) {
            click = false
            if (dbSql.logIn()) {
                setScreen(ScreenName.SHOP)
            } else {
                Toast.makeText(context, PointState.WRONG_PASS_NAME.type, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        Text(text = "Welcome back", style = MaterialTheme.typography.body1)
        InputValue(value = UserModel.firstName, placeholder = FIRST_NAME)
        InputValue(value = UserModel.password, placeholder = PASSWORD, pass = pass)
        PadBottom(value = PAD_BOTTOM)

        EnterButton(text = "Login") { click = true }
    }

}