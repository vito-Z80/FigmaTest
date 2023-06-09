package com.serdjuk.figmatest.app.domain

import java.security.MessageDigest
import java.util.Date
import kotlin.random.Random

object Password {

    private val chars = Regex("[\\dA-Za-z]")
    private const val startChar = '!'.code
    private const val endChar = '}'.code

    fun generate(): String {
        val rnd = Random(Date().time)
        val result = StringBuilder()
        repeat(16) {
            var r = rnd.nextInt(startChar, endChar).toChar()
            while (chars.matchEntire(r.toString())?.value == null) {
                r = rnd.nextInt(startChar, endChar).toChar()
            }
            result.append(r)
        }
        println(result)
        return result.toString()
    }

    fun encryption(password: String): String {
        val ba = password.encodeToByteArray()
        println(ba)
        val md = MessageDigest.getInstance("SHA-256")
        val bytes = md.digest(ba)
        return bytes.fold(StringBuilder()) { sb, it -> sb.append("%02x".format(it)) }.toString()
    }
}