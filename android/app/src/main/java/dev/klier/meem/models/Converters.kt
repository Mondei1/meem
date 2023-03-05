package dev.klier.meem.models

import androidx.room.TypeConverter
import java.net.URI
import java.net.URL

class Converters {
    @TypeConverter
    fun getInt(value: String?): Int? {
        return value?.toInt()
    }

    @TypeConverter
    fun intToString(value: Int?): String {
        return value.toString()
    }

    @TypeConverter
    fun getUri(value: String?): URI? {
        return try {
            URI.create(value)
        } catch (e: java.lang.IllegalArgumentException) {
            null
        }
    }

    @TypeConverter
    fun uriToString(value: URI?): String {
        return value.toString()
    }
}