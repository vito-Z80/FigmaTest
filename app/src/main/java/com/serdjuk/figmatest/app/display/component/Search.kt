package com.serdjuk.figmatest.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serdjuk.figmatest.R
import com.serdjuk.figmatest.systemControllerReturn
import com.serdjuk.figmatest.ui.theme.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppSearch() {
    val search = remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier
            .fillMaxWidth(0.75f)
            .background(color = SearchBackgroundColor, AbsoluteRoundedCornerShape(12f.dp))
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(0.9f)
                .height(24.dp)
        ) {
            BasicTextField(
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .focusRequester(focusRequester = focusRequester),
                value = search.value,
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        systemControllerReturn.value = !systemControllerReturn.value
                    }),
                onValueChange = { search.value = it },
//                cursorBrush = if (focus.value) SolidColor(BlackColor) else SolidColor(Color.Unspecified),
                decorationBox = { itf ->
                    if (search.value.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "What are you looking for ?",
                                style = MaterialTheme.typography.body2,
                                fontSize = 9f.sp,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center,
                                color = SearchColor
                            )
                        }
                    }
                    itf()
                })
        }
        Box(
            modifier = Modifier
                .weight(0.1f)
                .height(24.dp),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                modifier = Modifier,
                onClick = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    systemControllerReturn.value = !systemControllerReturn.value
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.search), contentDescription = null,
                    tint = SearchColor
                )
            }
        }
    }
}