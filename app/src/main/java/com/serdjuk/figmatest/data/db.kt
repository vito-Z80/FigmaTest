package com.serdjuk.figmatest.data

import com.serdjuk.figmatest.dbSql
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

//  https://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html
// https://www.sqlitetutorial.net/sqlite-java/update/

var latest = arrayListOf<LatestX>()
var sale = arrayListOf<FlashSale>()

////////////////////////////////////////////////////////////////////////////////////////////////////

fun testJson() {
    latest = gson.fromJson(latestText, Latest::class.java).latest
    sale = gson.fromJson(saleText, Sale::class.java).flash_sale
}

const val latestText = """{
  "latest": [
    {
      "category": "Phones",
      "name": "Samsung S10",
      "price": 1000,
      "image_url": "https://www.dhresource.com/0x0/f2/albu/g8/M01/9D/19/rBVaV1079WeAEW-AAARn9m_Dmh0487.jpg"
    },
    {
      "category": "Games",
      "name": "Sony Playstation 5",
      "price": 700,
      "image_url": "https://avatars.mds.yandex.net/get-mpic/6251774/img_id4273297770790914968.jpeg/orig"
    },
    {
      "category": "Games",
      "name": "Xbox ONE",
      "price": 500,
      "image_url": "https://www.tradeinn.com/f/13754/137546834/microsoft-xbox-xbox-one-s-1tb-console-additional-controller.jpg"
    },
    {
      "category": "Cars",
      "name": "BMW X6M",
      "price": 35000,
      "image_url": "https://mirbmw.ru/wp-content/uploads/2022/01/manhart-mhx6-700-01.jpg"
    }
  ]
}"""

const val saleText = """{
  "flash_sale": [
    {
      "category": "Kids",
      "name": "New Balance Sneakers",
      "price": 22.5,
      "discount": 30,
      "image_url": "https://newbalance.ru/upload/iblock/697/iz997hht_nb_02_i.jpg"
    },
    {
      "category": "Kids",
      "name": "Reebok Classic",
      "price": 24,
      "discount": 30,
      "image_url": "https://assets.reebok.com/images/h_2000,f_auto,q_auto,fl_lossy,c_fill,g_auto/3613ebaf6ed24a609818ac63000250a3_9366/Classic_Leather_Shoes_-_Toddler_White_FZ2093_01_standard.jpg"
    }
  ]
}"""

////////////////////////////////////////////////////////////////////////////////////////////////////


enum class UserFields {
    ID,
    FIRST_NAME,
    LAST_NAME,
    EMAIL,
    PASSWORD,
    AVATAR
}

enum class SaleTableFields {
    ID,
    NAME,
    CATEGORY,
    IMAGE_URL,
    PRICE,
    DISCOUNT
}

enum class LatestTableFields {
    ID,
    NAME,
    CATEGORY,
    IMAGE_URL,
    PRICE
}

object DB {

    const val DB_NAME = "DB"
    const val TABLE_USERS = "users"
    const val TABLE_SALE = "sale"
    const val TABLE_LATEST = "latest"

    private fun sqlUserTable(tableName: String) = """CREATE TABLE IF NOT EXISTS $tableName (
	${UserFields.ID} integer PRIMARY KEY,
    `${UserFields.FIRST_NAME}` text,
    `${UserFields.LAST_NAME}` text,
    `${UserFields.EMAIL}` text,
    `${UserFields.PASSWORD}` text,
    `${UserFields.AVATAR}` text
);"""

    private fun sqlSaleTable(tableName: String) = """CREATE TABLE IF NOT EXISTS $tableName (
	${SaleTableFields.ID} integer PRIMARY KEY,
    `${SaleTableFields.NAME}` text,
    `${SaleTableFields.CATEGORY}` text,
    `${SaleTableFields.IMAGE_URL}` text,
    `${SaleTableFields.PRICE}` real,
    `${SaleTableFields.DISCOUNT}` real
);"""

    private fun sqlLatestTable(tableName: String) = """CREATE TABLE IF NOT EXISTS $tableName (
	${LatestTableFields.ID} integer PRIMARY KEY,
    `${LatestTableFields.NAME}` text,
    `${LatestTableFields.CATEGORY}` text,
    `${LatestTableFields.IMAGE_URL}` text,
    `${LatestTableFields.PRICE}` real
);"""


    fun connect(dbName: String, closed: Boolean = false) = create(dbName, closed)
    fun create(dbName: String, closed: Boolean = false): Connection? {
        return try {
            val connection = DriverManager.getConnection("${JDBC_DRIVER}$dbName.db")
            val meta = connection?.metaData
            println("Driver: ${meta?.driverName}")
            println("БД \"$dbName\" была создана или уже существует.")
            if (closed) {
                connection?.close()
            }
            connection
        } catch (e: SQLException) {
            println("ОШИБКА ЗАПРОСА.")
            println(e.message)
            null
        }

    }

    private fun createTable(dbName: String, sqlWithTableName: () -> String) {
        connect(dbName).let {
            val stat = it?.createStatement()
            stat?.execute(sqlWithTableName.invoke())
            it?.close()
        }
    }

