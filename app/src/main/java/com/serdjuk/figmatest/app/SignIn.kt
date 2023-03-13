package com.serdjuk.figmatest.app

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.serdjuk.figmatest.R
import com.serdjuk.figmatest.data.*
import com.serdjuk.figmatest.dbSql
import com.serdjuk.figmatest.screen
import com.serdjuk.figmatest.ui.theme.BlackColor
import com.serdjuk.figmatest.ui.theme.FigmaTestTheme
import com.serdjuk.figmatest.ui.theme.FontColor2


@Composable
fun SignIn() {
    // TODO сделать регу с проверкой существовани мыла и имени
    //

    val screenSize = remember { mutableStateOf(IntSize(0, 0)) }
    val density = Density(LocalContext.current).density
    FigmaTestTheme {


        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.onGloballyPositioned {
                screenSize.value = IntSize(
                    (it.size.width.toFloat() / density).toInt(),
                    (it.size.height.toFloat() / density).toInt()
                )
            }
        ) {

            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                // sign in label
                Text(text = "Sign in", style = MaterialTheme.typography.h1)

                // inputs
                InputValue(
                    value = UserData.firstName,
                    placeholder = "First name"
                )
                InputValue(
                    value = UserData.lastName,
                    placeholder = "Last name"
                )
                InputValue(
                    value = UserData.emailAddress,
                    placeholder = "Email",
                    regex = emailRegex
                )
                // sign in button
                InButtons()
                LogInButtons()


            }
        }

    }


}

fun signInError(context: Context, message: String) {
    Toast.makeText(
        context,
        message,
        Toast.LENGTH_SHORT
    ).show()
}

@Composable
private fun InButtons() {
    val context = LocalContext.current
    Column {
        EnterButton(text = "Sign in") {
            when (UserData.isEnterDataCorrect()) {
                UserEnterState.EMAIL_IS_EXISTS -> {
                    signInError(
                        context = context,
                        message = "Email is existing in data base, try log in."
                    )
                }
                UserEnterState.WRONG_EMAIL -> {
                    signInError(context = context, message = "Email is not correctly, try again.")
                }
                UserEnterState.NAME_EMPTY -> {
                    signInError(context = context, message = "First or Last name is empty")
                }
                UserEnterState.DONE -> {
                    // TODO содержимое убрать в лаунчэфект
                    signInError(context = context, message = "Done")
                    val reelPass = Password.generate()
                    val encryptPass = Password.encryption(reelPass)
                    // локально сохраняется реальный пароль, в БД отсылается шифрованный
                    UserData.password.value = reelPass
                    UserData.avatar.value = DEFAULT_USER_AVATAR
                    dbSql.insert(
                        ReaderContract.FeedEntry.TABLE_NAME_USER,
                        Pair(
                            ReaderContract.FeedEntry.COLUMN_NAME_FIRST_NAME,
                            UserData.firstName.value
                        ),
                        Pair(
                            ReaderContract.FeedEntry.COLUMN_NAME_LAST_NAME,
                            UserData.lastName.value
                        ),
                        Pair(
                            ReaderContract.FeedEntry.COLUMN_NAME_EMAIL,
                            UserData.emailAddress.value
                        ),
                        Pair(
                            ReaderContract.FeedEntry.COLUMN_NAME_PASSWORD,
                            encryptPass
                        ),
                        Pair(ReaderContract.FeedEntry.COLUMN_NAME_AVATAR, UserData.avatar.value),
                    )
                    AppFile.saveData(context)
                    screen.value = Navigate.SHOP
                }
            }
        }

        Row(
            modifier = Modifier.padding(end = 8f.dp, top = 8f.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Already have an account?",
                style = MaterialTheme.typography.h2,
                color = FontColor2
            )
            Text(
                text = "Log in",
                style = MaterialTheme.typography.h2,
                color = FontColor2,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .padding(start = 16f.dp)
                    .clickable {
                        screen.value = Navigate.LOG_IN
                    },
            )
        }
    }
}

@Composable
private fun LogInButtons() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // sign with google
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { screen.value = Navigate.SHOP }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.g_logo),
                contentDescription = null,
                tint = BlackColor
            )
            Text(
                text = "Sign in with Google",
                style = MaterialTheme.typography.h2,
                color = FontColor2,
                modifier = Modifier.padding(start = 16f.dp)
            )
        }
        Spacer(modifier = Modifier.padding(top = 16f.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { screen.value = Navigate.SHOP }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.apple_icon),
                contentDescription = null,
                tint = BlackColor
            )
            Text(
                text = "Sign in with Apple",
                style = MaterialTheme.typography.h2,
                color = FontColor2,
                modifier = Modifier.padding(start = 16f.dp)
            )
        }
    }
}
