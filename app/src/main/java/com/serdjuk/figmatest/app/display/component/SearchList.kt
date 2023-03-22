package com.serdjuk.figmatest.app.display.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.serdjuk.figmatest.app.data.Api
import com.serdjuk.figmatest.app.data.EMPTY_STRING
import com.serdjuk.figmatest.systemControllerReturn
import com.serdjuk.figmatest.ui.theme.MutedColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.min

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchList(keyboardController: SoftwareKeyboardController?) {

    val finallyText = remember { mutableStateOf<List<String>?>(null) }
    val height = remember { mutableStateOf(0f) }

    LaunchedEffect(searchQuery.value) {
        if (searchQuery.value.isNotEmpty()) {
            Api.getSearching(template = searchQuery.value)?.let { list ->
                val generate = arrayListOf<String>()
                list.forEach {
                    if (it.contains(searchQuery.value, ignoreCase = true)) {
                        generate.add(it)
                    }
                }
                finallyText.value = generate
            }
        }
    }


    if (searchQuery.value.isNotEmpty() && !finallyText.value.isNullOrEmpty())
        Popup(alignment = Alignment.TopCenter,
            properties = PopupProperties(dismissOnClickOutside = true),
            onDismissRequest = {
//                searchQuery.value = EMPTY_STRING
                systemControllerReturn.value = !systemControllerReturn.value
            }) {

            finallyText.value?.let { text ->
                height.value = (finallyText.value?.size!! * 20f)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(min(height.value, 128f).dp)
                        .background(color = Color(0xE0FFFFFF))
                        .border(width = 1.dp, color = MutedColor)
                ) {

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        items(text.size) {
                            Text(
                                text = text[it], modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 4.dp)
                                    .clickable {
                                        searchQuery.value = text[it]
                                        keyboardController?.hide()
                                        // заглушка, типа загрузка по выбранному...
                                        Api.coroutine.launch {
                                            delay(1000)
                                            searchQuery.value = EMPTY_STRING
                                        }
                                    },
                                color = MutedColor,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                }
            }
        }
}