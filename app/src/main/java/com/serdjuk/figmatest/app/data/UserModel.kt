package com.serdjuk.figmatest.app.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

object UserModel {
    val firstName = mutableStateOf("")
    val lastName = mutableStateOf("")
    val emailAddress = mutableStateOf("")
    val password = mutableStateOf("")
    val balance = mutableStateOf(0.0)
    val image_url: MutableState<String?> = mutableStateOf(null)
}