    fun selectFieldId(dbName: String, tableName: String, field: Enum<*>, entry: String): Int? {
        connect(dbName).let {
            val sql =
                "SELECT * FROM $tableName WHERE ${field.name} = \"$entry\""
            println(sql)
            val stm = it?.prepareStatement(sql)

            val request = stm?.executeQuery()
            println(request)
            return request?.getInt(1)
        }
    }

    private fun selectSql(tableName: String, fields: List<String>?, where: String?) = """
            SELECT
                ${fields?.joinToString() ?: "*"}
            FROM
                $tableName
            ${where ?: ""}
        """.trimIndent()

    fun selectLatest(
        dbName: String = DB_NAME,
        tableName: String,
        fields: List<String>? = null,
        where: String? = null,
    ) {
        val sql = selectSql(tableName, fields, where)
        connect(dbName).let {
            val stm = it?.prepareStatement(sql)
            stm?.executeQuery().let { r ->
                latest.clear()
                while (r?.next() == true) {
                    val data = LatestX(
                        category = r.getString(LatestTableFields.CATEGORY.name),
                        image_url = r.getString(LatestTableFields.IMAGE_URL.name),
                        name = r.getString(LatestTableFields.NAME.name),
                        price = r.getDouble(LatestTableFields.PRICE.name)
                    )
                    latest.add(data)
                }
            }
            it?.close()
        }
    }

    fun selectSale(
        dbName: String = DB_NAME,
        tableName: String,
        fields: List<String>? = null,
        where: String? = null,
    ) {
        val sql = selectSql(tableName, fields, where)
        connect(dbName).let {
            val stm = it?.prepareStatement(sql)
            stm?.executeQuery().let { r ->
                sale.clear()
                while (r?.next() == true) {
                    val data = FlashSale(
                        category = r.getString(SaleTableFields.CATEGORY.name),
                        image_url = r.getString(SaleTableFields.IMAGE_URL.name),
                        name = r.getString(SaleTableFields.NAME.name),
                        price = r.getDouble(SaleTableFields.PRICE.name),
                        discount = r.getInt(SaleTableFields.DISCOUNT.name)
                    )
                    sale.add(data)
                }
            }
            it?.close()
        }
    }

    private fun insertSql(
        tableName: String,
        fieldNames: List<String>,
        values: List<String>
    ): String {
        val str = ArrayList<String>()
        fieldNames.forEach {
            str.add("`$it`")
        }
        return """INSERT INTO $tableName (${str.joinToString()}) VALUES(${
            values.joinToString(
                transform = { "\"$it\"" })
        });""".trimMargin()
    }

    fun insert(dbName: String, tableName: String, fieldNames: List<String>, values: List<String>) {
        if (fieldNames.isEmpty() || values.isEmpty() || fieldNames.size != values.size) {
            error("Incorrect insert data to DB.")
        }
        connect(dbName).let {
            val sql = insertSql(tableName, fieldNames, values)
            val ps = it?.prepareStatement(sql)
            ps?.executeUpdate()
            it?.close()
        }
    }


    // создание базы даных и таблиц
    fun init() {
        createTable(DB_NAME) { sqlUserTable(TABLE_USERS) }
        createTable(DB_NAME) { sqlSaleTable(TABLE_SALE) }
        createTable(DB_NAME) { sqlLatestTable(TABLE_LATEST) }

        val sale = gson.fromJson(SALE, Sale::class.java)
        val latest = gson.fromJson(LATEST, Latest::class.java)

        connect(DB_NAME).let { connect ->

            latest.latest.forEach { field ->
                val result = insertSql(
                    TABLE_LATEST,
                    LatestTableFields.values().drop(1).map { n -> n.name },
                    listOf(field.name, field.category, field.image_url, field.price.toString())
                )
                val ps = connect?.prepareStatement(result)
                ps?.executeUpdate()
            }
            sale.flash_sale.forEach { field ->
                val result = insertSql(
                    TABLE_SALE,
                    SaleTableFields.values().drop(1).map { n -> n.name },
                    listOf(
                        field.name,
                        field.category,
                        field.image_url,
                        field.price.toString(),
                        field.discount.toString()
                    )
                )
                val ps = connect?.prepareStatement(result)
                ps?.executeUpdate()
            }
            connect?.close()
        }
    }

}


fun main() {
//    DB.init()

    val r = gson.toJson(UserData.gsonVariant())
    println(r)

    return


    //  точная выборка с учетом регистра
    DB.selectLatest(
        DB.DB_NAME,
        DB.TABLE_LATEST,
        where = "WHERE ${LatestTableFields.NAME} = \"Xbox ONE\""
    )
    //  без учета регистра, поиск по шаблону
    DB.selectLatest(
        DB.DB_NAME,
        DB.TABLE_LATEST,
        where = "WHERE ${LatestTableFields.NAME} LIKE \"%box%\""
    )
    //  все поля
    DB.selectLatest(DB.DB_NAME, DB.TABLE_LATEST)

    println(latest.joinToString())

    val index = DB.selectFieldId(DB.DB_NAME, DB.TABLE_LATEST, LatestTableFields.NAME, "Xbox ONE")
    println(index)


}