package com.serdjuk.figmatest.app.domain

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import com.serdjuk.figmatest.app.data.UserModel
import com.serdjuk.figmatest.app.data.emailRegex
import com.serdjuk.figmatest.app.display.ScreenName
import com.serdjuk.figmatest.screen


object Utils {

    fun isEmailCorrectly() = emailRegex.value.matchEntire(UserModel.emailAddress.value) != null

    fun logOut() {
        UserModel.firstName.value = ""
        UserModel.lastName.value = ""
        UserModel.emailAddress.value = ""
        UserModel.password.value = ""
        UserModel.image_url.value = ""
        UserModel.balance.value = 0.0
    }

    fun navigation(
        // TODO если перед тем как нажать второй раз для выхода из приложения перейти дальше,
        //  то при следующем нажатии back будет выход из приложения
        //  в таких случаях нужно занести в стек ScreenName.SIGN_IN
        stack: java.util.Stack<ScreenName>,
        walk: MutableState<Boolean>,
        context: Context,
    ) {
        if (stack.isEmpty()) {
            walk.value = false
        } else {
            val previousScreen = stack.pop()
            if (stack.isEmpty() || screen.value == ScreenName.SIGN_IN) {
                walk.value = false
                Toast.makeText(
                    context,
                    "Tap Back again for exit",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                screen.value = stack.pop()
            }
        }
    }
}
