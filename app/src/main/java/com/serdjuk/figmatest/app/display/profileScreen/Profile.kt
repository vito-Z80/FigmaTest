package com.serdjuk.figmatest.app.display.profileScreen

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serdjuk.figmatest.R
import com.serdjuk.figmatest.app.data.AVATAR_FILE
import com.serdjuk.figmatest.app.data.UserModel
import com.serdjuk.figmatest.app.display.ScreenName
import com.serdjuk.figmatest.app.display.component.BackButton
import com.serdjuk.figmatest.app.display.component.ProfileLabelButton
import com.serdjuk.figmatest.app.display.setScreen
import com.serdjuk.figmatest.app.domain.Utils
import com.serdjuk.figmatest.app.domain.getAvatarBitmap
import com.serdjuk.figmatest.avatarBitmap
import com.serdjuk.figmatest.systemControllerReturn
import com.serdjuk.figmatest.ui.theme.*
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun Profile() {


    val context = LocalContext.current
    val changeAvatar = remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        //  https://stackoverflow.com/questions/72025124/save-and-show-picked-picture-by-uri-after-app-restart
        val input = uri?.let {
            context.contentResolver.openInputStream(it)
                ?: return@rememberLauncherForActivityResult
        }
        // TODO добавлять к сохраненной картинке имя пользвотеля, что бы при старте приложения фото соответствовало аккаунту
        val outputFile = context.filesDir.resolve(AVATAR_FILE)
        input?.copyTo(outputFile.outputStream()).also { input?.close() }
        getAvatarBitmap(context, uri)
        systemControllerReturn.value = !systemControllerReturn.value
    }

    LaunchedEffect(changeAvatar.value) {
        if (changeAvatar.value) {
            withContext(coroutineContext) {
                changeAvatar.value = false
                launcher.launch("image/*")
            }
        }
    }

    FigmaTestTheme {
        Column(
        ) {
            // status bar padding
            Box(
                modifier = Modifier
                    .statusBarsPadding()
                    .systemBarsPadding()
            ) {}

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f / 3f)
            ) {
                // top
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 8f.dp),
//                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
//                            .weight(1f),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Box(
                            contentAlignment = Alignment.CenterStart
                        ) {
                            BackButton(Modifier.padding(start = 20.dp))
                            Text(
                                text = "Profile",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.Center),
                                style = MaterialTheme.typography.body1,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = BlackColor
                            )
                        }
                    }
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // user avatar
                        Avatar(avatarBitmap, 64.dp)
                        // change photo button
                        Text(
                            text = "Change photo",
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Medium,
                            fontSize = 8.sp,
                            color = MutedColor,
                            modifier = Modifier.clickable {
                                changeAvatar.value = true
                            }
                        )
                    }
                    // name
                    Text(

                        text = "${UserModel.firstName.value} ${UserModel.lastName.value}",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15f.sp,
                        color = UserNameColor,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )

                    ExtendedFloatingActionButton(
                        modifier = Modifier.fillMaxWidth(0.75f),
                        // on below line we are setting text for our fab
                        text = {
                            Text(
                                text = "Upload item",
                                style = MaterialTheme.typography.body1,
                                fontSize = 14f.sp,
                                color = SignButtonTextColor
                            )
                        },
                        onClick = {},
                        backgroundColor = SignButtonColor,
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.share),
                                contentDescription = null, tint = SignButtonTextColor
                            )
                        }
                    )
                }
            }

            // content

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .weight(1f / 3f * 2f)

            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        ProfileLabelButton(
                            leftIconId = R.drawable.credit_card,
                            rightIconId = R.drawable.arrow_right,
                            text = "Trade store"
                        ) {}
                    }
                    item {
                        ProfileLabelButton(
                            leftIconId = R.drawable.credit_card,
                            rightIconId = R.drawable.arrow_right,
                            text = "Payment method"
                        ) {}
                    }
                    item {
                        ProfileLabelButton(
                            leftIconId = R.drawable.credit_card,
                            rightString = "$ ${UserModel.balance.value.toInt()}",
                            text = "Balance"
                        ) {}
                    }
                    item {
                        ProfileLabelButton(
                            leftIconId = R.drawable.credit_card,
                            rightIconId = R.drawable.arrow_right,
                            text = "Trade history"
                        ) {}
                    }
                    item {
                        ProfileLabelButton(
                            leftIconId = R.drawable.reload,
                            rightIconId = R.drawable.arrow_right,
                            text = "Restore Purchase"
                        ) {}
                    }
                    item {
                        ProfileLabelButton(
                            leftIconId = R.drawable.question,
                            rightIconId = null,
                            text = "Help"
                        ) {}
                    }
                    item {
                        ProfileLabelButton(
                            leftIconId = R.drawable.log_out,
                            rightIconId = null,
                            text = "Log out",
                            rightString = null,
                        ) {
                            Utils.logOut()
                            setScreen(ScreenName.SIGN_IN)
                        }
                    }
                }
            }
        }
    }
}

