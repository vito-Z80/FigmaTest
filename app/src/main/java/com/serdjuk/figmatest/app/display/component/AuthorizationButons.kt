package com.serdjuk.figmatest.app.display.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serdjuk.figmatest.R
import com.serdjuk.figmatest.app.data.*
import com.serdjuk.figmatest.app.display.ScreenName
import com.serdjuk.figmatest.app.display.setScreen
import com.serdjuk.figmatest.ui.theme.BlackColor


@Composable
fun AuthorizationButtons() {

    val context = LocalContext.current

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // sign with google
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                UserRepository(context).fromPreference()
                setScreen(ScreenName.SHOP)
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.g_logo),
                contentDescription = null,
                tint = BlackColor
            )
            Text(
                text = "$SIGN_IN with Google",
                style = MaterialTheme.typography.body1,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = BlackColor,
                modifier = Modifier.padding(start = 16f.dp)
            )
        }
        Spacer(modifier = Modifier.padding(top = 16f.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                UserRepository(context).fromPreference()
                setScreen(ScreenName.SHOP)
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.apple_icon),
                contentDescription = null,
                tint = BlackColor
            )
            Text(
                text = "$SIGN_IN with Apple",
                style = MaterialTheme.typography.body1,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = BlackColor,
                modifier = Modifier.padding(start = 16f.dp)
            )
        }
    }
}