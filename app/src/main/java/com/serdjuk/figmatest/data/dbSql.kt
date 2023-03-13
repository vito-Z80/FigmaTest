package com.serdjuk.figmatest.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

//  https://developer.android.com/training/data-storage/sqlite

object ReaderContract {


    // Table contents are grouped together in an anonymous object.
    object FeedEntry : BaseColumns {
        const val TABLE_NAME_USER = "User"
        const val TABLE_NAME_SALE = "Sale"
        const val TABLE_NAME_LATEST = "Latest"

        //        const val TABLE_NAME_MAIN = "Main"
        const val COLUMN_NAME_FIRST_NAME = "FIRSTNAME"
        const val COLUMN_NAME_LAST_NAME = "LASTNAME"
        const val COLUMN_NAME_PASSWORD = "USERPASS"
        const val COLUMN_NAME_BALANCE = "BALANCE"
        const val COLUMN_NAME_EMAIL = "EMAIL"
        const val COLUMN_NAME_AVATAR = "AVATAR"
        const val COLUMN_NAME_NAME = "NAME"
        const val COLUMN_NAME_CATEGORY = "CATEGORY"
        const val COLUMN_NAME_IMAGE_URL = "IMAGE_URL"
        const val COLUMN_NAME_PRICE = "PRICE"
        const val COLUMN_NAME_DISCOUNT = "DISCOUNT"
    }

    const val SQL_CREATE_USER_ENTRIES =
        "CREATE TABLE ${FeedEntry.TABLE_NAME_USER} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${FeedEntry.COLUMN_NAME_FIRST_NAME} TEXT," +
                "${FeedEntry.COLUMN_NAME_LAST_NAME} TEXT," +
                "${FeedEntry.COLUMN_NAME_BALANCE} REAL," +
                "${FeedEntry.COLUMN_NAME_PASSWORD} TEXT," +
                "${FeedEntry.COLUMN_NAME_EMAIL} TEXT," +
                "${FeedEntry.COLUMN_NAME_AVATAR} TEXT)"

    const val SQL_CREATE_SALE_ENTRIES =
        "CREATE TABLE ${FeedEntry.TABLE_NAME_SALE} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${FeedEntry.COLUMN_NAME_NAME} TEXT," +
                "${FeedEntry.COLUMN_NAME_CATEGORY} TEXT," +
                "${FeedEntry.COLUMN_NAME_IMAGE_URL} TEXT," +
                "${FeedEntry.COLUMN_NAME_PRICE} REAL," +
                "${FeedEntry.COLUMN_NAME_DISCOUNT} REAL)"

    const val SQL_CREATE_LATEST_ENTRIES =
        "CREATE TABLE ${FeedEntry.TABLE_NAME_LATEST} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${FeedEntry.COLUMN_NAME_NAME} TEXT," +
                "${FeedEntry.COLUMN_NAME_CATEGORY} TEXT," +
                "${FeedEntry.COLUMN_NAME_IMAGE_URL} TEXT," +
                "${FeedEntry.COLUMN_NAME_PRICE} REAL)"
}


class FeedReaderDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(ReaderContract.SQL_CREATE_USER_ENTRIES)
        db.execSQL(ReaderContract.SQL_CREATE_SALE_ENTRIES)
        db.execSQL(ReaderContract.SQL_CREATE_LATEST_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("DROP TABLE IF EXISTS ${ReaderContract.FeedEntry.TABLE_NAME_USER}")
        db.execSQL("DROP TABLE IF EXISTS ${ReaderContract.FeedEntry.TABLE_NAME_SALE}")
        db.execSQL("DROP TABLE IF EXISTS ${ReaderContract.FeedEntry.TABLE_NAME_LATEST}")
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "testDb.db"
    }
}
////////////////////////////////////////////////////////////////////////////////////////////////////

class DbSql(private val dbHelper: FeedReaderDbHelper) {


    private val write = dbHelper.writableDatabase
    private val read = dbHelper.readableDatabase


