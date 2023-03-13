package com.serdjuk.figmatest.data

const val JDBC_DRIVER = "jdbc:sqlite:"

const val LOGIN_REGEX = "[\\d\\w _-]"

const val FIRST_NAME = "First name"
const val LAST_NAME = "Last name"
const val EMAIL = "Email"
const val PASSWORD = "Password"

const val PAD_BOTTOM = 35f
const val INPUT_CORNER = 15f

const val CONTENT_WEIGHT = 1f / 10f * 9f
const val BOTTOM_BAR_WEIGHT = 1f / 10

const val DEFAULT_USER_AVATAR = "https://img.freepik.com/free-vector/mysterious-mafia-man-smoking-cigarette_52683-34828.jpg?w=740&t=st=1678618827~exp=1678619427~hmac=fae24475e5da3de5e13be275af9fbf176c77a093a5f32bb2baec70e24a0856fe"
enum class Navigate {
    SIGN_IN,
    LOG_IN,
    SHOP,
    HOME,
    BACK,
    PROFILE
}