package com.serdjuk.figmatest.app.data

import android.content.Context

const val SHARE_USER_DATA = "userData"
const val SHARE_USER_FIRST_NAME = "firstName"
const val SHARE_USER_LAST_NAME = "lastName"
const val SHARE_USER_EMAIL_ADDRESS = "emailAddress"
const val SHARE_USER_PASSWORD = "password"
const val SHARE_USER_IMAGE_URL = "imageUrl"
const val SHARE_USER_BALANCE = "balance"
const val EMPTY_STRING = ""

class UserRepository(context: Context) {

    private val share = context.getSharedPreferences(SHARE_USER_DATA, Context.MODE_PRIVATE)

    fun saveFirstName(firstName: String) {
        share.edit().putString(SHARE_USER_FIRST_NAME, firstName).apply()
    }

    fun saveLastName(lastName: String) {
        share.edit().putString(SHARE_USER_LAST_NAME, lastName).apply()
    }

    fun saveEmail(emailAddress: String) {
        share.edit().putString(SHARE_USER_EMAIL_ADDRESS, emailAddress).apply()
    }

    fun savePassword(password: String) {
        share.edit().putString(SHARE_USER_PASSWORD, password).apply()
    }

    fun saveImage(image_url: String) {
        share.edit().putString(SHARE_USER_IMAGE_URL, image_url).apply()
    }

    fun saveBalance(balance: Double) {
        share.edit().putString(SHARE_USER_BALANCE, balance.toString()).apply()
    }


    private fun getUserFirstName() = share.getString(SHARE_USER_FIRST_NAME, EMPTY_STRING) ?: EMPTY_STRING
    private fun getUserLastName() = share.getString(SHARE_USER_LAST_NAME, EMPTY_STRING) ?: EMPTY_STRING
    private fun getUserEmail() = share.getString(SHARE_USER_EMAIL_ADDRESS, EMPTY_STRING) ?: EMPTY_STRING
    private fun getUserPassword() = share.getString(SHARE_USER_PASSWORD, EMPTY_STRING) ?: EMPTY_STRING
    private fun getUserImage() = share.getString(SHARE_USER_IMAGE_URL, EMPTY_STRING) ?: EMPTY_STRING
    private fun getUserBalance() =
        share.getString(SHARE_USER_BALANCE, EMPTY_STRING)?.toDoubleOrNull() ?: 0.0


    /**
     * From shared preference to mutable params.
     */
    fun fromPreference() {
        UserModel.firstName.value = getUserFirstName()
        UserModel.lastName.value = getUserLastName()
        UserModel.emailAddress.value = getUserEmail()
        UserModel.password.value = getUserPassword()
        UserModel.image_url.value = getUserImage()
        UserModel.balance.value = getUserBalance()
    }

    /**
     * From mutable params to shared preference.
     */
    fun toPreference() {
        share.edit().putString(SHARE_USER_FIRST_NAME, UserModel.firstName.value)
            .putString(SHARE_USER_LAST_NAME, UserModel.lastName.value)
            .putString(SHARE_USER_EMAIL_ADDRESS, UserModel.emailAddress.value)
            .putString(SHARE_USER_PASSWORD, UserModel.password.value)
            .putString(SHARE_USER_IMAGE_URL, UserModel.image_url.value)
            .putString(SHARE_USER_BALANCE, UserModel.balance.value.toString())
            .apply()
    }

    fun toDb() {
        dbSql.insert(
            ReaderContract.FeedEntry.TABLE_NAME_USER,
            Pair(ReaderContract.FeedEntry.COLUMN_NAME_FIRST_NAME, UserModel.firstName.value),
            Pair(ReaderContract.FeedEntry.COLUMN_NAME_LAST_NAME, UserModel.lastName.value),
            Pair(ReaderContract.FeedEntry.COLUMN_NAME_EMAIL, UserModel.emailAddress.value),
            Pair(ReaderContract.FeedEntry.COLUMN_NAME_PASSWORD, UserModel.password.value),
            Pair(ReaderContract.FeedEntry.COLUMN_NAME_AVATAR, UserModel.image_url.value),
            Pair(ReaderContract.FeedEntry.COLUMN_NAME_BALANCE, UserModel.balance.value),
        )
    }

    fun fromDb() {
        val query = dbSql.queryWithWhere(
            tableName = ReaderContract.FeedEntry.TABLE_NAME_USER,
            columns = arrayOf(
                ReaderContract.FeedEntry.COLUMN_NAME_FIRST_NAME,
                ReaderContract.FeedEntry.COLUMN_NAME_LAST_NAME,
                ReaderContract.FeedEntry.COLUMN_NAME_EMAIL,
                ReaderContract.FeedEntry.COLUMN_NAME_PASSWORD,
                ReaderContract.FeedEntry.COLUMN_NAME_AVATAR,
                ReaderContract.FeedEntry.COLUMN_NAME_BALANCE,
            )
        )
        query?.let {
            UserModel.firstName.value = it[0]
            UserModel.lastName.value = it[1]
            UserModel.emailAddress.value = it[2]
            UserModel.password.value = it[3]
            UserModel.image_url.value = it[4]
            UserModel.balance.value = it[5].toDoubleOrNull() ?: 0.0
        }
    }

}