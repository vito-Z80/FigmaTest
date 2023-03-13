package com.serdjuk.figmatest.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serdjuk.figmatest.R
import com.serdjuk.figmatest.ui.theme.SearchBackgroundColor
import com.serdjuk.figmatest.ui.theme.SearchColor

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Search() {
    val search = remember {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    BasicTextField(value = search.value, onValueChange = { search.value = it },
        decorationBox = { itf ->
            Box(
                modifier = Modifier
                    .height(24f.dp)
                    .fillMaxWidth(0.75f)
                    .background(color = SearchBackgroundColor, AbsoluteRoundedCornerShape(12f.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (search.value.isEmpty() /*&& !focus.value*/) {
                    Text(
                        text = "What are you looking for ?",
                        style = MaterialTheme.typography.h2.copy(
                            fontSize = 12f.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                }
                itf()

                IconButton(
                    onClick = {
                        keyboardController?.hide()
                    }, modifier = Modifier.align(
                        Alignment.CenterEnd
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = null, tint = SearchColor
                    )
                }

            }
        })

}