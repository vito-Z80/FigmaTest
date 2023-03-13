package com.serdjuk.figmatest.app

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.serdjuk.figmatest.data.*
import com.serdjuk.figmatest.dbSql
import com.serdjuk.figmatest.screen
import kotlinx.coroutines.withContext

@Composable
fun Login() {
    val pass = remember { mutableStateOf(true) }
    val context = LocalContext.current
    val checking = remember { mutableStateOf(false) }
    val enabled = remember { mutableStateOf(true) }
    if (checking.value) {
        LaunchedEffect(checking.value) {
            checking.value = false
            enabled.value = false
            withContext(coroutineContext) {
                val result = dbSql.query(
                    ReaderContract.FeedEntry.TABLE_NAME_USER,
                    arrayOf(
                        ReaderContract.FeedEntry.COLUMN_NAME_FIRST_NAME,
                        ReaderContract.FeedEntry.COLUMN_NAME_PASSWORD
                    ),
                    arrayOf(
                        Pair(
                            ReaderContract.FeedEntry.COLUMN_NAME_FIRST_NAME,
                            UserData.firstName.value
                        ),
                        Pair(
                            ReaderContract.FeedEntry.COLUMN_NAME_PASSWORD,
                            Password.encryption(UserData.password.value)
                        )
                    )
                )
                if (!result.isNullOrEmpty()) {
                    screen.value = Navigate.SHOP
                } else {
                    signInError(context, "Wrong Name or Password\nTry again")
                }
            }
            enabled.value = true
        }
    }



    AppFile.loadData(context)
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        Text(text = "Welcome back", style = MaterialTheme.typography.h1)
        InputValue(value = UserData.firstName, placeholder = FIRST_NAME)
        InputValue(value = UserData.password, placeholder = PASSWORD, pass = pass)
        PadBottom(value = PAD_BOTTOM)

        EnterButton(text = "Login") {
            checking.value = true
        }
    }

}