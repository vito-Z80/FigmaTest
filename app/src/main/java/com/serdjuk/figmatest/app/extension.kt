package com.serdjuk.figmatest.app

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serdjuk.figmatest.R
import com.serdjuk.figmatest.data.INPUT_CORNER
import com.serdjuk.figmatest.ui.theme.*

val emailRegex = mutableStateOf(Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"))

////////////////////////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
@Composable
fun PadBottom(value: Float) {
    Spacer(modifier = Modifier.padding(bottom = value.dp))
}

////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

@Composable
fun EnterButton(text: String, enabled: Boolean = true, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth(0.75f)
            .height(46f.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = SignButtonColor),
        shape = AbsoluteRoundedCornerShape(INPUT_CORNER.dp),
        onClick = onClick,
        enabled = enabled
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.h1,
            fontSize = 14f.sp,
            color = SignButtonTextColor
        )
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InputValue(
    placeholder: String = "",
    value: MutableState<String>,
    regex: MutableState<Regex>? = null,
    pass: MutableState<Boolean>? = null,
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val focus = remember { mutableStateOf(false) }
    val error = remember { mutableStateOf(InputColor) }
    val context = LocalContext.current

    BasicTextField(
        modifier = Modifier
            .onFocusChanged { focusState ->
                focus.value = focusState.isFocused
            }
            .border(
                width = 1.dp,
                color = error.value,
                shape = AbsoluteRoundedCornerShape(INPUT_CORNER.dp)
            ),
        value = value.value,
        onValueChange = { value.value = it },

        singleLine = true,

        textStyle = MaterialTheme.typography.h3,

        visualTransformation = if (pass?.value == true) PasswordVisualTransformation() else VisualTransformation.None,

        keyboardOptions = KeyboardOptions(
            keyboardType = if (pass?.value == true) KeyboardType.Password
            else KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                focus.value = false
                // error email format
                if (regex == null) {
                    error.value = InputColor
                } else {
                    if (regex.value.matchEntire(value.value)?.value != null) {
                        error.value = InputColor
                    } else {
                        error.value = RedColor
                        Toast.makeText(context, "Wrong EMAIL format", Toast.LENGTH_SHORT).show()
                    }
                }
            },

            ),
        decorationBox = { itf ->
            Box(
                modifier = Modifier
                    .height(32.dp)
                    .fillMaxWidth(0.75f)
                    .background(
                        color = InputColor,
                        shape = AbsoluteRoundedCornerShape(INPUT_CORNER.dp)
                    )
                    .border(
                        width = 1f.dp,
                        shape = AbsoluteRoundedCornerShape(INPUT_CORNER.dp),
                        color = InputColor
                    )
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                if (value.value.isEmpty() /*&& !focus.value*/) {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.h2,
                        color = MutedColor,
                        fontSize = 12.sp
                    )
                }
                // show eye icon if pass != null
                if (pass != null) {
                    IconButton(
                        onClick = { pass.value = !pass.value }, modifier = Modifier.align(
                            Alignment.CenterEnd
                        )
                    ) {
                        Icon(
                            painter = painterResource(id = if (pass.value) R.drawable.eye_off else R.drawable.eye),
                            contentDescription = null, tint = Eye
                        )
                    }
                }
                itf()
            }
        }
    )
}

////////////////////////////////////////////////////////////////////////////////////////////////////

@Composable
fun Int.toPix() = LocalDensity.current.run { this@toPix.toDp().toPx() }