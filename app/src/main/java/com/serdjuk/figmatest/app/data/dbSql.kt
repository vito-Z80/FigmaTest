package com.serdjuk.figmatest.app.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.serdjuk.figmatest.app.domain.Utils

//  https://developer.android.com/training/data-storage/sqlite

enum class PointState(val type: String) {
    EMAIL_IS_EXISTS("Email is exists\nTry Log In"),
    WRONG_EMAIL("Email is incorrect\nTry again"),
    EMPTY_FIELD("Not all fields are filled"),
    WRONG_PASS_NAME("Name or password is not correctly"),
    DONE("Done")
}

lateinit var dbSql: DbSql
//var latest = arrayListOf<LatestX>()
//var sale = arrayListOf<FlashSale>()


object ReaderContract {


    // Table contents are grouped together in an anonymous object.
    object FeedEntry : BaseColumns {
        const val TABLE_NAME_USER = "User"
//        const val TABLE_NAME_SALE = "Sale"
//        const val TABLE_NAME_LATEST = "Latest"

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
                "${FeedEntry.COLUMN_NAME_BALANCE} TEXT," +
                "${FeedEntry.COLUMN_NAME_PASSWORD} TEXT," +
                "${FeedEntry.COLUMN_NAME_EMAIL} TEXT," +
                "${FeedEntry.COLUMN_NAME_AVATAR} TEXT)"

//    const val SQL_CREATE_SALE_ENTRIES =
//        "CREATE TABLE ${FeedEntry.TABLE_NAME_SALE} (" +
//                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
//                "${FeedEntry.COLUMN_NAME_NAME} TEXT," +
//                "${FeedEntry.COLUMN_NAME_CATEGORY} TEXT," +
//                "${FeedEntry.COLUMN_NAME_IMAGE_URL} TEXT," +
//                "${FeedEntry.COLUMN_NAME_PRICE} TEXT," +
//                "${FeedEntry.COLUMN_NAME_DISCOUNT} TEXT)"
//
//    const val SQL_CREATE_LATEST_ENTRIES =
//        "CREATE TABLE ${FeedEntry.TABLE_NAME_LATEST} (" +
//                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
//                "${FeedEntry.COLUMN_NAME_NAME} TEXT," +
//                "${FeedEntry.COLUMN_NAME_CATEGORY} TEXT," +
//                "${FeedEntry.COLUMN_NAME_IMAGE_URL} TEXT," +
//                "${FeedEntry.COLUMN_NAME_PRICE} TEXT)"
}


class FeedReaderDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(ReaderContract.SQL_CREATE_USER_ENTRIES)
//        db.execSQL(ReaderContract.SQL_CREATE_SALE_ENTRIES)
//        db.execSQL(ReaderContract.SQL_CREATE_LATEST_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("DROP TABLE IF EXISTS ${ReaderContract.FeedEntry.TABLE_NAME_USER}")
//        db.execSQL("DROP TABLE IF EXISTS ${ReaderContract.FeedEntry.TABLE_NAME_SALE}")
//        db.execSQL("DROP TABLE IF EXISTS ${ReaderContract.FeedEntry.TABLE_NAME_LATEST}")
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

class DbSql(dbHelper: FeedReaderDbHelper) {


