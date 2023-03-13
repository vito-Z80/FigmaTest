package com.serdjuk.figmatest.data

import androidx.compose.runtime.mutableStateOf
import com.serdjuk.figmatest.app.emailRegex
import com.serdjuk.figmatest.dbSql

enum class UserEnterState {
    EMAIL_IS_EXISTS,
    WRONG_EMAIL,
    NAME_EMPTY,
    DONE
}

data class UserDataGson(
    val firstName: String,
    val lastName: String,
    val emailAddress: String,
    val password: String,
    val balance: Double?,
    val avatar: String,
)

object UserData {
    val firstName = mutableStateOf("")
    val lastName = mutableStateOf("")
    val emailAddress = mutableStateOf("")
    val password = mutableStateOf("")
    val balance = mutableStateOf(0.0)
    val avatar = mutableStateOf("")

    fun isEnterDataCorrect(): UserEnterState {
        return when (true) {
            isEmailExists() -> UserEnterState.EMAIL_IS_EXISTS
            !isEmailCorrectly() -> UserEnterState.WRONG_EMAIL
            (firstName.value.isEmpty() || lastName.value.isEmpty()) -> UserEnterState.NAME_EMPTY
            else -> UserEnterState.DONE
        }
    }

    private fun isEmailCorrectly() = emailRegex.value.matchEntire(emailAddress.value) != null


    private fun isEmailExists(): Boolean {
        println("CHECK")
        val cursor = dbSql.select(
            tableName = ReaderContract.FeedEntry.TABLE_NAME_USER,
            projection = arrayOf(ReaderContract.FeedEntry.COLUMN_NAME_EMAIL),
            selectionColumnName = arrayOf(ReaderContract.FeedEntry.COLUMN_NAME_EMAIL),
            whereValues = arrayOf(emailAddress.value),
            sortBy = ReaderContract.FeedEntry.COLUMN_NAME_EMAIL,
            sortOrder = "DESC"
        )
        val itemIds = mutableListOf<String>()
        cursor?.let {
            while (it.moveToNext()) {
                val itemId =
                    it.getString(it.getColumnIndexOrThrow(ReaderContract.FeedEntry.COLUMN_NAME_EMAIL))
                itemIds.add(itemId)
            }
        }
        cursor?.close()
        println(itemIds.joinToString())
        return itemIds.size > 0
    }

    fun logOut() {
        firstName.value = ""
        lastName.value = ""
        emailAddress.value = ""
        password.value = ""
        avatar.value = ""
        balance.value = 0.0
    }

    fun gsonVariant() = UserDataGson(
        firstName = firstName.value,
        lastName = lastName.value,
        emailAddress = emailAddress.value,
        password = password.value,
        balance = balance.value,
        avatar = avatar.value
    )

    fun fillFromGson(data: UserDataGson) {
        firstName.value = data.firstName
        lastName.value = data.lastName
        emailAddress.value = data.emailAddress
        password.value = data.password
        balance.value = data.balance ?: 0.0
        avatar.value = data.avatar
    }

}