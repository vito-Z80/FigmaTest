package com.serdjuk.figmatest.app.profile

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.serdjuk.figmatest.R
import com.serdjuk.figmatest.data.DEFAULT_USER_AVATAR
import com.serdjuk.figmatest.data.ReaderContract
import com.serdjuk.figmatest.data.UserData
import com.serdjuk.figmatest.dbSql
import com.serdjuk.figmatest.ui.theme.*

//  https://ngengesenior.medium.com/pick-image-from-gallery-in-jetpack-compose-5fa0d0a8ddaf


@Composable
fun Profile() {

    val avatarPath = remember { mutableStateOf(DEFAULT_USER_AVATAR) }

//    LaunchedEffect(Unit) {
//        avatarPath.value = dbSql.query(
//            ReaderContract.FeedEntry.TABLE_NAME_USER,
//            null,
//            null
//        )?.let {
//            println(it.size)
//            println(it.joinToString())
//            println(it[0])
//            println(it[1])
//            if (it.contains(UserData.firstName.value)) {
//                it[0]// первое значение путь до аватара, второе имя пользователя
//            } else {
//                DEFAULT_USER_AVATAR
//            }
//        } ?: DEFAULT_USER_AVATAR
//    }

    Surface(
        color = MaterialTheme.colors.background

    ) {

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
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalArrangement = Arrangement.Start
                    ) {
//                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_left),
                            contentDescription = null,
                            modifier = Modifier.padding(start = 1f.dp)
                        )
                        Text(
                            text = "Profile",
                            style = MaterialTheme.typography.h2,
                            fontWeight = FontWeight(600),
                            fontSize = 15f.sp,
                        )
                    }
                    // TODO Photo

//                    Avatar(avatarPath)

                    AsyncImage(
                        modifier = Modifier
                            .size(96f.dp)
                            .clip(CircleShape)
                            .border(2.dp, BorderColor, CircleShape),
                        model = UserData.avatar.value.ifEmpty { DEFAULT_USER_AVATAR } ,
                        contentDescription = null,
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Crop,
                    )

                    // change photo button
//                    Text(
//                        text = "Change photo",
//                        style = MaterialTheme.typography.h2,
//                        fontSize = 8.sp
//                    )
                    // name
                    Text(
                        text = "${UserData.firstName.value} ${UserData.lastName.value}",
                        style = MaterialTheme.typography.h2,
                        fontWeight = FontWeight(700),
                        fontSize = 15f.sp
                    )

                    ExtendedFloatingActionButton(
                        modifier = Modifier.fillMaxWidth(0.75f),
                        // on below line we are setting text for our fab
                        text = {
                            Text(
                                text = "Upload item",
                                style = MaterialTheme.typography.h1,
                                fontSize = 14f.sp
                            )
                        },
                        // on below line we are adding click listener.
                        onClick = {

                        },
                        // on below line adding
                        // a background color.
                        backgroundColor = SignButtonColor,
                        // on below line we are
                        // adding a content color.
                        contentColor = SignButtonTextColor,
                        // on below line we are
                        // adding icon for our fab
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.share),
                                contentDescription = null
                            )
                        }
                    )
                }
            }

            // content

            Box(
                modifier = Modifier
                    .fillMaxWidth()
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
                        )
                    }
                    item {
                        ProfileLabelButton(
                            leftIconId = R.drawable.credit_card,
                            rightIconId = R.drawable.arrow_right,
                            text = "Payment method"
                        )
                    }
                    item {
                        ProfileLabelButton(
                            leftIconId = R.drawable.credit_card,
                            rightString = "$ ${UserData.balance.value.toInt()}",
                            text = "Balance"
                        )
                    }
                    item {
                        ProfileLabelButton(
                            leftIconId = R.drawable.credit_card,
                            rightIconId = R.drawable.arrow_right,
                            text = "Trade history"
                        )
                    }
                    item {
                        ProfileLabelButton(
                            leftIconId = R.drawable.reload,
                            rightIconId = R.drawable.arrow_right,
                            text = "Restore Purchase"
                        )
                    }
                    item {
                        ProfileLabelButton(
                            leftIconId = R.drawable.question,
                            rightIconId = null,
                            text = "Help"
                        )
                    }
                    item {
                        ProfileLabelButton(
                            leftIconId = R.drawable.log_out,
                            rightIconId = null,
                            text = "Log out"
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Avatar(avatarPath: MutableState<String>) {

    // FIXME много ремемберов, после загрузки фото все помстоянно перекомпозируется

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }


    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        imageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images
                    .Media.getBitmap(context.contentResolver, it)

            } else {
                val source = ImageDecoder
                    .createSource(context.contentResolver, it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }

            bitmap.value?.let { btm ->
                Image(
                    bitmap = btm.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(64f.dp)
                        .clip(CircleShape)                       // clip to the circle shape
                        .border(2.dp, Color.Gray, CircleShape)
                )
            }
        }
        // change photo button
        Text(
            text = "Change photo",
            style = MaterialTheme.typography.h2,
            fontSize = 8.sp,
            modifier = Modifier.clickable { launcher.launch("image/*") }
        )
    }
}

@Composable
private fun ProfileLabelButton(
    leftIconId: Int? = null,
    rightIconId: Int? = null,
    rightString: String? = null,
    text: String,
) {
    Row(
        modifier = Modifier
            .padding(16f.dp)
            .clickable { },
        verticalAlignment = Alignment.CenterVertically
    ) {
        leftIconId?.let {
            Box() {
                Icon(
                    painter = painterResource(id = R.drawable.ellipse_25),
                    contentDescription = null,
                    tint = CircleColor
                )
                Icon(
                    painter = painterResource(id = it), contentDescription = null,
                    modifier = Modifier.align(Alignment.Center),
                    tint = ProfileLabelColor
                )
            }
        }
        Text(
            text = text,
            style = MaterialTheme.typography.h2.copy(
                fontWeight = FontWeight(500),
                color = ProfileLabelColor,
                fontSize = 14f.sp,
            ),
            modifier = Modifier.padding(start = 6f.dp),
        )
        Spacer(modifier = Modifier.weight(1f))
        rightIconId?.let {
            Icon(
                painter = painterResource(id = it),
                contentDescription = null,
                tint = ProfileLabelColor
            )
        }
        rightString?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.h2,
                fontWeight = FontWeight(500),
                fontSize = 14f.sp,
                color = ProfileLabelColor
            )
        }
    }
}