    //  returning the primary key value of the new row
    //  Pair(Column name, value)
    @Suppress("IMPLICIT_CAST_TO_ANY")
    fun insert(tableName: String, vararg values: Pair<String, Any>): Long {
        // Create a new map of values, where column names are the keys
        val sqlValues = ContentValues().apply {
            values.forEach {
                when (it.second) {
                    is String -> put(it.first, it.second as String)
                    is Int -> put(it.first, it.second as Int)
                    is Boolean -> put(it.first, it.second as Boolean)
                    is Byte -> put(it.first, it.second as Byte)
                    is ByteArray -> put(it.first, it.second as ByteArray)
                    is Double -> put(it.first, it.second as Double)
                    is Float -> put(it.first, it.second as Float)
                    is Long -> put(it.first, it.second as Long)
                    is Short -> put(it.first, it.second as Short)
                    else -> {
                        error("Insert wrong MYSQL value !!!")
                    }
                }
            }
        }
        return write.insert(tableName, null, sqlValues)
    }

    fun query(
        tableName: String,
        projection: Array<String>? = null,
        where: Array<Pair<String, String>>? = null,
    ) = read.query(
        tableName,
        projection,
        where?.joinToString { "${it.first} = '${it.second}' AND" }?.dropLast(4)?.replace(",", ""),
        null, null, null, null
    )?.let { cursor ->
        val result = ArrayList<String>()
        while (cursor.moveToNext()) {
            where?.forEach {
                result.add(cursor.getString(cursor.getColumnIndexOrThrow(it.first)))
            }
        }
        cursor.close()
        result.toList()
    }

    fun checkLoginData(): Boolean {
        println("CHECK")

        return read.query(
            ReaderContract.FeedEntry.TABLE_NAME_USER,
            arrayOf(
                ReaderContract.FeedEntry.COLUMN_NAME_FIRST_NAME,
                ReaderContract.FeedEntry.COLUMN_NAME_PASSWORD
            ),
            "${ReaderContract.FeedEntry.COLUMN_NAME_FIRST_NAME} = '${UserData.firstName.value}' AND ${ReaderContract.FeedEntry.COLUMN_NAME_PASSWORD} = '${
                Password.encryption(
                    UserData.password.value
                )
            }'",
            null,
            null,
            null,
            null
        )?.let { cursor ->
            val result = ArrayList<Boolean>()
            while (cursor.moveToNext()) {
                result.add(cursor.getString(cursor.getColumnIndexOrThrow(ReaderContract.FeedEntry.COLUMN_NAME_FIRST_NAME)) == UserData.firstName.value)
                result.add(
                    cursor.getString(cursor.getColumnIndexOrThrow(ReaderContract.FeedEntry.COLUMN_NAME_PASSWORD)) == Password.encryption(
                        UserData.password.value
                    )
                )
            }
            cursor.close()
            println(result.size)
            result.size == 2
        } ?: false
    }

    fun select(
        tableName: String,
        projection: Array<String>? = null,
        selectionColumnName: Array<String>?,
        sortBy: String? = null,
        sortOrder: String? = null,
        whereValues: Array<String>,
    ): Cursor? {
        return read.query(
            tableName,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            if (selectionColumnName != null) "${selectionColumnName.joinToString(separator = " ")} = ?" else null,              // The columns for the WHERE clause
            whereValues,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            "$sortBy $sortOrder"               // The sort order
        )
    }
}


// Create a new map of values, where column names are the keys
//val values = ContentValues().apply {
//    put(ReaderContract.FeedEntry.COLUMN_NAME_FIRST_NAME, "")
//    put(ReaderContract.FeedEntry.COLUMN_NAME_LAST_NAME, "subtitle")
//}

// Insert the new row, returning the primary key value of the new row
//val newRowId = db?.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values)


//////////////////////////////////////////////////////
//val db = dbHelper.readableDatabase
//
//// Define a projection that specifies which columns from the database
//// you will actually use after this query.
//val projection =
//    arrayOf(BaseColumns._ID, FeedEntry.COLUMN_NAME_TITLE, FeedEntry.COLUMN_NAME_SUBTITLE)
//
//// Filter results WHERE "title" = 'My Title'
//val selection = "${FeedEntry.COLUMN_NAME_TITLE} = ?"
//val selectionArgs = arrayOf("My Title")
//
//// How you want the results sorted in the resulting Cursor
//val sortOrder = "${FeedEntry.COLUMN_NAME_SUBTITLE} DESC"
//
//val cursor = db.query(
//    FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
//    projection,             // The array of columns to return (pass null to get all)
//    selection,              // The columns for the WHERE clause
//    selectionArgs,          // The values for the WHERE clause
//    null,                   // don't group the rows
//    null,                   // don't filter by row groups
//    sortOrder               // The sort order
//)