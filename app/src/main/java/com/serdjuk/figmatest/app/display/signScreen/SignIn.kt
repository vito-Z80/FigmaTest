package com.serdjuk.figmatest.app

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serdjuk.figmatest.app.data.*
import com.serdjuk.figmatest.app.display.ScreenName
import com.serdjuk.figmatest.app.display.component.AuthorizationButtons
import com.serdjuk.figmatest.app.display.setScreen
import com.serdjuk.figmatest.app.domain.Password
import com.serdjuk.figmatest.app.domain.Utils
import com.serdjuk.figmatest.ui.theme.LinkColor
import com.serdjuk.figmatest.ui.theme.MutedColor
import com.serdjuk.figmatest.ui.theme.ParagraphColor


@Composable
fun SignIn() {

    val context = LocalContext.current
    val user by lazy { UserRepository(context) }
    var click by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        Utils.logOut()
    }
    LaunchedEffect(click) {
        if (click) {
            click = false
            val response = dbSql.checkFields()
            if (response == PointState.DONE) {
                UserModel.password.value = Password.generate()
                user.toPreference()
                user.toDb()
                setScreen(ScreenName.SHOP)
            }
            Toast.makeText(context, response.type, Toast.LENGTH_SHORT).show()
        }
    }

    Surface {

        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
        ) {


            // sign in label
            Text(
                text = SIGN_IN,
                style = MaterialTheme.typography.body1,
                color = ParagraphColor
            )
            // inputs
            InputValue(
                value = UserModel.firstName,
                placeholder = FIRST_NAME
            )
            InputValue(
                value = UserModel.lastName,
                placeholder = LAST_NAME
            )
            InputValue(
                value = UserModel.emailAddress,
                placeholder = EMAIL,
                regex = emailRegex
            )

            Column(modifier = Modifier.defaultMinSize()) {


                // sign in button
                EnterButton(text = SIGN_IN) { click = true }
                //
                Row(
                    modifier = Modifier.padding(end = 8f.dp, top = 8f.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Already have an account?",
                        style = MaterialTheme.typography.body1,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = MutedColor
                    )
                    Text(
                        text = "Log in",
                        style = MaterialTheme.typography.body1,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = LinkColor,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier
                            .padding(start = 16f.dp)
                            .clickable {
                                setScreen(ScreenName.LOG_IN)
                            },
                    )
                }
            }
            AuthorizationButtons()
        }
    }
}