    private val write = dbHelper.writableDatabase
    private val read = dbHelper.readableDatabase

//    fun selectSale() = read.query(
//        ReaderContract.FeedEntry.TABLE_NAME_SALE,
//        null,
//        null,
//        null, null, null, null
//    )?.let { cursor ->
//        val sale = ArrayList<FlashSale>()
//        while (cursor.moveToNext()) {
//            val s = FlashSale(
//                category = cursor.getString(cursor.getColumnIndexOrThrow(ReaderContract.FeedEntry.COLUMN_NAME_CATEGORY)),
//                image_url = cursor.getString(cursor.getColumnIndexOrThrow(ReaderContract.FeedEntry.COLUMN_NAME_IMAGE_URL)),
//                name = cursor.getString(cursor.getColumnIndexOrThrow(ReaderContract.FeedEntry.COLUMN_NAME_NAME)),
//                price = cursor.getString(cursor.getColumnIndexOrThrow(ReaderContract.FeedEntry.COLUMN_NAME_PRICE)),
//                discount = cursor.getString(cursor.getColumnIndexOrThrow(ReaderContract.FeedEntry.COLUMN_NAME_DISCOUNT))
//            )
//            sale.add(s)
//        }
//        cursor.close()
//        sale.toList()
//    }

//    fun selectLatest() = read.query(
//        ReaderContract.FeedEntry.TABLE_NAME_LATEST,
//        null,
//        null,
//        null, null, null, null
//    )?.let { cursor ->
//        val latest = ArrayList<Latest>()
//        while (cursor.moveToNext()) {
//            val l = Latest(
//                category = cursor.getString(cursor.getColumnIndexOrThrow(ReaderContract.FeedEntry.COLUMN_NAME_CATEGORY)),
//                image_url = cursor.getString(cursor.getColumnIndexOrThrow(ReaderContract.FeedEntry.COLUMN_NAME_IMAGE_URL)),
//                name = cursor.getString(cursor.getColumnIndexOrThrow(ReaderContract.FeedEntry.COLUMN_NAME_NAME)),
//                price = cursor.getString(cursor.getColumnIndexOrThrow(ReaderContract.FeedEntry.COLUMN_NAME_PRICE)),
//                discount = ""
//            )
//            latest.add(l)
//        }
//        cursor.close()
//        latest.toList()
//    }


    fun checkFields(): PointState {
        return when (true) {
            isEmailExists() -> PointState.EMAIL_IS_EXISTS
            (UserModel.firstName.value.isEmpty() || UserModel.lastName.value.isEmpty()) -> PointState.EMPTY_FIELD
            !Utils.isEmailCorrectly() -> PointState.WRONG_EMAIL
            else -> PointState.DONE
        }
    }

    private fun isEmailExists() = queryWithWhere(
        tableName = ReaderContract.FeedEntry.TABLE_NAME_USER,
        columns = arrayOf(ReaderContract.FeedEntry.COLUMN_NAME_EMAIL),
        where = arrayOf(
            Pair(
                ReaderContract.FeedEntry.COLUMN_NAME_EMAIL,
                UserModel.emailAddress.value
            )
        )
    )?.let { it.size == 1 } ?: false

    fun logIn() = queryWithWhere(
        tableName = ReaderContract.FeedEntry.TABLE_NAME_USER,
        where = arrayOf(
            Pair(
                ReaderContract.FeedEntry.COLUMN_NAME_FIRST_NAME,
                UserModel.firstName.value
            ),
            Pair(
                ReaderContract.FeedEntry.COLUMN_NAME_PASSWORD,
                UserModel.password.value
            )
        )
    )?.let { it.size == 2 } ?: false


    //  returning the primary key value of the new row
    //  Pair(Column name, value)
    @Suppress("IMPLICIT_CAST_TO_ANY")
    fun insert(tableName: String, vararg values: Pair<String, Any?>): Long {
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

    fun queryWithWhere(
        tableName: String,
        columns: Array<String>? = null,
        where: Array<Pair<String, String>>? = null,
    ) = read.query(
        tableName,
        columns,
        where?.joinToString { "${it.first} = '${it.second}' AND" }?.dropLast(4)?.replace(",", ""),
        null, null, null, null
    )?.let { cursor ->
        val result = ArrayList<String>()
        while (cursor.moveToNext()) {
            where?.forEach {
                println("RES: __________ ${it.first}, ${it.second}")
                result.add(cursor.getString(cursor.getColumnIndexOrThrow(it.first)))
            }
        }
        cursor.close()
        result.toList()
    }
}