package com.serdjuk.figmatest.data

import android.content.Context
import android.widget.Toast
import java.io.File

object AppFile {

    const val FILE_DATA = "data.gson"

    fun saveData(context: Context) {
        try {
            val file = File(context.filesDir, FILE_DATA)
            if (!file.exists()) {
                file.createNewFile()
            }
            val text = gson.toJson(UserData.gsonVariant())
            file.writeText(text)
        } catch (e: Exception) {
            Toast.makeText(context, "No known issues with saving history.", Toast.LENGTH_SHORT)
                .show()
            e.printStackTrace()
        }
    }

    fun loadData(context: Context): String? {
        return try {
            val file = File(context.filesDir, FILE_DATA)
            if (file.exists()) {
                file.readText()
            } else {
                Toast.makeText(context, "user data file is absent.", Toast.LENGTH_SHORT)
                    .show()
                null
            }
        } catch (e: Exception) {
            Toast.makeText(context, "No known issues with load user data.", Toast.LENGTH_SHORT)
                .show()
            e.printStackTrace()
            null
        }
    }
}